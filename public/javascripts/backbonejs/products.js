var app = app || {};

var Product = Backbone.Model.extend({});

var ProductView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#productTemplate').html()),

    initialize: function(){
        this.model.on('change', this.render, this);
    },
    render: function(){
        console.log("Render ProductView to a product model with id: " + this.model.id);
        var template = this.template(this.model.toJSON())
        this.$el.html( template );
        return this;
    }
});

var Products = Backbone.Collection.extend({
    model: Product,

    initialize: function(){
        this.on('selectedCategory',
            function(currentCategory){
                this.fetch({
                    data: $.param({ categoryId: currentCategory}), reset: true});
            }, this);
    },

    url: "/clients/" + clientId + "/groups/" + currentGroupId + "/products"
});

var ProductsView = Backbone.View.extend({
    el: '#productsTable',

    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.collection.on('reset', this.render, this);
    },

    render: function(){
        this.$el.empty();
        this.collection.each(this.addOne, this);
        return this;
    },

    addOne: function(product){
        var productView = new ProductView({model: product});
        this.$el.append(productView.render().el);
    }
});

app.Products = new Products();
app.ProductsView = new ProductsView({collection: app.Products});