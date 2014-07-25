var app1 = app1 || {};

var actVent = _.extend({}, Backbone.Events);

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'providers/:providerId/linkProducts' : 'linkProducts',
        'providers/:providerId/linkProducts/search/:text' : 'unlinkedSearch'
    },

    initialize: function(){

    },

    start: function(){
        console.log('Hello!');
        this.ProviderPage = new ProviderPage();
        $('#content').html(this.ProviderPage.render().el);
    },

    linkProducts: function(providerId){
        this.UnlinkedPage = new UnlinkedPage({providerId: providerId});
        $('#content').html(this.UnlinkedPage.render().el);
    },

    unlinkedSearch: function(providerId, text){
        this.UnlinkedPage = new UnlinkedPage({providerId: providerId});
        this.UnlinkedPage.search(text);
        $('#content').html(this.UnlinkedPage.el);
    }
});
app1.actualizationRouter = new ActualizationRouter();
Backbone.history.start();

