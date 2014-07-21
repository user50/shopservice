var app1 = app1 || {};


var UnlinkedPage = Backbone.View.extend({
    tagName: 'div',

    initialize: function(options){
        this.options = options;

        var providerId = this.options.providerId; // провайдер, для якого рендериться сторінка

        this.Provider = new Provider({id: providerId});
        this.Provider.fetch();

        this.UnlinkedBreadcrumbsView = new UnlinkedBreadcrumbsView({model: this.Provider});
        this.UnlinkedProducts = new UnlinkedProducts();
        this.UnlinkedProductsView = new UnlinkedProductsView({collection: this.UnlinkedProducts})

        this.$el.append(this.UnlinkedBreadcrumbsView.render().el);
    },

    events: {
    }

});