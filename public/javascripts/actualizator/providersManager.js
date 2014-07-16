var app1 = app1 || {};


var ProviderPage = Backbone.View.extend({
    el: '#providers',

    initialize: function(){
        this.Providers = new Providers();
        this.Providers.fetch();
        this.ProvidersView = new ProvidersView({collection: this.Providers});
        this.AddProvider = new AddProvider({collection: this.Providers});
        this.EditProvider = new EditProvider({model: new Provider({name: '', url: ''})});
    },

    events: {
        'click #addProviderBtn': function(){this.AddProvider.clear()}
    },

    renderEditView: function(model){
        this.EditProvider.model.set(model.toJSON());
    }

});

var AddProvider = Backbone.View.extend({
    el: '#addProviderModal',

    initialize: function() {
    },

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
    }
});

var EditProvider = Backbone.View.extend({
    el: '#editProviderModal',

    initialize: function(){
        var tags = this.model;
        tags.on('change', this.render, this);
    },

    events: {
        'click #editProvider': 'editGroup'
    },

    editGroup: function(){
        var changedProvider = app1.ProviderPage.Providers.get(this.model.id);

        var changedName = this.$el.find('#providerNameInputEdit').val();
        var changedUrl = this.$el.find('#providerUrlInputEdit').val();

        changedProvider.set('name', changedName);
        changedProvider.set('url', changedUrl);

        var provider = changedProvider.toJSON();

        changedProvider.save(provider, {wait: true,
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
        if (this.model == null)
            return this;

        this.$el.find('#providerNameInputEdit').val(this.model.get('name'));
        this.$el.find('#providerUrlInputEdit').val(this.model.get('url'));

        return this;
    }
});

app1.ProviderPage = new ProviderPage();