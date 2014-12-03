
var CurrenciesRouter = Backbone.Router.extend({
   routes: {
       '' : 'start',
       'search/:text' : 'search'
   },

    initialize: function(){
        $('#header').html(new HeaderView().render().el);
    },

    start: function(){
        console.log('Hello!');
        if (this.ManufacturersPage != null){
            this.ManufacturersPage.remove();
        }
        this.ManufacturersPage = new ManufacturersPage();
        $('#content').html(this.ManufacturersPage.render().el);
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