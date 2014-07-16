var app1 = app1 || {};


var UnlinkedPage = Backbone.View.extend({
    el: '#unlinkedPage',

    initialize: function(){
        this.UnlinkedProducts = new UnlinkedProducts();
        this.UnlinkedProducts.fetch();

        this.UnlinkedProductsView = new UnlinkedProductsView({collection: this.UnlinkedProducts});
        this.UnlinkedBreadcrumbsView = new UnlinkedBreadcrumbsView({model: new Provider({id: '', name: '', url: ''})});
    },

    events: {
    },

    setProvider: function(providerModel){
        this.UnlinkedBreadcrumbsView = new UnlinkedBreadcrumbsView(providerModel.toJSON());
    }

});

app1.UnlinkedPage = new UnlinkedPage();