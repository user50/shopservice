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
            currentGroupId = selectedGroupId;
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

app.Groups = new Groups();
app.GroupsView = new GroupsView({collection: app.Groups});
app.Groups.fetch();
