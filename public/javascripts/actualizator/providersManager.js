var app1 = app1 || {};


var ProviderPage = Backbone.View.extend({

    el: '#contents',

    initialize: function(){
        this.Providers = new Providers();
        this.Providers.fetch();
        this.ProvidersView = new ProvidersView({collection: this.Providers});
        this.AddProvider = new AddProvider({collection: this.Providers});
        this.EditProvider = new EditProvider({model: new Provider({name: '', url: ''})});
        this.UpdateButton = new UpdateButton();

        this.listenTo(actVent, 'provider:edit', this.renderEditView);
    },

    events: {
        'click #addProviderBtn': function(){this.AddProvider.clear()}
    },

    renderEditView: function(modelId){
        this.EditProvider.$el.remove();

        this.EditProvider = new EditProvider({model: this.Providers.get(modelId)});
        this.$el.append(this.EditProvider.render().el);
    },

    render: function(){
        this.$el.append(this.AddProvider.render().el);
        this.$el.append(this.ProvidersView.render().el);
        this.$el.append(this.EditProvider.render().el);
        this.$el.append(this.UpdateButton.render().el);
        return this;
    }

});

var AddProvider = Backbone.View.extend({

    tagName: 'div',
    className: 'row',

    initialize: function() {
    },

    template: _.template($('#addProviderTpl').html()),

    events: {
        'click #saveNewProvider' : 'addProvider'
    },

    addProvider: function(){
        var name = this.$el.find('#providerNameInput').val();
        var url = this.$el.find('#providerUrlInput').val();

        var newProvider = new Provider({name: name, url: url});

        this.collection.create(newProvider, {wait: true,
            error: function(model, response){
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: '#addProviderModal', type: 'danger', width: 350});
            },
            success: function(model, response){
                $.bootstrapGrowl("Изменения успешно сохранены!",
                    {ele: '#addProviderModal', type: 'success', width: 350});
            }});
    },

    clear: function(){
        console.log('clear form for creating a provider');
        this.$el.find('#providerNameInput').val('');
        this.$el.find('#providerUrlInput').val('');
    },

    render: function(){
        var template = this.template();
        this.$el.html( template );
        return this;
    }
});

var EditProvider = Backbone.View.extend({
    id: 'editProviderModal',
    tagName: 'div',
    className: 'modal fade',

    attributes: {
        tabindex: -1,
        role: 'dialog',
        'aria-labelledby': 'myModalLabel1',
        'aria-hidden': true
    },

    initialize: function(){
        var tags = this.model;
        tags.on('change', this.render, this);
        this.render();
    },

    template: _.template($('#editProviderTpl').html()),

    events: {
        'click #editProvider': 'editGroup'
    },

    editGroup: function(){
        var changedName = this.$el.find('#providerNameInputEdit').val();
        var changedUrl = this.$el.find('#providerUrlInputEdit').val();

        this.model.set('name', changedName);
        this.model.set('url', changedUrl);

        var provider = this.model.toJSON();

        this.model.save(provider, {wait: true,
            error: function(model, response){
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: '#editProviderModal', type: 'danger', width: 350});
            },
            success: function(model, response){
                $.bootstrapGrowl("Изменения успешно сохранены!",
                    {ele: '#editProviderModal', type: 'success', width: 350});
            }});
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    }
});

var UpdateButton = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    template: _.template($('#updateBtnTpl').html()),

    render: function(){
        this.$el.html( this.template );
        return this;
    }
});