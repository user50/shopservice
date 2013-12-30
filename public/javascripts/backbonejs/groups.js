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

    App.Models.Group = Backbone.Model.extend({});

    clientId = 'client2';

    App.Collections.Groups = Backbone.Collection.extend({
        model: App.Models.Group,
        url: function(){
            return '/clients/'+clientId+'/groups';
        }
    });

    App.Views.Groups = Backbone.View.extend({
        el: '#sites',

        initialize: function(){
            this.collection.on('add', this.addOne, this);
        },
        render: function(){
            this.$el.empty();
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(group){
            var groupView = new App.Views.Group({model: group});
            this.$el.append(groupView.render().el);
        }
    });

    App.Views.Group = Backbone.View.extend({
        initialize: function(){
            this.model.on('destroy', this.remove, this);
        },
        template: template('groupTemp'),
        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        }
    });

    App.Views.AddGroup = Backbone.View.extend({
        el: '#addGroup',

        events: {
            'submit': 'submit'
        },

        initialize: function(){
        },

        submit: function(e){
            e.preventDefault();

            var newGroupName = $(e.currentTarget).find('input[type=text]').val();
            var newGroup = new App.Models.Group({name: newGroupName});
            this.collection.create(newGroup, {wait: true});
        }
    });
});