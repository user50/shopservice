var Category = Backbone.Model.extend({});

var Categories = Backbone.Collection.extend({
    model: Category,

    url: '/domosed/categories'
});

var CategoryView = Backbone.View.extend({
    tagName: 'a',
    className: 'list-group-item',
    attributes: {
        'href' : '#products'
    },

    events: {
        'click' : 'onClick'
    },

    render: function(){
        this.$el.text(this.model.get('name'));
        return this;
    },

    onClick: function(event){
        event.preventDefault();
        console.log('Category ' + this.model.get('name') + ' was clicked');
        currenciesRouter.navigate("/products/" + this.model.id, {trigger: true});
    }
});

var CategoriesView = Backbone.View.extend({
    tagName: 'div',
    className: 'list-group',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( category ) {
        var view = new CategoryView({model:category});
        this.$el.append(view.render().el);
    },

    render: function(){
        this.$el.empty();

        var headerRow = $('<a href="#products" class="list-group-item active">Категории товаров</a>');
        this.$el.append(headerRow);

        this.collection.each(this.addOne, this);
        return this;
    }
});