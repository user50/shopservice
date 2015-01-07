var ProductsPage = Backbone.View.extend({
    tagName: 'div',

    initialize: function(options){
        this.categoryId = options.categoryId;
    },

    render: function(){
        this.$el.append(new SwitchButtons().render().el);
        this.$el.append(new ProductSearch().render().el);

        this.Products = new Products([], {categoryId: this.categoryId});
        this.ProductsView = new ProductsView({collection: this.Products});
        this.ProductsPaginationView = new PaginationView({collection: this.Products});
        this.Products.fetch({wait: true});

        var rowDiv = $('<div class="row clearfix">');
        var colDiv = $('<div class="col-md-6 column">');
        colDiv.append(this.ProductsView.render().el);
        colDiv.append(this.ProductsPaginationView.render().el);
        rowDiv.append(colDiv);
        this.$el.append(rowDiv);

        return this;
    }
});


var PaginationView = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    events: {
        'click a.servernext': 'nextResultPage',
        'click a.serverprevious': 'previousResultPage',
        'click a.serverlast': 'gotoLast',
        'click a.page': 'gotoPage',
        'click a.serverfirst': 'gotoFirst',
        'click a.serverpage': 'gotoPage',
        'click .btn-group button': 'changeCount'
    },


    initialize: function () {
        this.template = _.template(tpl.get('paginationTpl').text);
        this.collection.on('reset', this.render, this);
        this.collection.on('sync', this.render, this);
    },

    render: function () {
        var html = this.template(this.collection.info());
        this.$el.html(html);
        return this;
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
        e.preventDefault();
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
    },

    changeCount: function (e) {
        e.preventDefault();
        var per = $(e.target).text();
        this.collection.howManyPer(per);
    }
});