var app1 = app1 || {};

var UnlinkedSearch = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    template: _.template($('#unlinkedSearchTpl').html()),

    initialize: function(options){
        this.options = options;
    },

    events: {
        'click #unlinkedSearchBtn' : 'searchText',
        'keydown #searchText' : function(e){
            if(e.keyCode == 13) {
                e.preventDefault();
                this.searchText();
            }
        }
    },

    render: function(text){
        var template = this.template({text: text});
        this.$el.html( template );
        return this;
    },

    searchText: function(){
        var text = this.$el.find('#searchText').val();
        if (text == '')
            return;
        else
            app1.actualizationRouter.navigate('/providers/' + this.options.providerId+ "/linkProducts/search/" + text, {trigger: true});
    }
});

var UnlinkedSearchResult = Backbone.Model.extend({

});

var UnlinkedSearchResults = Backbone.Paginator.requestPager.extend({
    model: UnlinkedSearchResult,

    url: function(){
        return "/clients/" + clientId + "/providers/" + this.providerId + "/products";
    },

    setProviderId: function(providerId){
        this.providerId = providerId;
    },

    setWords: function(words){
        this.words = words;
    },

    paginator_core: {
        type: 'GET',
        dataType: 'json',
        url: function(){
            return this.url();
        }
    },

    paginator_ui: {
        firstPage: 1,
        currentPage: 1,
        perPage: 10,
        totalPages: 10
    },

    server_api: {
        'offset': function() { return this.currentPage * this.perPage - this.perPage},
        'limit' : function() { return this.perPage},
        'words' : function() { return this.words;},
        'linked' : 'true'
    },

    parse: function (response) {
        this.totalPages = Math.floor(response.totalCount/this.perPage) + 1;
        this.totalRecords = this.totalPages * this.perPage;
        return response.collectionResult;
    }
});

var UnlinkedSearchResultView = Backbone.View.extend({
    tagName: 'a',
    className: 'list-group-item cursorPointer',

    events: {
        'click' : 'onClick'
    },

    template: _.template($('#unlinkedProductTpl').html()),

    initialize: function(){
        this.model.on('change', this.render, this);
        this.model.on('remove', this.remove, this);
    },

    render: function(){
        console.log("Render UnlinkedProductView to a UnlinkedProduct model with id: " + this.model.id);
        this.$el.attr('id', this.model.id);
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    onClick: function(){
        console.log('Selected unlinkedProduct with name : ' + this.model.get('name'));
        actVent.trigger('unlinked:selected', this.model.get('name'));
    }
});


var UnlinkedSearchResultsView = Backbone.View.extend({
    tagName: 'div',
    className: 'list-group',

    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.render, this);
    },
    render: function(){
        this.$el.empty();
        this.collection.each(this.addOne, this);
        return this;
    },
    addOne: function(unlinkedProduct){
        console.log("Adding the UnlinkedProduct view (model.name = " + unlinkedProduct.get('name') + ")to UnlinkedProductsView : start...");
        var unlinkedSearchResultView = new UnlinkedSearchResultView({model: unlinkedProduct});
        this.$el.append(unlinkedSearchResultView.render().el);
    }
});
