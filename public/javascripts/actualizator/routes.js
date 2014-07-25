var app1 = app1 || {};

var actVent = _.extend({}, Backbone.Events);

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start',
        'providers/:providerId/linkProducts' : 'linkProducts',
        'providers/:providerId/linkProducts/search/:text' : 'unlinkedSearch',
        'providers/:providerId/linkingProduct/:name' : 'linkingProduct'
    },

    initialize: function(){

    },

    start: function(){
        console.log('Hello!');
        if (this.ProviderPage != null){
            this.ProviderPage.remove();
        }
        this.ProviderPage = new ProviderPage();
        $('#content').html(this.ProviderPage.render().el);
    },

    linkProducts: function(providerId){
        if (this.UnlinkedPage != null){
            this.UnlinkedPage.remove();
        }
        this.UnlinkedPage = new UnlinkedPage({providerId: providerId});
        $('#content').html(this.UnlinkedPage.render().el);
    },

    unlinkedSearch: function(providerId, text){
        if (this.UnlinkedPage != null){
            this.UnlinkedPage.remove();
        }
        this.UnlinkedPage = new UnlinkedPage({providerId: providerId});
        this.UnlinkedPage.search(text);
        $('#content').html(this.UnlinkedPage.el);
    },

    linkingProduct: function(providerId, name){
        console.log("Linking for unlinked product " + name +
            'of provider ' + providerId);
        if (this.LinkingPage != null){
            this.LinkingPage.remove();
        }
        this.LinkingPage = new LinkingPage({providerId: providerId, providerProductName: name});
        $('#content').html(this.LinkingPage.render().el);
    }
});
app1.actualizationRouter = new ActualizationRouter();
Backbone.history.start();

