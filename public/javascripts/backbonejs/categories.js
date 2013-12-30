$(function(){
    window.App = {
        Models: {},
        Views: {},
        Collections: {},
        Router: {}
    };

    window.template = function (id){
        return _.template( $('#' + id).html());
    };

    App.Models.Category = Backbone.Model.extend({});

    App.Collections.Category = Backbone.Collection.extend({
        model: App.Models.Category,
        url: function(){
            return "/clients/" + clientId + "/categories";
        },
        parse: function(response){
            return response.categories;
        }
    });

    App.Views.Category = Backbone.View.extend({
        tagName: 'li',
        template: template('categoryTpl'),
        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        },
        events: {
            'click': 'showMessage'
        },

        showMessage: function(e){
            alert($(e.currentTarget).text());
        }
    });

    App.Views.Categories = Backbone.View.extend({
        initialize: function(){
            //this.render();
            this.collection.on('add', this.addOne, this);
        },
        tagName: 'ol',
        className: 'rectangle-list',
        render: function(){
            this.$el.empty();
            this.collection.each(function(category){
                var categoryView = new App.Views.Category({model: category});
                this.$el.append(categoryView.render().el);
            }, this);
            return this;
        },
        addOne: function(category){
            var categoryView = new App.Views.Category({model: category});
            this.$el.append(categoryView.render().el);
        }
    });
});