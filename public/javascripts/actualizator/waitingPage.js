var WaitingPage = Backbone.View.extend({
    tagName: 'div',

    initialize: function(options){
        this.options = options;
        this.providerId = this.options.providerId; // провайдер, для якого рендериться сторінка
        this.Provider = new Provider({id: this.providerId});
        this.Provider.fetch();

        this.WaitingPageBreadcrumbs = new WaitingPageBreadcrumbs({model: this.Provider,
            providerProductName: this.providerProductName});

        this.WaitingSearch = new WaitingSearch({providerId: this.providerId});
    },

    render: function(){
        this.$el.empty();

        this.WaitingProducts = new WaitingProducts({providerId: this.providerId});
        this.WaitingProductsView = new WaitingProductsView({collection: this.WaitingProducts});
        this.WaitingProducts.fetch({data: {linked: false}});

        this.$el.append(this.WaitingPageBreadcrumbs.render().el);
        this.$el.append(this.WaitingSearch.render().el);
        this.$el.append(this.WaitingProductsView.render().el);
        return this;
    }
});

var WaitingPageBreadcrumbs = Backbone.View.extend({
    tagName: 'ol',
    className: 'breadcrumb',

    initialize: function() {
        this.template = _.template(tpl.get('waitingPageBreadcrumbsTpl').text);
        this.model.bind("change", this.render, this);
    },

    render: function(){
        var template = this.template({providerId: this.model.id,
            providerName: this.model.get('name')});
        this.$el.html( template );
        return this;
    }

});

var WaitingSearch = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    initialize: function(options){
        this.options = options;
        this.template = _.template(tpl.get('unlinkedSearchTpl').text);
    },

    events: {
        'click #unlinkedSearchBtn' : 'searchText',
        'keydown #searchText' : function(e){
            if(e.keyCode == 13) {
                e.preventDefault();
                this.searchText();
            }
        }
    },

    render: function(text){
        var template = this.template({text: text});
        this.$el.html( template );
        return this;
    },

    searchText: function(){
        var text = this.$el.find('#searchText').val();
        if (text != '')
            actVent.trigger('waitingProduct:search', text);
    }
});

var WaitingProduct = Backbone.Model.extend();

var WaitingProducts = Backbone.Collection.extend({
    model: WaitingProduct,

    initialize: function(options){
        this.options = options;
        this.providerId = this.options.providerId;
    },

    url: function(){
        return '/clients/' + clientId + '/providers/' + this.providerId + '/linkedProductEntries';
    }
});

var WaitingProductView = Backbone.View.extend({
    tagName: 'tr',

    events: {
        'click .btn-danger' : 'removeThis'
    },

    initialize: function(){
        this.template = _.template(tpl.get('waitingProductViewTpl').text);

        this.model.on('change', this.render, this);
        this.model.on('destroy', this.remove, this);
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    removeThis: function(){
        var model = this.model;
        model.destroy({
            error: function(data, status){
                if (status.status == 200){
                    $.bootstrapGrowl("Товар поставщика \"" + data.attributes.name +
                        "\" перенесен в список несвязанных товаров!",
                        {ele: 'body', type: 'info', width: 350});
                } else {
                    $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                        {ele: 'body', type: 'danger', width: 350});
                }
            }
        });
    }
});

var WaitingProductsView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        var view = new WaitingProductView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of WaitingProductsView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        tr.append('<th  class="col-md-10">Товар поставщика</th>');
        tr.append('<th class="col-md-2"></th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});

