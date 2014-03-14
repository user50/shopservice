var app = app || {};

    app.Category = Backbone.Model.extend({});

    app.Categories = Backbone.Collection.extend({
        model: app.Category,
        url: function(){
            return "/clients/" + clientId + "/categories?groupId=23";
        },
        parse: function(response){
            return response.categories;
        }
    });

    app.CategoryView = Backbone.View.extend({
        tagName: 'li',
        template: _.template($('#categoryTpl').html()),
        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        }
    });

    app.CategoriesView = Backbone.View.extend({
        initialize: function(){
            //this.render();
            this.collection.on('add', this.addOne, this);
        },
        el: '.rectangle-list',
        render: function(){
            this.$el.empty();
            this.collection.each(function(category){
                var categoryView = new app.CategoryView({model: category});
                this.$el.append(categoryView.render().el);
            }, this);
            return this;
        },
        addOne: function(category){
            var categoryView = new app.CategoryView({model: category});
            this.$el.append(categoryView.render().el);
        },

        filterOne: function(){
            alert("ups");
        }


    });
