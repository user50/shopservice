var app = app || {};

var PriceManager = Backbone.View.extend({
    el: '#priceManagerDiv',

    initialize: function(){
        app.AddGroup = new AddGroup({collection: app.Groups});
        app.EditGroup = new EditGroup({collection: app.Groups});
        app.DeleteGroup = new DeleteGroup({collection: app.Groups});
    },

    events: {
        'click #createNewPriceBtn': function(){app.AddGroup.clear()},
        'click #editPriceBtn': function(){app.EditGroup.render()},
        'click #downloadPriceBtn': 'downloadPrice'
    },

    downloadPrice: function(){
        console.log('downloading price...');
        var url = "/client/"+clientId + "/groups/" + currentGroupId + "/pricelist";
        window.open(url);
    }
});

var AddGroup = Backbone.View.extend({
    el: '#createPriceModal',

    initialize: function() {
    },

    events: {
        'click #saveNewPrice': 'addGroup'
    },

    initialize: function(){
    },

    addGroup: function(e){
        //TODO validation
        var name = this.$el.find('#priceNameInput').val();
        var format = this.$el.find('#priceFormatSelect option:selected').val();
        var newGroup = new Group({name: name, format: format});
        this.collection.create(newGroup, {wait: true,
            error: function(){$('#invalidNewPriceName').show()},
            success: function(){$('#invalidNewPriceName').hide()}});
    },

    clear: function(){
        console.log('clear form for creating a price');
        this.$el.find('#invalidNewPriceName').hide();
        var name = this.$el.find('#priceNameInput').val('');
        var format = this.$el.find('#priceFormatSelect').val('YML');
    }
});

var EditGroup = Backbone.View.extend({
    el: '#editPriceModal',

    events: {
        'click #saveEditedPrice': 'editGroup'
    },

    editGroup: function(e){
        //TODO validation
        var changedName = this.$el.find('#priceNameInputEdit').val();
        var changedFormat = this.$el.find('#priceFormatSelectEdit option:selected').val();
        var changedGroup = this.collection.get(currentGroupId);
        changedGroup.save({name: changedName, format: changedFormat}, {wait: true,
            error: function(){$('#invalidPriceName').show()},
            success: function(){$('#invalidPriceName').hide()}});
    },

    render: function(){
        this.$el.find('#invalidPriceName').hide();
        var group = app.Groups.get(currentGroupId);
        var name = this.$el.find('#priceNameInputEdit').val(group.get('name'));
        var format = this.$el.find('#priceFormatSelectEdit').val(group.get('format'));
    }

});

var DeleteGroup = Backbone.View.extend({
    el: '#deletePriceButton',

    events: {
        'click': 'removeOne'
    },

    removeOne: function(){
        var groupName = this.collection.get(currentGroupId).get('name');

        bootbox.dialog({
            message: "Are you sure you want to delete the " + groupName + " price list?",
            buttons: {
                success: {
                    label: "Close",
                    className: "btn-default",
                    callback: function() {
                    }
                },
                danger: {
                    label: "Delete",
                    className: "btn-danger",
                    callback: function() {
                        app.Groups.get(currentGroupId).destroy();
                        app.categories.reset();
                        new BreadcrumbsView({categoryName: null, groupName: null});
                        app.ProductsView.$el.hide();
                        app.pagination.$el.hide();
                    }
                }
            }
        });
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

app.PriceManager = new PriceManager();
//app.AddGroup = new AddGroup({collection: app.Groups});
//app.EditGroup = new EditGroup({collection: app.Groups});
//app.DeleteGroup = new DeleteGroup({collection: app.Groups});
//app.MergeView = new MargeView({collection: app.Groups});
//app.ExcludeView = new ExcludeView({collection: app.Groups});