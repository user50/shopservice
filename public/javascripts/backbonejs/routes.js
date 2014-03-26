var app = app || {};

var Router = Backbone.Router.extend({
    routes: {
        "products/page/:page" : "productsPage"
    },

    productsPage: function(page){
        app.Products.trigger('updatePage', page);
    }
});

new Router();
Backbone.history.start();