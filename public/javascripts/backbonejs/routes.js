var app = app || {};

var Router = Backbone.Router.extend({
    routes: {
        'groups/:groupId/categories/:categoryId' : 'showProductsTable',
        'groups/:groupId' : 'showGroup'
    },

    showProductsTable: function(groupId, categoryId){
        console.log("Show products of category: " + categoryId + ", group " + groupId);
        app.categories.reset();
        app.categoriesView.$el.hide();
        vent.trigger('selectedCategory');
        app.BackButton.$el.show();
        app.ProductsView.$el.show();
        app.pagination.$el.show();
    },

    showGroup: function(groupId){
        console.log("Show products of group: " + groupId);
        app.categories.trigger('changeCurrentGroup', groupId);
        app.ProductsView.$el.hide();
        app.pagination.$el.hide();
        app.categoriesView.$el.show();
    }
});

app.router = new Router();
Backbone.history.start();