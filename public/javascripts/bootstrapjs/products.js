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
        perPage: 15,
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
        'click .productCheck' : 'productToggle',
        'click .btn-warning' : 'editThis'
    },

    render : function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    },

    productToggle: function(){
        this.model.toggle();
    },

    editThis: function(){
        vent.trigger('product:edit', this.model.id);
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

        if (this.collection.length == 0){
            var emptyRow = $('<tr/>');
            emptyRow.append($('<th/>').text('Здесь нет товаров.'));
            this.$el.append(emptyRow);
            return this;
        }

        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        var checkAll = $('<input>');
        checkAll.attr('id', 'check_all_products');
        checkAll.attr('type', 'checkbox');
        var th = $('<th class="col-md-1"></th>');
        th.append(checkAll);
        tr.append(th);
        tr.append('<th  class="col-md-5">Наименование товара</th>');
        tr.append('<th class="col-md-3">Цена</th>');
        tr.append('<th class="col-md-2">Опубликован</th>');
        tr.append('<th class="col-md-1"></th>');
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
        'click a.serverpage': 'gotoPage',
        'click .btn-group button': 'changeCount'
    },

    el: '#paginationView',

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
    },

    changeCount: function (e) {
        e.preventDefault();
        var per = $(e.target).text();
        this.collection.howManyPer(per);
    }
});

var SearchResult = Backbone.Model.extend({
    toggle: function(){
//        if (this.attributes.checked == true){
//            app.Counter.set('count', app.Counter.get('count') - 1)
//        } else {
//            app.Counter.set('count', app.Counter.get('count') + 1)
//        }
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

var SearchResults = Backbone.Paginator.requestPager.extend({
    model: SearchResult,

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
        perPage: 15,
        totalPages: 10
    },

    server_api: {
        'offset': function() {
            if (this.newQuery){
                this.newQuery = false;
                this.currentPage = 1;
            }
            return this.currentPage * this.perPage - this.perPage
        },
        'limit' : function() { return this.perPage},
//        'categoryId' : function(){
//                                    if (currentCategoryId != -1){
//                                        return currentCategoryId;
//                                    } else  {
//                                        return null;
//                                    }
//                                },
        'words' : function(){ return this.words }
    },

    setWords: function(words){
        this.words = words;
        this.newQuery = true;
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

var SearchResultView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#searchResultViewTemplate').html()),

    initialize: function() {
        this.model.on('change', this.render, this);
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

var SearchResultsView = Backbone.View.extend({
    el: '#searchResultsTable',

    initialize: function(){
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
//        tags.pager();
    },

    events: {
        'change #check_all_searched_products' : 'checkAllOnPage'
    },

    render: function(){
        console.log('Rendering of SearchResultsView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
        },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        var checkAll = $('<input>');
        checkAll.attr('id', 'check_all_searched_products');
        checkAll.attr('type', 'checkbox');
        var th = $('<th class="col-md-1"></th>');
        th.append(checkAll);
        tr.append(th);
        tr.append('<th class="col-md-3">Категория</th>');
        tr.append('<th  class="col-md-5">Наименование товара</th>');
        tr.append('<th class="col-md-2">Цена</th>');
        tr.append('<th class="col-md-1">Опубликован</th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    },

    addOne : function ( item ) {
        console.log('add one search result');
        var view = new SearchResultView({model:item});
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
    }
});

var SearchResultsPaginationView = Backbone.View.extend({

    events: {
        'click a.servernext': 'nextResultPage',
        'click a.serverprevious': 'previousResultPage',
        'click a.serverlast': 'gotoLast',
        'click a.page': 'gotoPage',
        'click a.serverfirst': 'gotoFirst',
        'click a.serverpage': 'gotoPage'
    },

    el: '#searchResultsPagination',

    template: _.template($('#searchResultsPaginationTmp').html()),

    initialize: function () {
//        this.listenTo(vent, 'selectedCategory',
//            function(){
//                this.gotoFirst();
//            }, this);

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


var SearchView = Backbone.View.extend({
    el: '#searchDiv',

    events: {
        'click #sendSearchTextsBtn' : 'sendSearchQuery',
        'keydown #searchText' : 'search'
    },

    initialize: function(){
        this.listenTo(vent, 'group: deleted', this.hideSearchForm);
        this.listenTo(vent, 'group: selected', this.showSearchForm);

        this.searchResults = new SearchResults();
        this.searchResultsView = new SearchResultsView ({collection: this.searchResults});
        this.searchResultsPaginationView = new SearchResultsPaginationView({collection: this.searchResults});

        this.$el.find('#searchForm').hide();
        this.$el.find('#searchResultsTable').hide();
        this.$el.find('#searchQueryLabel').hide();
        this.searchResultsPaginationView.$el.hide();
    },

    search: function(e){
        if(e.keyCode == 13) {
            e.preventDefault();
            this.sendSearchQuery();
        }
    },

    sendSearchQuery: function(){
        var text = this.$el.find('#searchText').val();

        if (text == '')
            return;

        console.log("Search texts: " + text);
        this.$el.find('#searchQueryLabel').html('Результаты поиска по запросу: <em><strong>' + text + '</em></strong>');
        this.$el.find('#searchQueryLabel').show();

        this.searchResults.setWords(text);

        this.searchResultsPaginationView.collection.pager();

        this.searchResultsView.$el.show();
        this.searchResultsPaginationView.$el.show();

        app.router.navigate('groups/' + currentGroupId + "/search=" + text, {trigger: true});
    },

    hideSearchForm: function(){
        this.$el.find('#searchForm').hide();
    },

    showSearchForm: function(){
        this.$el.find('#searchForm').show();
    },

    hideResults: function(){
        this.$el.find('#searchForm').show();
        this.$el.find('#searchText').val('');
        this.$el.find('#searchQueryLabel').hide();
        this.searchResultsView.$el.hide();
        this.searchResultsPaginationView.$el.hide();
    }
});

app.Products = new Products();
app.ProductsView = new ProductsView({collection: app.Products});
app.pagination = new PaginationView({collection:app.Products});app.pagination.$el.hide();

var searchResultsStub = JSON.parse('[{"id":"0889119c-d6ef-1004-8e43-b7d03cabe5c7","categoryId":"35","productName":"Парта детская Спорт (Эдисан) PR-01","price":1490.0,"url":"parta-detskaya-sport-edican-pr-01.html","published":true,"checked":true},{"id":"0889119d-d6ef-1004-8e43-b7d03cabe5c7","categoryId":"35","productName":"Парта детская Полиция (Эдисан) PR-01","price":1490.0,"url":"parta-detskaya-politsiya-edican-pr-01.html","published":true,"checked":false}]');

app.SearchView = new SearchView();