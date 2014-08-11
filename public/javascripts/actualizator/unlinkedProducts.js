var UnlinkedProduct = Backbone.Model.extend();

var UnlinkedProducts = Backbone.Paginator.requestPager.extend({
    model: UnlinkedProduct,

    setProviderId: function(providerId){
        this.providerId = providerId;
    },

    url: function(){
        return '/clients/'+clientId+'/providers/' + this.providerId + "/products";
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
        'linked' : 'true'
    },

    parse: function (response) {
        this.totalPages = Math.floor(response.totalCount/this.perPage) + 1;
        this.totalRecords = this.totalPages * this.perPage;
        return response.collectionResult;
    }
});

var UnlinkedProductView = Backbone.View.extend({
    tagName: 'a',
    className: 'list-group-item cursorPointer',

    events: {
        'click' : 'onClick'
    },


    initialize: function(){
        this.template = _.template(tpl.get('unlinkedProductTpl').text);

        this.model.on('change', this.render, this);
        this.model.on('remove', this.remove, this);
    },

    render: function(){
        console.log("Render UnlinkedProductView to a UnlinkedProduct model with id: " + this.model.cid);
        this.$el.attr('id', this.model.cid);
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    onClick: function(){
        console.log('Selected unlinkedProduct with name : ' + this.model.get('name'));
        actVent.trigger('unlinked:selected', this.model.get('name'));
    }
});

var UnlinkedProductsView = Backbone.View.extend({
    tagName: 'div',
    className: 'list-group',

    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.collection.on('change', this.render, this);
        this.collection.on('reset', this.render, this);
//        this.collection.pager();
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