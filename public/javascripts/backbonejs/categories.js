var app = app || {};

    var Category = Backbone.Model.extend({});

    var Categories = Backbone.Collection.extend({
        updateUrl: function(groupId){
            this.url = "/clients/" + clientId + "/categories?groupId=" + groupId;
        },

        parse: function(response){
            return response.categories;
        }
    });

    var CategoryView = Backbone.View.extend({
        tagName: 'li',
        template: _.template($('#categoryTpl').html()),
        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        }
    });

    var CategoriesView = Backbone.View.extend({
        initialize: function(){
            this.collection.on('add', this.addOne, this);
            this.listenTo(app.GroupsView, 'changeCurrentGroup', this.render);
        },

        el: '.rectangle-list',
        render: function(groupId){
            this.$el.empty();

            this.collection.updateUrl(groupId);
            this.collection.reset();
            this.collection.fetch();

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
