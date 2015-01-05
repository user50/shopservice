var ProductsPage = Backbone.View.extend({
    tagName: 'div',

    render: function(){
        this.$el.append(new SwitchButtons().render().el);

        this.ProductSearch = new ProductSearch();
        this.$el.append(this.ProductSearch.render().el);

        return this;
    }
});