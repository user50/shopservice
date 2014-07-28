var app1 = app1 || {};

var LinkingPage = Backbone.View.extend({
    tagName: 'div',

    events: {
        'click #toUnlinkedList' : 'toUnlinkedList'
    },

    initialize: function(options){
        this.options = options;
        this.providerId = this.options.providerId; // провайдер, для якого рендериться сторінка
        this.providerProductName = this.options.providerProductName;
        this.Provider = new Provider({id: this.providerId});
        this.Provider.fetch();

        this.LinkingSearch = new LinkingSearch({providerId: this.providerId})

        this.LinkingPageBreadcrumbs = new LinkingPageBreadcrumbs({model: this.Provider,
            providerProductName: this.providerProductName});

        this.BackButtons = new BackButtons();

        this.listenTo(actVent,'product:linked', this.linkProduct);
    },

    render: function(){
        this.Products = new Products();
        this.ProductsView = new ProductsView({collection: this.Products});
        this.Products.fetch();

        this.$el.append(this.LinkingPageBreadcrumbs.render().el);
        this.$el.append(this.LinkingSearch.render().el);
        this.$el.append(this.ProductsView.render().el);
        this.$el.append(this.BackButtons.render().el);
        return this;
    },

    linkProduct: function(productEntryId){
        var url = '/clients/' + clientId + '/providers/' + this.Provider.id + '/linkedProductEntries';
        var linkedProductEntry = {clientProductId: productEntryId,
                                  name: this.providerProductName};
        var self = this;
        $.ajax({
            url:url,
            type: 'POST',
            data: JSON.stringify(linkedProductEntry),
            contentType: 'application/json',
            success: function(){
                $.bootstrapGrowl("Изменения успешно сохранены!",
                    {ele: 'body', type: 'success', width: 350});
                self.toUnlinkedList();
            },
            error: function(){
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: 'body', type: 'danger', width: 350});
            }
        });
    },

    toUnlinkedList: function(){
        app1.actualizationRouter.navigate('providers/' + this.providerId+ "/linkProducts", {trigger: true});
    }
});

var LinkingPageBreadcrumbs = Backbone.View.extend({
    tagName: 'ol',
    className: 'breadcrumb',

    template: _.template($('#linkingPageBreadcrumbsTpl').html()),

    initialize: function(options) {
        this.options = options || {};
        this.providerProductName = this.options.providerProductName;

        this.model.bind("change", this.render, this);
    },

    render: function(){
        var template = this.template({providerName: this.model.get('name'),
            providerProductName: this.providerProductName});
        this.$el.html( template );
        return this;
    }
});

var Product = Backbone.Model.extend({});

var Products = Backbone.Collection.extend({
    model: Product,

    url: function(){
        return '/clients/' + clientId + '/products?like=test';
    }
});

var ProductView = Backbone.View.extend({
    tagName: 'a',
    className: 'list-group-item cursorPointer',

    events: {
        'click' : 'onClick'
    },

    template: _.template($('#unlinkedProductTpl').html()),

    initialize: function(){
        this.model.on('change', this.render, this);
    },

    render: function(){
        console.log("Render ProductView to a Product model with id: " + this.model.id);
        this.$el.attr('id', this.model.id);
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    onClick: function(){
        console.log('Selected roduct with name : ' + this.model.get('name'));
        actVent.trigger('product:linked', this.model.id);
    }
});

var ProductsView = Backbone.View.extend({
    tagName: 'div',
    className: 'list-group',

    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.render, this);
    },
    render: function(){
        this.$el.empty();
        this.collection.each(this.addOne, this);
        return this;
    },
    addOne: function(product){
        console.log("Adding the Product view (model.name = " + product.get('name') + ")to ProductsView : start...");
        var productView = new ProductView({model: product});
        this.$el.append(productView.render().el);
    }
});

var BackButtons = Backbone.View.extend({
    tagName: 'div',
    className: 'row',
    template: _.template($('#linkingBackBtnsTpl').html()),
    render: function(){
        var template = this.template();
        this.$el.html( template );
        return this;
    }
});

var LinkingSearch = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    template: _.template($('#unlinkedSearchTpl').html()),

    initialize: function(options){
        this.options = options;
    },

    events: {
//        'click #unlinkedSearchBtn' : 'searchText',
//        'keydown #searchText' : function(e){
//            if(e.keyCode == 13) {
//                e.preventDefault();
//                this.searchText();
//            }
//        }
    },

    render: function(text){
        var template = this.template({text: text});
        this.$el.html( template );
        return this;
    },

    searchText: function(){
        var text = this.$el.find('#searchText').val();
        if (text == '')
            return;
        else
            app1.actualizationRouter.navigate('/providers/' + this.options.providerId+ "/linkProducts/search/" + text, {trigger: true});
    }
});