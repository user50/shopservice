var ProviderPage = Backbone.View.extend({

    tagName: 'div',

    initialize: function(){
        this.Providers = new Providers();
        this.Providers.fetch();
        this.ProvidersView = new ProvidersView({collection: this.Providers});
        this.AddProvider = new AddProvider({collection: this.Providers});
        this.EditProvider = new EditProvider({model: new Provider({name: '', url: '', margin: ''})});
        this.PriceUploader = new PriceUploader({model: new Provider({name: '', url: '', margin: ''})});
        this.UpdateButton = new UpdateButton();

        this.listenTo(actVent, 'provider:edit', this.renderEditView);
        this.listenTo(actVent, 'provider:uploadPrice', this.renderPriceUploadView);
    },

    events: {
        'click #addProviderBtn': function(){this.AddProvider.clear()}
    },

    renderEditView: function(modelId){
        this.EditProvider.$el.remove();

        this.EditProvider = new EditProvider({model: this.Providers.get(modelId)});
        this.$el.append(this.EditProvider.render().el);
    },

    renderPriceUploadView: function(modelId){
        this.PriceUploader.$el.remove();

        this.PriceUploader = new PriceUploader({model: this.Providers.get(modelId)});
        this.$el.append(this.PriceUploader.render().el);
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
        this.template = _.template(tpl.get('addProviderTpl').text);
    },

    events: {
        'click #saveNewProvider' : 'addProvider'
    },

    addProvider: function(){
        var name = this.$el.find('#providerNameInput').val();
        var url = this.$el.find('#providerUrlInput').val();
        var margin = this.$el.find('#providerMarginInput').val();

        var newProvider = new Provider({name: name, url: url, margin: margin});

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
        this.$el.find('#providerMarginInput').val('');
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
        this.template = _.template(tpl.get('editProviderTpl').text);

        var tags = this.model;
        tags.on('change', this.render, this);
        this.render();
    },

    events: {
        'click #editProvider': 'editGroup'
    },

    editGroup: function(){
        var changedName = this.$el.find('#providerNameInputEdit').val();
        var changedUrl = this.$el.find('#providerUrlInputEdit').val();
        var changedMargin = this.$el.find('#providerMarginInputEdit').val();

        this.model.set('name', changedName);
        this.model.set('url', changedUrl);
        this.model.set('margin', changedMargin);

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

var PriceUploader = Backbone.View.extend({
    id: 'uploadPriceModal',
    tagName: 'div',
    className: 'modal fade',

    attributes: {
        tabindex: -1,
        role: 'dialog',
        'aria-labelledby': 'myModalLabel2',
        'aria-hidden': true
    },

    events: {
        'click #uploadBtn' : 'uploadFile',
        'change input[type=file]' : 'prepareUpload'
    },
    initialize: function(){
        this.template = _.template(tpl.get('priceUploaderTpl').text);

        var tags = this.model;
        tags.on('change', this.render, this);
        this.render();
    },

    prepareUpload: function (event)
    {
        this.files = event.target.files;
        this.$el.find('#uploadFileName').val(this.files[0].name);
    },

    uploadFile: function(event){
            event.stopPropagation(); // Stop stuff happening
            event.preventDefault(); // Totally stop stuff happening

        if (this.files == null)
        {
            $.bootstrapGrowl("Ошибка! Вы не выбрали файл прайса поставщика для загрузки!",
                {ele: 'body', type: 'danger', width: 350});
            return;
        }
        // Create a formdata object and add the files
        var data = new FormData();
        $.each(this.files, function(key, value)
        {
            data.append(key, value);
        });

        var providerName = this.model.get('name');

        $.ajax({
            url: this.getUploadUrl(),
            type: 'POST',
            data: data,
            cache: false,
            dataType: 'json',
            processData: false, // Don't process the files
            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
            success: function(data)
            {
                $.bootstrapGrowl("Файл прайс листа \"" + data[0] + "\" для \"" + providerName + "\" успешно загружен!",
                    {ele: 'body', type: 'info', width: 500});
            },
            error: function()
            {
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: 'body', type: 'danger', width: 350});
            }
        });
    },

    getUploadUrl: function(){
        return '/clients/' + clientId + '/providers/' + this.model.id + '/upload';
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

    initialize: function(){
        this.template = _.template(tpl.get('updateBtnTpl').text)
    },

    render: function(){
        this.$el.html( this.template );
        return this;
    }
});

