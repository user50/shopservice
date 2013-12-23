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

    App.Collections.Groups = Backbone.Collection.extend({
        model: App.Models.Group,
        url: '/clients/client2/groups'
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
//            var label = $('<label/>');
//            label.attr('for', group.get('name'));
//            label.text(group.get('name'));
//            this.$el.append(label);
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

    window.groups = new App.Collections.Groups();
    groups.fetch();
    var groupsView = new App.Views.Groups({collection: groups});
});