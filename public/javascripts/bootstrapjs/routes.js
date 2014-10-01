var app = app || {};

var Router = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'groups/:groupId/categories/:categoryId' : 'showProductsTable',
        'groups/:groupId' : 'showGroup',
        'groups/:groupId/search=*searchText' : 'searchProduct'
    },

    initialize: function(){
        this.EditProduct = new EditProduct({model: new Product()});
        this.listenTo(vent, 'product:edit', this.renderEditProductView);
    },

    start: function(){
        console.log('Hello!');
    },

    showProductsTable: function(groupId, categoryId){
        console.log("Show products of category: " + categoryId + ", group " + groupId);
        new BreadcrumbsView({categoryName: app.categories.get(categoryId).get('name'),
                             groupName: app.Groups.get(currentGroupId).get('name')});
        app.SearchView.hideResults();
        $('#categoriesTabs').hide();
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
        $('#categoriesTabs').show();
    },

    searchProduct: function(groupId, searchText){
        $('#categoriesTabs').hide();
        app.ProductsView.$el.hide();
        app.pagination.$el.hide();
    },

    renderEditProductView: function(modelId){
        this.EditProduct.updateModel(modelId);
        this.EditProduct.render();
    }
});

app.router = new Router();
Backbone.history.start();