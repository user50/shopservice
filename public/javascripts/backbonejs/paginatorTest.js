var app = app || {};

var Product = Backbone.Model.extend({
    toggle: function(){
        if (this.attributes.checked == true){
            app.Counter.set('count', app.Counter.get('count') - 1)
        } else {
            app.Counter.set('count', app.Counter.get('count') + 1)
        }
        this.save({
            checked: !this.get('checked')
        }, { groupId: currentGroupId});
    },

    checked: function(checked){
        if (checked == true && this.attributes.checked != true) {
            app.Counter.set('count', app.Counter.get('count') + 1);
            this.set('checked', true);
        }
        if (checked == false && this.attributes.checked != false) {
            app.Counter.set('count', app.Counter.get('count') - 1);
            this.set('checked', false);
        }
        console.log("Product with id " + this.attributes.id + " checked as " + checked);
    }
});

var Products = Backbone.Paginator.requestPager.extend({


    // As usual, let's specify the model to be used
    // with this collection
    model: Product,

    // We're going to map the parameters supported by
    // your API or backend data service back to attributes
    // that are internally used by Backbone.Paginator.

    // e.g the GitHub API refers to it's parameter for
    // stating how many results to skip ahead by as $skip
    // and it's number of items to return per page as $top

    // We simply map these to the relevant Paginator equivalents

    // Note that you can define support for new custom attributes
    // adding them with any name you want
    url: function(){
        return "/clients/" + clientId + "/groups/" + currentGroupId + "/products"
    },
    paginator_core: {
        // the type of the request (GET by default)
        type: 'GET',

        // the type of reply (jsonp by default)
        dataType: 'json',

        // the URL (or base URL) for the service
        url: function(){
            return this.url();
        }
    },

    paginator_ui: {
        // the lowest page index your API allows to be accessed
        firstPage: 1,

        // which page should the paginator start from
        // (also, the actual page the paginator is on)
        currentPage: 1,

        // how many items per page should be shown
        perPage: 10,

        // a default number of total pages to query in case the API or
        // service you are using does not support providing the total
        // number of pages for us.
        // 10 as a default in case your service doesn't return the total
        totalPages: 10
    },

    server_api: {
        'offset': function() { return this.currentPage * this.perPage - this.perPage},
        'limit' : function() { return this.perPage},
        'categoryId' : function(){ return currentCategoryId}
    },

    parse: function (response) {
        // Normally this would be parsed from the response,
        // but GitHub doesn't make this readily available
        this.totalPages = Math.floor(response.totalCount/this.perPage) + 1;

        this.totalRecords = this.totalPages * this.perPage;

        // Be sure to change this based on how your results
        // are structured (e.g response.data is GitHub specific)
        return response.collectionResult;
    }

});

var ProductView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#productTemplate').html()),

    initialize: function() {
        this.model.bind('change', this.render, this);
        this.model.bind('remove', this.remove, this);
    },

    events: {
        'click .productCheck' : 'productToggle'
    },

    render : function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    },

    productToggle: function(){
        this.model.toggle();
    }
});

var ProductsView = Backbone.View.extend({
    el : '#productsTable',

    initialize : function () {
        var tags = this.collection;

        tags.on('add', this.addOne, this);
//        tags.on('all', this.render, this);

        tags.pager();

    },

    addOne : function ( item ) {
        var view = new ProductView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        this.renderHeader();
    },

    renderHeader: function(){
        this.$el.append($('<col width="20px">'));
        this.$el.append($('<col>'));
        this.$el.append($('<col width="10px">'));
        this.$el.append($('<col width="20px">'));

        var tr = $('<tr/>');

        var checkAll = $('<input>');
        checkAll.attr('id', 'check_all_products');
        checkAll.attr('type', 'checkbox');
        var th = $('<th></th>');
        th.append(checkAll);
        tr.append(th);
        tr.append("<th>Product Name</th>");
        tr.append("<th>Price, UAH</th>");
        tr.append("<th>Published</th>");
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});

var PaginationView = Backbone.View.extend({

    events: {
        'click a.servernext': 'nextResultPage',
        'click a.serverprevious': 'previousResultPage',
        'click a.serverlast': 'gotoLast',
        'click a.page': 'gotoPage',
        'click a.serverfirst': 'gotoFirst',
        'click a.serverpage': 'gotoPage'
    },

    tagName: 'aside',

    template: _.template($('#tmpServerPagination').html()),

    initialize: function () {
        this.listenTo(vent, 'selectedCategory',
            function(){
                this.gotoFirst();
            }, this);

        this.collection.on('reset', this.render, this);
        this.collection.on('sync', this.render, this);

        this.$el.appendTo('#pagination');

    },

    render: function () {
        var html = this.template(this.collection.info());
        this.$el.html(html);
    },

    nextResultPage: function (e) {
        e.preventDefault();
        this.collection.requestNextPage();
    },

    previousResultPage: function (e) {
        e.preventDefault();
        this.collection.requestPreviousPage();
    },

    gotoFirst: function () {
//        e.preventDefault();
        this.collection.goTo(this.collection.information.firstPage);
    },

    gotoLast: function (e) {
        e.preventDefault();
        this.collection.goTo(this.collection.information.lastPage);
    },

    gotoPage: function (e) {
        e.preventDefault();
        var page = $(e.target).text();
        this.collection.goTo(page);
    }
});

app.Products = new Products();
app.ProductsView = new ProductsView({collection: app.Products});
app.pagination = new PaginationView({collection:app.Products});
