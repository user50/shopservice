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
    },

    showGroup: function(groupId){
        console.log("Show products of group: " + groupId);
        app.categories.trigger('changeCurrentGroup', groupId);
    }
});

app.router = new Router();
Backbone.history.start();