var app1 = app1 || {};

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'providers/:providerId/linkProducts' : 'linkProducts'
    },
    start: function(){
        console.log('Hello!');
        app1.ProviderPage.$el.show();
    },

    linkProducts: function(providerId){
        console.log('Link products of provider: ' + providerId);
        app1.ProviderPage.$el.hide();
    }
});

app1.actualizationRouter = new ActualizationRouter();
Backbone.history.start();

var actVent = _.extend({}, Backbone.Events);