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
                this.fetch({
                    data: $.param({ categoryId: currentCategory, offset: 0, limit: 10}), reset: true});
            }, this);
    },

    url: function(){
        return "/clients/" + clientId + "/groups/" + currentGroupId + "/products"
    },

    parse: function(response){
        return response.collectionResult;
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

var PaginationView = Backbone.View.extend({
    el: '#pagination',
    template: _.template($("#pagination-view").html()), // шаблон
    link: "", // ссылка
    page_count: null, // кол-во страниц
    page_active: null, // активная страница
    page_show: 5, // кол-во страниц в блоке видимости
    attributes: { // атрибуты элемента
        "class": "pagination"
    },
    initialize: function(params) { // конструктор
        this.link = params.link;
        this.page_count = params.page_count;
        if (this.page_count <= this.page_show) {
            this.page_show = this.page_count;
        }
        this.page_active = params.page_active;
    },

    render: function() {
        var range = Math.floor(this.page_show / 2);
        var nav_begin = this.page_active - range;
        if (this.page_show % 2 == 0) { // Если четное кол-во
            nav_begin++;
        }
        var nav_end = this.page_active + range;
        var left_dots = true;
        var right_dots = true;
        if (nav_begin <= 2) {
            nav_end = this.page_show;
            if (nav_begin == 2) {
                nav_end++;
            }
            nav_begin = 1;
            left_dots = false;
        }

        if (nav_end >= this.page_count - 1 ) {
            nav_begin = this.page_count - this.page_show + 1;
            if (nav_end == this.page_count - 1) {
                nav_begin--;
            }
            nav_end = this.page_count;
            right_dots = false;
        }

        $(this.el).html( this.template({
            link: this.link,
            page_count: this.page_count,
            page_active: this.page_active,
            nav_begin: nav_begin,
            nav_end: nav_end,
            left_dots: left_dots,
            right_dots: right_dots
        }) );
        return this;
    }
});

app.pagination = new PaginationView({
    link: "#products/page/",
    page_count: 10,
    page_active: 5
});
app.pagination.render();
app.Products = new Products();
app.ProductsView = new ProductsView({collection: app.Products});