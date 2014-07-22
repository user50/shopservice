var app1 = app1 || {};

var actVent = _.extend({}, Backbone.Events);

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'providers/:providerId/linkProducts' : 'linkProducts'
    },

    initialize: function(){

    },

    start: function(){
        console.log('Hello!');
        this.ProviderPage = new ProviderPage();
        $('#contents').empty();
        this.ProviderPage.render();
        console.log('Hello!');
    },

    linkProducts: function(providerId){
        this.UnlinkedPage = new UnlinkedPage({providerId: providerId});
        $('#contents').empty();
        this.UnlinkedPage.render();
    }
});
app1.actualizationRouter = new ActualizationRouter();
Backbone.history.start();

