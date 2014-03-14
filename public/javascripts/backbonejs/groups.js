var app = app || {};

    app.Group = Backbone.Model.extend({});

    clientId = 'client1';

    app.Groups = Backbone.Collection.extend({
        model: app.Group,

        url: function(){
            return '/clients/'+clientId+'/groups';
        }
    });

    app.GroupsView = Backbone.View.extend({
        el: '#sites',

        initialize: function(){
            this.collection.on('add', this.addOne, this);
        },

        events: {
            'click input[name=site]': 'showMessage'
        },

        showMessage: function(e){
            alert(e.currentTarget.attributes.siteid.value);
        },

        render: function(){
            this.$el.empty();
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(group){
            var groupView = new app.GroupView({model: group});
            this.$el.append(groupView.render().el);
        }
    });

    app.GroupView = Backbone.View.extend({
        initialize: function(){
            this.model.on('destroy', this.remove, this);
        },
        className: "site",
        template: _.template($('#groupTemp').html()),

        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        }
    });

    app.AddGroup = Backbone.View.extend({
        el: '#addGroup',

        events: {
            'submit': 'submit'
        },

        initialize: function(){
        },

        submit: function(e){
            e.preventDefault();

            var newGroupName = $(e.currentTarget).find('input[type=text]').val();
            var newGroup = new app.Group({name: newGroupName});
            this.collection.create(newGroup, {wait: true});
        }
    });
