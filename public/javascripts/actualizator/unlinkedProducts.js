var app1 = app1 || {};

var clientId = "client1";

var UnlinkedProduct = Backbone.Model.extend();

var UnlinkedProducts = Backbone.Collection.extend({
    model: UnlinkedProduct,

    setProviderId: function(providerId){
        this.providerId = providerId;
    },

    url: function(){
        return '/clients/'+clientId+'/providers/' + this.providerId + "/products?linked=true";
    }
});

var UnlinkedProductView = Backbone.View.extend({
    tagName: 'a',
    className: 'list-group-item cursorPointer',

    events: {
        'click' : 'onClick'
    },

    template: _.template($('#unlinkedProductTpl').html()),

    initialize: function(){
        this.model.on('change', this.render, this);
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
//        currentCategoryId = this.model.id;
//        app.router.navigate('groups/' + currentGroupId + '/categories/' + this.model.id, {trigger: true});
    }
});

var UnlinkedProductsView = Backbone.View.extend({
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
        var unlinkedProductView = new UnlinkedProductView({model: unlinkedProduct});
        this.$el.append(unlinkedProductView.render().el);
    }
});