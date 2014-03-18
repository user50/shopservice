var app = app || {};

    var Category = Backbone.Model.extend({});

    var Categories = Backbone.Collection.extend({
        url: "/clients/" + clientId + "/categories",
        parse: function(response){
            app.Counter.set('count', response.totalCount);
            return response.categories;
        }
    });

    var CategoryView = Backbone.View.extend({
        tagName: 'li',
        template: _.template($('#categoryTpl').html()),

        initialize: function(){
            this.model.on('change', this.render, this);
        },

        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        }
    });

var CategoriesView = Backbone.View.extend({
        el: '.rectangle-list',

        initialize: function(){
            this.collection.on('add', this.addOne, this);
            this.listenTo(app.GroupsView, 'mergeIsHappened', this.render);
        },
        render: function(){
            this.$el.empty();
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(category){
            var categoryView = new CategoryView({model: category});
            this.$el.append(categoryView.render().el);
        }
    });

    app.categories = new Categories();
    app.categoriesView = new CategoriesView({collection: app.categories});
