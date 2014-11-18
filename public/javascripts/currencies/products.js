
var Product = Backbone.Model.extend({});

var SearchResultProducts = Backbone.Collection.extend({
    model: Product,

    url: function(){
        return '/clients/' + clientId + '/products?offset=0&limit=10&like=' + this.searchExpression;
    },

    setText: function(searchExpression){
        this.searchExpression = searchExpression;
    },

    parse: function (response) {
        return response.collectionResult;
    }
});

var SearchResultView = Backbone.View.extend({
    tagName: 'tr',

    initialize: function(){
        this.template = _.template(tpl.get('searchResultViewTpl').text);
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    }
});

var  SearchResultsView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        var view = new SearchResultView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of SearchResultsView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        var th = $('<th class="col-md-3">Название</th>');
        tr.append(th);
        tr.append('<th  class="col-md-1">Цена, у.е.</th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});