var app = app || {};


    var Group = Backbone.Model.extend();

    clientId = 'client1';

    var Groups = Backbone.Collection.extend({
        model: Group,

        url: function(){
            return '/clients/'+clientId+'/groups';
        }
    });

    var GroupsView = Backbone.View.extend({
        el: '#sites',

        initialize: function(){
            this.collection.on('add', this.addOne, this);
        },

        events: {
            'click input[name=site]': 'setSelectedGroup'
        },

        setSelectedGroup: function(e){
            var selectedGroupId = e.currentTarget.attributes.siteid.value;
            this.trigger('changeCurrentGroup', selectedGroupId);
        },

        render: function(){
            this.$el.empty();
            this.collection.each(this.addOne, this);
            return this;
        },
        addOne: function(group){
            var groupView = new GroupView({model: group});
            this.$el.append(groupView.render().el);
        }
    });

    var GroupView = Backbone.View.extend({
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

    var AddGroup = Backbone.View.extend({
        el: '#addGroup',

        events: {
            'submit': 'submit'
        },

        initialize: function(){
        },

        submit: function(e){
            e.preventDefault();
            var newGroupName = $(e.currentTarget).find('input[type=text]').val();
            var newGroup = new Group({name: newGroupName});
            this.collection.create(newGroup, {wait: true});
        }
    });

    app.Groups = new Groups();
    app.Groups.fetch();
    app.GroupsView = new GroupsView({collection: app.Groups});