
var ManufacturersPage = Backbone.View.extend({
    tagName: 'div',
     initialize: function(){
        this.Manufacturers = new Manufacturers();
        this.ManufacturersView = new ManufacturersView({collection: this.Manufacturers});
     },

    render: function(){
        this.Manufacturers.fetch({wait: true});

        this.ProductSearch = new ProductSearch();
        this.$el.append(this.ProductSearch.render().el);

        var rowDiv = $('<div class="row clearfix">');
        var colDiv = $('<div class="col-md-6 column">');
        colDiv.append(this.ManufacturersView.render().el);
        rowDiv.append(colDiv);
        this.$el.append(rowDiv);

        return this;
    }
});

var Manufacturer = Backbone.Model.extend({});

var Manufacturers = Backbone.Collection.extend({
    model: Manufacturer,

    url: function(){
        return '/domosed/manufacturers';
    }
});

var ManufacturerView = Backbone.View.extend({
    tagName: 'tr',

    initialize: function(){
        this.template = _.template(tpl.get('manufacturerViewTpl').text);
    },

    events : {
        'click .btn-info' : 'updateRate'
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    updateRate: function(){
        var newRate = this.$el.find('input').val();

        if (newRate <= 0){
            $.bootstrapGrowl("Недопустимое значения курса! \n " +
                             "Установите значения курса больше 0!",
                {ele: 'body', type: 'danger', width: 350});
            return;
        }
        console.log('New rate for ' + this.model.get('name') + ' is ' + newRate);

        this.model.set('rate', newRate);

        this.model.save(
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

var  ManufacturersView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        var view = new ManufacturerView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of ManufacturersView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        var th = $('<th class="col-md-3">Производитель</th>');
        tr.append(th);
        tr.append('<th  class="col-md-1">Курс, у.е.</th>');
        tr.append('<th class="col-md-2"></th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});

var manufacturers = [{name: "Akant", rate: 15}, {name: "Briz", rate: 16}];