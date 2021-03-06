var app = app || {};

var AppView = Backbone.View.extend({
    el: '#cont',
    initialize: function(){
//        this.listenTo(vent, 'selectedCategory', this.onSelectedCategory);
//        this.listenTo(vent, 'selectedGroup', this.onSelectedGroup);
        this.listenTo(app.Groups, 'remove', this.groupRemoved);

    },
    onSelectedCategory: function(){
        app.categories.reset();
        app.categoriesView.$el.hide();
        app.BackButton.$el.show();
    },
    onSelectedGroup: function(){
        app.categoriesView.$el.show();
        app.BackButton.$el.hide();
    },
    groupRemoved: function(){
        app.categories.reset();
    }
});

var BackButton = Backbone.View.extend({
    el: '#toCategories',
    initialize: function(){
        this.$el.addClass('hidden');
    },
    events:{
        'click' : 'toCategories'
    },

    toCategories: function(){
        this.$el.hide();
        app.router.navigate('groups/' + currentGroupId,{trigger: true});
    }
});

app.BackButton = new BackButton();

var vent = _.extend({}, Backbone.Events);
app.AppView = new AppView();