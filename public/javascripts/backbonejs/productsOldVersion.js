var app = app || {};

var Product = Backbone.Model.extend({
//    urlRoot: function(){
//        return "/clients/" + clientId + "/groups/" + currentGroupId + "/products";
//    },
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

var ProductView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#productTemplate').html()),

    initialize: function(){
        this.model.on('change', this.render, this);
    },

    events: {
        'click .productCheck' : 'productToggle'
    },

    initialize: function(){
        this.model.on('change', this.render, this);
    },
    render: function(){
        console.log("Render ProductView to a product model with id: " + this.model.id);
        var template = this.template(this.model.toJSON())
        this.$el.html( template );
        return this;
    },

    productToggle: function(e){
        this.model.toggle();
    }

});

var Products = Backbone.Collection.extend({
    model: Product,

    initialize: function(){
        this.on('selectedCategory',
            function(currentCategory){
//                this.fetch({
//                    data: $.param({ categoryId: currentCategory, offset: 0, limit: 10}), reset: true});
                this.updatePage(1);
            }, this);

        this.on('updatePage', this.updatePage, this);
    },

    url: function(){
        return "/clients/" + clientId + "/groups/" + currentGroupId + "/products"
    },

    parse: function(response){
        app.PaginationModel.set('page_count', Math.floor(response.totalCount/10)+1);
        return response.collectionResult;
    },

    updatePage: function(page){
        app.PaginationModel.set('page_active', page);

        if (page > 1)
            var offset = page*10 - 10;
        else var offset = 0;

        this.fetch({
            data: $.param({ categoryId: currentCategoryId, offset: offset, limit: 10}), reset: true});
    }
});

var ProductsView = Backbone.View.extend({
    el: '#productsTable',

    initialize: function(){
        this.collection.on('add', this.render, this);
        this.collection.on('reset', this.render, this);
    },

    events: {
        'change #check_all_products' : 'checkAll'
    },

    render: function(){
        this.$el.empty();
        this.renderHeader();
        if (this.collection.length == this.collection.where({checked: true}).length){
            console.log("All products are checked!");
            this.$el.find('#check_all_products').prop('checked', 'checked');
        }

        this.collection.each(this.addOne, this);
        return this;
    },

    addOne: function(product){
        var productView = new ProductView({model: product});
        this.$el.append(productView.render().el);
    },

    checkAll: function(e){
        console.log("Check all products...");

        var url = "/clients/"+clientId + "/groups/" + currentGroupId + "/products?categoryId=" + currentCategoryId;
        var body = {checked: e.currentTarget.checked};

        $.ajax({
            url:url,
            type:"put",
            data: JSON.stringify(body),
            contentType: 'application/json'
        });

        this.collection.each(function(product){
            product.checked(e.currentTarget.checked);
        }, this);
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

var PaginationModel = Backbone.Model.extend({});

var PaginationView = Backbone.View.extend({
    el: '#pagination',
    template: _.template($("#pagination-view").html()), // шаблон

    attributes: {
        "class": "pagination"
    },
    initialize: function() {
        this.model.bind('change', this.render, this);
    },

    render: function() {

        $(this.el).html( this.template({
            link: this.model.attributes.link,
            page_count: this.model.attributes.page_count,
            page_active: this.model.attributes.page_active
        }) );
        return this;
    }
});

app.PaginationModel = new PaginationModel({
    link: "#products/page/"
});
app.pagination = new PaginationView({model: app.PaginationModel});
app.Products = new Products();
app.ProductsView = new ProductsView({collection: app.Products});