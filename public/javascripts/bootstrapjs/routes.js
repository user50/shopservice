var app = app || {};

var Router = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'groups/:groupId/categories/:categoryId' : 'showProductsTable',
        'groups/:groupId' : 'showGroup'
    },
    start: function(){
        console.log('Hello!');
    },

    showProductsTable: function(groupId, categoryId){
        console.log("Show products of category: " + categoryId + ", group " + groupId);
        new BreadcrumbsView({categoryName: app.categories.get(categoryId).get('name'),
                             groupName: app.Groups.get(currentGroupId).get('name')});
        app.SearchView.hideResults();
        app.categoriesView.$el.hide();
        vent.trigger('selectedCategory');
        app.ProductsView.$el.show();
        app.pagination.$el.show();
    },

    showGroup: function(groupId){
        console.log("Show products of group: " + groupId);
        new BreadcrumbsView({categoryName: null, groupName: app.Groups.get(currentGroupId).get('name')});
        app.categories.trigger('changeCurrentGroup', groupId);
        app.ProductsView.$el.hide();
        app.pagination.$el.hide();
        app.SearchView.hideResults();
        app.categoriesView.$el.show();
    }
});

app.router = new Router();
Backbone.history.start();