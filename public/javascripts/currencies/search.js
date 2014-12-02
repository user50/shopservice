
var SearchPage = Backbone.View.extend({
    tagName: 'div',

    initialize: function(){
        this.SearchResultProducts = new SearchResultProducts();
        this.SearchResultsView = new SearchResultsView({collection: this.SearchResultProducts});
    },

    render: function(text){
        this.ProductSearch = new ProductSearch();
        this.$el.append(this.ProductSearch.render(text).el);

        this.SearchResultProducts.setText(text);
        this.SearchResultProducts.fetch({wait: true});

        var rowDiv = $('<div class="row clearfix">');
        var colDiv = $('<div class="col-md-6 column">');
        colDiv.append(this.SearchResultsView.render().el);
        rowDiv.append(colDiv);
        this.$el.append(rowDiv);

        this.$el.append(new BackButton().render().el);
        return this;
    }
});

var ProductSearch = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    initialize: function(options){
        this.options = options;
        this.template = _.template(tpl.get('productSearchTpl').text);
    },

    events: {
        'click #searchBtn' : 'searchText',
        'keydown #searchPhrase' : function(e){
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
        var text = this.$el.find('#searchPhrase').val();
        if (text == '')
            return;
        else
            currenciesRouter.navigate("/search/" + text, {trigger: true});
    }
});