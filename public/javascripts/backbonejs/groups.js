var app = app || {};


    var Group = Backbone.Model.extend();

    clientId = Cookie.get('clientId');

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
            app.router.navigate('groups/' + selectedGroupId, {trigger: true});
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
        className: "site",
        template: _.template($('#groupTemp').html()),

        initialize: function(){
            this.model.on('change', this.render, this);
            this.model.on('destroy', this.remove, this);
        },

        events: {
            'click' : 'click'
        },
        render: function(){
            var template = this.template(this.model.toJSON())
            this.$el.html( template );
            return this;
        },
        click: function(){
            vent.trigger('selectedGroup');
        }
    });

    var GroupViewToSelect = Backbone.View.extend({
        tagName: 'option',
        initialize: function(){
            this.model.on('destroy', this.remove, this);
        },

        render: function(){
            this.$el.html(this.$el.attr('value', this.model.id));
            this.$el.text(this.model.get('name'));
            return this;
        }
    });

app.Groups = new Groups();
app.GroupsView = new GroupsView({collection: app.Groups});
app.Groups.fetch();
