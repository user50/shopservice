
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
    }
});

var SearchResultView = Backbone.View.extend({
    tagName: 'tr',

    initialize: function(){
        this.template = _.template(tpl.get('searchResultViewTpl').text);
    },

    events: {
        'click .btn-info' : 'updatePrice'
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

var  SearchResultsView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        var view = new SearchResultView({model:item});
        this.$el.append(view.render().el);
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

        var th = $('<th class="col-md-4">Название</th>');
        tr.append(th);
        tr.append('<th  class="col-md-1">Цена, у.е.</th>');
        tr.append('<th  class="col-md-1"></th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});