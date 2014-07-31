var LinkedPage = Backbone.View.extend({
    tagName: 'div',

    events: {
    },

    initialize: function(options){
        this.options = options;

        this.providerId = this.options.providerId; // провайдер, для якого рендериться сторінка

        console.log('Linked products of provider: ' + this.providerId);

        this.Provider = new Provider({id: this.providerId});
        this.Provider.fetch();

        this.LinkedProducts = new LinkedProducts({providerId: this.providerId});
        this.LinkedProductsView = new LinkedProductsView({collection: this.LinkedProducts});

        this.LinkedBreadcrumbsView = new LinkedBreadcrumbsView({model: this.Provider});
        this.LinkedSearch = new LinkedSearch();

        this.listenTo(actVent, 'linkedProduct:relink', function(linkedName){
            actualizationRouter.navigate('providers/' + this.providerId+ "/linkingProduct/" + linkedName, {trigger: true});
        });
//
//        this.listenTo(actVent, 'unlinked:autolink', function(){
//            this.UnlinkedProducts.fetchAutolink();
//        })
    },

    render: function(){
        this.$el.empty();
        this.LinkedProducts.fetch();

        this.$el.append(this.LinkedBreadcrumbsView.render().el);
        this.$el.append(this.LinkedSearch.render().el);
        this.$el.append(this.LinkedProductsView.el);
        return this;
    }
});

var LinkedBreadcrumbsView = Backbone.View.extend({
    tagName: 'ol',
    className: 'breadcrumb',

    template: _.template($('#linkedPageBreadcrumbsTpl').html()),

    initialize: function(options) {
        this.options = options || {};
        this.model.bind("change", this.render, this);
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    }
});

var LinkedSearch = Backbone.View.extend({
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
            actVent.trigger('linked:search', text);
    }
});

var LinkedProduct = Backbone.Model.extend({});

var LinkedProductView = Backbone.View.extend({
    tagName: 'tr',

    events: {
        'click .btn-danger' : 'removeThis',
        'click .btn-primary' : function(){
            actVent.trigger('linkedProduct:relink', this.model.get('name'));
        }
    },

    template: _.template($('#linkedProductViewTpl').html()),

    initialize: function(){
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
        bootbox.dialog({
            message: "Вы уверены, что хотите отменить связывание  \"" + this.model.get('name') + "\" ?",
            buttons: {
                success: {
                    label: "Отмена",
                    className: "btn-default",
                    callback: function() {
                    }
                },
                danger: {
                    label: "Удалить",
                    className: "btn-danger",
                    callback: function() {
                        model.destroy({
                            data: JSON.stringify({
                                linked: true
                            }),
                            contentType: 'application/json'
                        });
                        $.bootstrapGrowl("Связывание \"" + model.get('name') + "\" отменено!",
                            {ele: 'body', type: 'success', width: 350});
                    }
                }
            }
        });
    }
});

var LinkedProducts = Backbone.Collection.extend({
    model: LinkedProduct,

    initialize: function(options){
        this.options = options;
        this.providerId = this.options.providerId;
    },

    url: function(){
        return '/clients/' + clientId + '/providers/' + this.providerId + '/linkedProductEntries';
    }
});

var LinkedProductsView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        var view = new LinkedProductView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of LinkedProductsView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        tr.append('<th  class="col-md-5">Товар поставщика</th>');
        tr.append('<th class="col-md-5"> Товар интернет-магазина</th>');
        tr.append('<th class="col-md-2"></th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});
