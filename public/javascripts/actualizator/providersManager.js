var app1 = app1 || {};


var ProviderPage = Backbone.View.extend({

    tagName: 'div',
    className: 'row clearfix',

    initialize: function(){
        this.Providers = new Providers();
        this.Providers.fetch();
        this.ProvidersView = new ProvidersView({collection: this.Providers});
        this.AddProvider = new AddProvider({collection: this.Providers});
        this.EditProvider = new EditProvider({model: new Provider({name: 'zg', url: 'sdfg'})});

        this.listenTo(actVent, 'provider:edit', this.renderEditView);
    },

    events: {
        'click #addProviderBtn': function(){this.AddProvider.clear()},
        'click #editProvider': function(){
            if (this.EditProvider) this.EditProvider.close();
        }
    },

    renderEditView: function(modelId){
        var model = this.Providers.get(modelId);
        this.EditProvider.model.set(model.toJSON());
    },

    render: function(){
        this.$el.append(this.AddProvider.render().el);
        this.$el.append(this.ProvidersView.render().el);
        this.$el.append(this.EditProvider.render().el);
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
        var changedProvider = new Provider({id: this.model.id});
        changedProvider.fetch();

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
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    }
});

//app1.ProviderPage = new ProviderPage();