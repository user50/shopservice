
var CurrenciesRouter = Backbone.Router.extend({
   routes: {
       '' : 'start',
       'manufacturers' : 'manufacturers',
       'categories' : 'categories',
       'products/:categoryId' : 'products',
       'search/:text' : 'search'
   },

    initialize: function(){
        $('#header').html(new HeaderView().render().el);
    },

    start: function(){
        $('#content').html(new SwitchButtons().render().el);
    },

    manufacturers: function(){
        console.log('Manufacturers page');
        if (this.ManufacturersPage != null){
            this.ManufacturersPage.remove();
        }
        this.ManufacturersPage = new ManufacturersPage();
        $('#content').html(this.ManufacturersPage.render().el);
    },

    categories: function(){
        console.log('Products page');
        if (this.CategoriesPage != null){
            this.CategoriesPage.remove();
        }
        this.CategoriesPage = new CategoriesPage();
        $('#content').html(this.CategoriesPage.render().el);
    },

    products: function(categoryId){
        console.log('Products Page');
        if (this.ProductsPage != null){
            this.ProductsPage.remove();
        }
        this.ProductsPage = new ProductsPage({categoryId : categoryId});
        $('#content').html(this.ProductsPage.render().el);
    },

    search: function(text){
        if (this.SearchPage != null)
            this.SearchPage.remove();

        this.SearchPage = new SearchPage();
        $('#content').html(this.SearchPage.render(text).el);
    }
});

tpl.loadTemplates(function () {
    clientId = "client1";
    actVent = _.extend({}, Backbone.Events);
//    clientId = $.cookie('clientId');
    currenciesRouter = new CurrenciesRouter();
    Backbone.history.start();
});