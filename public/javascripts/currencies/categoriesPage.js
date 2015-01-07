var CategoriesPage = Backbone.View.extend({
    tagName: 'div',

    initialize: function(){
        this.ProductSearch = new ProductSearch();

        this.Categories = new Categories();
        this.CategoriesView = new CategoriesView({collection: this.Categories})
    },

    render: function(){
        this.Categories.fetch();

        this.$el.append(new SwitchButtons().render().el);
        this.$el.append(this.ProductSearch.render().el);

        var rowDiv = $('<div class="row clearfix">');
        var colDiv = $('<div class="col-md-6 column">');
        colDiv.append(this.CategoriesView.render().el);
        rowDiv.append(colDiv);
        this.$el.append(rowDiv);

        return this;
    }
});