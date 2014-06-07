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
            app.Counter.set('count', response.totalCount);
            return response.categories;
        }
    });

    var CategoryView = Backbone.View.extend({
        tagName: 'li',
        template: _.template($('#categoryTpl').html()),

        events: {
            'click' : 'onClick'
        },
        initialize: function(){
            this.model.on('change', this.render, this);
        },

        render: function(){
            console.log("Render CategoryView to a category model with id: " + this.model.id);
            var template = this.template(this.model.toJSON())
                this.$el.html( template );
                return this;
        },

        onClick: function(){
            currentCategoryId = this.model.id;
            app.router.navigate('groups/' + currentGroupId + '/categories/' + this.model.id, {trigger: true});
//            app.Products.trigger('selectedCategory');
//            vent.trigger('selectedCategory');
        }
    });

    var CategoriesView = Backbone.View.extend({
        el: '.rectangle-list',

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
            console.log("Adding the category view (model.id = " + category.id + ")to CategoriesView : start...")
            var categoryView = new CategoryView({model: category});
            this.$el.append(categoryView.render().el);
        }
    });

    app.categories = new Categories();
    app.categoriesView = new CategoriesView({collection: app.categories});
