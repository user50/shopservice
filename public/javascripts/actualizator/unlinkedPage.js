var app1 = app1 || {};


var UnlinkedPage = Backbone.View.extend({
    el: '#contents',

    initialize: function(options){
        this.options = options;

        var providerId = this.options.providerId; // провайдер, для якого рендериться сторінка

        console.log('Link products of provider: ' + providerId);

        this.Provider = new Provider({id: providerId});
        this.Provider.fetch();

        this.UnlinkedBreadcrumbsView = new UnlinkedBreadcrumbsView({model: this.Provider});
        this.UnlinkedProducts = new UnlinkedProducts();
        this.UnlinkedProducts.setProviderId(providerId);
        this.UnlinkedProductsView = new UnlinkedProductsView({collection: this.UnlinkedProducts});
        this.UnlinkedProducts.fetch();
    },

    render: function(){
        this.$el.append(this.UnlinkedBreadcrumbsView.render().el);
        this.$el.append(this.UnlinkedProductsView.render().el);
        return this;
    }

});