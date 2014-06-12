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
    model: Product,
    url: function(){
        return "/clients/" + clientId + "/groups/" + currentGroupId + "/products"
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
        'categoryId' : function(){ return currentCategoryId}
    },

    // Fetch method when called triggers 'fetch' event. That way we can
    // set loading state on components.
    fetch: function(options) {
        this.trigger('fetch', this, options);
        return Backbone.Collection.prototype.fetch.call(this, options);
    },

    parse: function (response) {
        this.totalPages = Math.floor(response.totalCount/this.perPage) + 1;
        this.totalRecords = this.totalPages * this.perPage;
        return response.collectionResult;
    }

});

var ProductView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#productViewTemplate').html()),

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
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
        tags.on('fetch', this.test, this);
        tags.pager();
    },

    events: {
        'change #check_all_products' : 'checkAllOnPage'
    },

    test: function(){
        console.log("products are fetched..");
    },

    addOne : function ( item ) {
        var view = new ProductView({model:item});
        this.$el.append(view.render().el);
    },

    checkAllOnPage: function(e){
        console.log("Check all products on the page...");

        var url = "/clients/"+clientId + "/groups/" + currentGroupId + "/products?checked=" + e.currentTarget.checked;
        var productIdsToUpdate = [];

        for(i = 0; i < this.collection.length; i++){
            productIdsToUpdate[i] = this.collection.at(i).id;
        }

        $.ajax({
            url:url,
            type:"put",
            data: JSON.stringify(productIdsToUpdate),
            contentType: 'application/json'
        });

        this.collection.each(function(product){
            product.checked(e.currentTarget.checked);
        }, this);
    },

    render: function(){
        console.log('Rendering of ProductsView...');
        this.$el.empty();
        this.renderHeader();
//        if (this.collection.length == 0){
//            var emptyRow = $('<tr/>');
//            emptyRow.append($('<td/>'));
//            emptyRow.append($('<td/>').text('Products not found.'));
//            emptyRow.append($('<td/>'));
//            emptyRow.append($('<td/>'));
//            this.$el.append(emptyRow);
//            return this;
//        }
//        if (this.collection.length == this.collection.where({checked: true}).length){
//            console.log("All products are checked!");
//            this.$el.find('#check_all_products').prop('checked', 'checked');
//        } else {
//            console.log("All products are not checked!");
//            this.$el.find('#check_all_products').prop('checked', '');
//        }
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
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

    el: '.pagination',

    template: _.template($('#tmpServerPagination').html()),

    initialize: function () {
        this.listenTo(vent, 'selectedCategory',
            function(){
                this.gotoFirst();
            }, this);

        this.collection.on('reset', this.render, this);
        this.collection.on('sync', this.render, this);
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
