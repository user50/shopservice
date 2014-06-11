var app = app || {};

    var Category = Backbone.Model.extend({});

    var Categories = Backbone.Collection.extend({

        initialize: function(){
            this.on('changeCurrentGroup mergeIsHappened excludeIsHappened',
                function(currentGroupId){
                    this.fetch({
                      data: $.param({ groupId: currentGroupId})});
                }, this);
        },

        url: "/clients/" + clientId + "/categories",

        parse: function(response){
            return response.categories;
        }
    });

    var CategoryView = Backbone.View.extend({
        template: _.template($('#categoryTpl').html()),

        initialize: function(){
            this.model.on('change', this.render, this);
        },

        render: function(){
            console.log("Render CategoryView to a category model with id: " + this.model.id);
            this.model.set('href', 'groups/' + currentGroupId + '/categories/' + this.model.id);
            var template = this.template(this.model.toJSON());
            this.setElement(template);
            return this;
        }
    });

    var CategoriesView = Backbone.View.extend({
        el: '#categories',

        initialize: function(){
            this.collection.on('add', this.addOne, this);
            this.collection.on('reset', this.render, this);
        },
        render: function(){
            this.$el.empty();
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(category){
            console.log("Adding the category view (model.id = " + category.id + ")to CategoriesView : start...");
            var categoryView = new CategoryView({model: category});
            this.$el.append(categoryView.render().el);
        }
    });

    app.categories = new Categories();
    app.categoriesView = new CategoriesView({collection: app.categories});
