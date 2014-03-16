var app = app || {};

var AddGroup = Backbone.View.extend({
    el: '#addNewSiteForm',

    events: {
        'submit': 'submit'
    },

    initialize: function(){
    },

    submit: function(e){
        var newGroupName = $(e.currentTarget).find('input[type=text]').val();
        var newGroup = new Group({name: newGroupName});
        this.collection.create(newGroup, {wait: true});
    }
});

var DeleteGroup = Backbone.View.extend({
    el: '#deleteGroupIdImg',
    events: {
        'click': 'removeOne'
    },

    removeOne: function(){
        this.collection.find(
            function(model) {
                return model.get('id') == currentGroupId;
            }).destroy();
    }
});

var MargeView = Backbone.View.extend({
    el: '#mergeGroups',

    initialize: function(){
        this.collection.on('add', this.addOne, this);
        this.on('change', this.merge);
    },

    render: function(){
        this.$el.empty();
        this.collection.each(this.addOne, this);
        return this;
    },

    addOne: function(group){
        var newGroup = new GroupViewToSelect({model: group});
        this.$el.append(newGroup.render().el);
    }
});

var ExcludeView = Backbone.View.extend({
    el: '#excludeGroups',

    initialize: function(){
        this.collection.on('add', this.addOne, this);
    },

    render: function(){
        this.$el.empty();
        this.collection.each(this.addOne, this);
        return this;
    },

    addOne: function(group){
        var newGroup = new GroupViewToSelect({model: group});
        this.$el.append(newGroup.render().el);
    }
});

app.AddGroup = new AddGroup({collection: app.Groups});
app.DeleteGroup = new DeleteGroup({collection: app.Groups});
app.MergeView = new MargeView({collection: app.Groups});
app.ExcludeView = new ExcludeView({collection: app.Groups});