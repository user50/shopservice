var app1 = app1 || {};

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'providers/:providerId/linkProducts' : 'linkProducts'
    },
    start: function(){
        console.log('Hello!');
        app1.ProviderPage.$el.show();
        app1.UnlinkedPage.$el.hide();
    },

    linkProducts: function(providerId){
        console.log('Link products of provider: ' + providerId);
        app1.ProviderPage.$el.hide();
        app1.UnlinkedPage.setProvider(app1.ProviderPage.Providers.get(providerId));
        app1.UnlinkedPage.$el.show();
    }
});

app1.actualizationRouter = new ActualizationRouter();
Backbone.history.start();

var actVent = _.extend({}, Backbone.Events);