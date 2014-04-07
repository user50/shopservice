var app = app || {};

var AddGroup = Backbone.View.extend({
    el: '#addNewSiteForm',

    initialize: function() {
    },

    events: {
        'click input[type=button]': 'addGroup',
        'keypress #new-group': 'createOnEnter'
    },

    initialize: function(){
    },

    createOnEnter: function( event ) {
        $newGroup = this.$('#new-group');
        if ( event.which !== ENTER_KEY || !$newGroup.val().trim() ) {
            return;
        }
        event.preventDefault();
        var newGroup = new Group({name: $newGroup.val().trim()});
        this.collection.create(newGroup, {wait: true});
    },
    addGroup: function(e){
        var newGroupName = this.$el.find('input[type=text]').val();
        var newGroup = new Group({name: newGroupName});
        this.collection.create(newGroup, {wait: true});
    }
});

var EditGroup = Backbone.View.extend({
    el: '#editSiteForm',

    events: {
        'click input[type=button]': 'editGroup',
        'keypress #edit-group': 'editOnEnter'
    },

    editOnEnter: function( event ) {
        $groupToEdit = this.$('#edit-group');
        if ( event.which !== ENTER_KEY || !$groupToEdit.val().trim() ) {
            return;
        }
        event.preventDefault();
        var changedGroup = this.collection.get(currentGroupId);
        changedGroup.save({name: $groupToEdit.val().trim()})
    },
    editGroup: function(e){
        var changedGroupName = this.$el.find('input[type=text]').val();
        var changedGroup = this.collection.get(currentGroupId);
        changedGroup.save({name: changedGroupName});
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
//        this.listenTo($('#mergeGroups option'), 'click', function(e){console.log('Hello!')});
        this.collection.on('add', this.addOne, this);
        this.listenTo(app.categories, 'mergeIsHappened', this.render);
    },

    render: function(){
        this.$el.find('option').remove()
            .end().append('<option selected disabled>Merge to</option>');
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
        this.listenTo(app.categories, 'excludeIsHappened', this.render);
    },

    render: function(){
        this.$el.find('option').remove()
            .end().append('<option selected disabled>Exclude</option>');
        this.collection.each(this.addOne, this);
        return this;
    },

    addOne: function(group){
        var newGroup = new GroupViewToSelect({model: group});
        this.$el.append(newGroup.render().el);
    }
});

app.AddGroup = new AddGroup({collection: app.Groups});
app.EditGroup = new EditGroup({collection: app.Groups});
app.DeleteGroup = new DeleteGroup({collection: app.Groups});
app.MergeView = new MargeView({collection: app.Groups});
app.ExcludeView = new ExcludeView({collection: app.Groups});