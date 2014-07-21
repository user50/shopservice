var app1 = app1 || {};

var actVent = _.extend({}, Backbone.Events);

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'providers/:providerId/linkProducts' : 'linkProducts'
    },

    initialize: function(){

    },

    start: function(){
        console.log('Hello!');
        this.ProviderPage = new ProviderPage();
        $('#contents').html(this.ProviderPage.render().el);
    },

    linkProducts: function(providerId){
        console.log('Link products of provider: ' + providerId);

        this.Provider = new Provider({id: providerId});
        this.Provider.fetch();

        this.UnlinkedBreadcrumbsView = new UnlinkedBreadcrumbsView({model: this.Provider});
        this.UnlinkedProducts = new UnlinkedProducts();
        this.UnlinkedProducts.setProviderId(providerId);
        this.UnlinkedProductsView = new UnlinkedProductsView({collection: this.UnlinkedProducts});
        this.UnlinkedProducts.fetch();

        $('#contents').html(this.UnlinkedBreadcrumbsView.render().el);
        $('#contents').append(this.UnlinkedProductsView.render().el);
    }
});
app1.actualizationRouter = new ActualizationRouter();
Backbone.history.start();

