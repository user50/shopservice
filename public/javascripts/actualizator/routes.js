

var ActualizationRouter = Backbone.Router.extend({
    routes: {
        '' : 'start'
//        'providers/:providerId/linkProducts' : 'linkProducts',
//        'providers/:providerId/linkProducts/search/:text' : 'unlinkedSearch',
//        'providers/:providerId/linkingProduct/:name' : 'linkingProduct',
//        'providers/:providerId/linkingProduct/:name/search/:text' : 'linkingProductSearch',
//        'providers/:providerId/linkedProducts' : 'linkedProducts',
//        'providers/:providerId/linkedProducts/search/:text' : 'linkedSearch'
    },

    initialize: function(){
        $('#header').html(new HeaderView().render().el);
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
        $('#content').html(this.UnlinkedPage.search(text).el);
    },

    linkingProduct: function(providerId, name){
        console.log("Linking for unlinked product " + name +
            'of provider ' + providerId);
        if (this.LinkingPage != null){
            this.LinkingPage.remove();
        }
        this.LinkingPage = new LinkingPage({providerId: providerId, providerProductName: name});
        $('#content').html(this.LinkingPage.render().el);
    },

    linkingProductSearch: function(providerId, name, text){
        if (this.LinkingPage != null){
            this.LinkingPage.remove();
        }
        this.LinkingPage = new LinkingPage({providerId: providerId, providerProductName: name});
        $('#content').html(this.LinkingPage.search(text).el);
    },

    linkedProducts: function(providerId){
        console.log("Linked products for provider " + providerId);
        if (this.LinkedPage != null){
            this.LinkedPage.remove();
        }
        this.LinkedPage = new LinkedPage({providerId: providerId});
        $('#content').html(this.LinkedPage.render().el);
    }
});

tpl.loadTemplates(function () {
    clientId = "client1";
    actVent = _.extend({}, Backbone.Events);

    actualizationRouter = new ActualizationRouter();
    Backbone.history.start();
});


