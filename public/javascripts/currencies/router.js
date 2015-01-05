
var CurrenciesRouter = Backbone.Router.extend({
   routes: {
       '' : 'start',
       'manufacturers' : 'manufacturers',
       'products' : 'products',
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

    products: function(){
        console.log('Products page');
        if (this.ProductsPage != null){
            this.ProductsPage.remove();
        }
        this.ProductsPage = new ProductsPage();
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