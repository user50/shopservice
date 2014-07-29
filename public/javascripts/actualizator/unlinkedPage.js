var app1 = app1 || {};


var UnlinkedPage = Backbone.View.extend({
    tagName: 'div',

    events: {
    },

    initialize: function(options){
        this.options = options;

        this.providerId = this.options.providerId; // провайдер, для якого рендериться сторінка

        console.log('Link products of provider: ' + this.providerId);

        this.Provider = new Provider({id: this.providerId});
        this.Provider.fetch();

        this.UnlinkedBreadcrumbsView = new UnlinkedBreadcrumbsView({model: this.Provider});
        this.LinkAutomaticBtn = new LinkAutomaticBtn();

        this.UnlinkedProducts = new UnlinkedProducts();

        this.listenTo(actVent, 'unlinked:selected', function(unlinkedName){
            app1.actualizationRouter.navigate('providers/' + this.providerId+ "/linkingProduct/" + unlinkedName, {trigger: true});
        });

        this.listenTo(actVent, 'unlinked:autolink', function(){
            this.UnlinkedProducts.fetchAutolink();
        })
    },

    render: function(){
        this.$el.empty();
        this.UnlinkedProducts.setProviderId(this.providerId);
        this.UnlinkedProductsView = new UnlinkedProductsView({collection: this.UnlinkedProducts});
        this.UnlinkedProducts.fetch();

        this.UnlinkedSearch = new UnlinkedSearch({providerId: this.providerId});

        this.$el.append(this.UnlinkedBreadcrumbsView.render().el);
        this.$el.append(this.LinkAutomaticBtn.render().el);
        this.$el.append(this.UnlinkedSearch.render('').el);
        this.$el.append(this.UnlinkedProductsView.render().el);
        return this;
    },

    search: function(text){
        this.$el.empty();

        console.log("Search texts: " + text);

        this.UnlinkedSearch = new UnlinkedSearch({providerId: this.providerId});
        this.UnlinkedSearchResults = new UnlinkedSearchResults();
        this.UnlinkedSearchResults.setProviderId(this.providerId);
        this.UnlinkedSearchResults.setWords(text);
        this.UnlinkedSearchResultsView = new UnlinkedSearchResultsView({collection: this.UnlinkedSearchResults});
        this.UnlinkedSearchResults.fetch();

        this.$el.append(this.UnlinkedBreadcrumbsView.render().el);
        this.$el.append(this.LinkAutomaticBtn.render().el);
        this.$el.append(this.UnlinkedSearch.render(text).el);
        this.$el.append(this.UnlinkedSearchResultsView.render().el);
        return this;
    }

});

var LinkAutomaticBtn = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    template: _.template($('#linkAutomaticBtn').html()),

    events: {
        'click' : function(){actVent.trigger('unlinked:autolink')}
    },

    render: function(){
        var template = this.template();
        this.$el.html( template );
        return this;
    }

});