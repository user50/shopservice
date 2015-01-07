
var Product = Backbone.Model.extend({
    urlRoot: function(){
        return '/domosed/products';
    }
});

var SearchResultProducts = Backbone.Collection.extend({
    model: Product,

    url: function(){
        return '/domosed/products?offset=0&limit=10&words=' + this.searchExpression;
    },

    setText: function(searchExpression){
        this.searchExpression = searchExpression;
    },

    parse: function(responce){
        return responce.collectionResult;
    }
});

var Products = Backbone.Paginator.requestPager.extend({
    model: Product,

    initialize: function(models, options){
        this.options = options;
    },

    url: function(){
        return '/domosed/categories/' + this.options.categoryId;
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
        'limit' : function() { return this.perPage}
    },

    parse: function (response) {
        this.totalPages = Math.floor(response.totalCount/this.perPage) + 1;
        this.totalRecords = this.totalPages * this.perPage;
        return response.collectionResult;
    }
});

var ProductView = Backbone.View.extend({
    tagName: 'tr',

    initialize: function(){
        this.template = _.template(tpl.get('productViewTpl').text);
    },

    events: {
        'click .btn-default' : 'updatePrice'
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    updatePrice: function(){
        var newPrice = this.$el.find('input').val();

        if (newPrice < 0){
            $.bootstrapGrowl("Недопустимое значение! \n " +
                "Установите положительное значение цены!",
                {ele: 'body', type: 'danger', width: 350});
            return;
        }

        console.log('New price for ' + this.model.get('name') + ' is ' + newPrice);

        this.model.set('usdPrice', newPrice);

        this.model.save( this.model.toJSON(),
            {
                error: function(model, response){
                    $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                        {ele: 'body', type: 'danger', width: 350});
                },
                success: function(model, response){
                    $.bootstrapGrowl("Изменения успешно сохранены!",
                        {ele: 'body', type: 'success', width: 350});
                }
            });
    }
});

var  ProductsView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        console.log("Render product with id : " + item.id);
        var view = new ProductView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of ProductsView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        var th = $('<th class="col-md-4">Название</th>');
        tr.append(th);
        tr.append('<th  class="col-md-1">Цена, у.е.</th>');
        tr.append('<th  class="col-md-1"></th>');
        this.$el.append(tr);
    }
});