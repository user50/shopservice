var app = app || {};

var CustomCategory = Backbone.Model.extend({});

var CustomCategories = Backbone.Collection.extend({
    model: CustomCategory,

    url: function(){
        return '/clients/'+clientId+'/clientsCategories';
    }
});

var CustomCategoryView = Backbone.View.extend({
    tagName: 'tr',
    template: _.template($('#customCategoryViewTpl').html()),

    events : {
        'click .btn-danger' : 'removeThis'
    },

    initialize: function() {
        this.model.bind('change', this.render, this);
        this.model.bind('remove', this.remove, this);
    },

    render : function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    },

    removeThis: function(){
        var model = this.model;
        bootbox.dialog({
            message: "Вы уверены, что хотите удалить категорию  \"" + this.model.get('name') + "\" ?",
            buttons: {
                success: {
                    label: "Отмена",
                    className: "btn-default",
                    callback: function() {
                    }
                },
                danger: {
                    label: "Удалить",
                    className: "btn-danger",
                    callback: function() {
                        model.destroy();
                        $.bootstrapGrowl("Категория \"" + model.get('name') + "\" была удалена!",
                            {ele: 'body', type: 'success', width: 350});
                    }
                }
            }
        });
    }
});

var CustomCategoryOptionView = Backbone.View.extend({
    tagName: 'option',

    initialize: function() {
        this.model.bind('change', this.render, this);
        this.model.bind('remove', this.remove, this);
    },

    render: function(){
        this.$el.html(this.$el.attr('value', this.model.id));
        this.$el.text(this.model.get('name'));
        return this;
    }
});

var CustomCategoriesView = Backbone.View.extend({
    el : '#customCategoriesTable',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
        this.render();
    },

    addOne : function ( item ) {
        var view = new CustomCategoryView({model:item});
        this.$el.append(view.render().el);
    },

    setParentsName: function(){
        for (var i=0; i < this.collection.length; i++){
            var model = this.collection.at(i);
            var parentModel = this.collection.get(model.get('parentId'));
            if (parentModel == 'undefined' || parentModel == null){
                model.set('parentName', '-');
            } else
                model.set('parentName', parentModel.get('name'));
        }
    },

    render: function(){
        console.log('Rendering of CustomCategoriesView...');
        this.$el.empty();

        if (this.collection.length == 0){
            var emptyRow = $('<tr/>');
            emptyRow.append($('<th/>').text('Вы еще не создали ни одной категории.'));
            this.$el.append(emptyRow);
            return this;
        }

        this.renderHeader();
        this.setParentsName();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        tr.append('<th  class="col-md-5">Название категории</th>');
        tr.append('<th class="col-md-5">Имя родительской категории</th>');
        tr.append('<th class="col-md-2"></th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});

var CustomCategoriesSelectView = Backbone.View.extend({
    el: '#customCategoryParent',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
        this.render();
    },

    addOne : function ( item ) {
        var view = new CustomCategoryOptionView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of CustomCategoriesSelectView...');
        this.$el.find('option').remove()
            .end().append('<option value="null" selected disabled>Родительская категория</option>');
        this.collection.each(this.addOne, this);
        return this;
    }
});

var CustomCategoryAddView = Backbone.View.extend({
    el: '#addCustomCategoryBtn',

    events : {
        'click' : 'addOne'
    },

    addOne: function(){
        var name = $('#customCategoryName').val();
        var parentId = $('#customCategoryParent').val();

        var newGroup = new CustomCategory({name : name, parentId : parentId});
        this.collection.create(newGroup, {wait: true,
            error: function(model, response){
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: 'body', type: 'danger', width: 350});
            },
            success: function(model, response){
                $.bootstrapGrowl("Категория успешно сохранена!",
                    {ele: 'body', type: 'success', width: 350});
            }});
    }
});

app.customCategories = new CustomCategories();
app.customCategoriesView = new CustomCategoriesView({collection: app.customCategories});
app.customCategoriesSelectView = new CustomCategoriesSelectView({collection: app.customCategories});
app.addCustomCategoryBtn = new CustomCategoryAddView({collection: app.customCategories});
app.customCategories.fetch();