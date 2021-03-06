var Provider = Backbone.Model.extend({
    urlRoot: function(){
        return '/clients/'+clientId+'/providers';
    }
});

var Providers = Backbone.Collection.extend({
    model: Provider,

    url: function(){
        return '/clients/'+clientId+'/providers';
    }
});

var ProviderView = Backbone.View.extend({
    tagName: 'tr',

    events: {
        'click .btn-danger' : 'removeThis',
        'click .btn-warning' : 'editThis',
        'click .btn-primary' : function(){
            actualizationRouter.navigate('providers/' + this.model.id + "/linkProducts", {trigger: true});
        },
        'click .btn-default' : function(){
            actualizationRouter.navigate('providers/' + this.model.id + "/waitingProducts", {trigger: true});
        },
        'click .btn-info' : function(){
            actualizationRouter.navigate('providers/' + this.model.id + "/linkedProducts", {trigger: true});
        }
    },

    initialize: function(){
        this.template = _.template(tpl.get('providerViewTemplate').text);
        this.model.on('change', this.render, this);
        this.model.on('destroy', this.remove, this);
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    },

    editThis: function(){
        actVent.trigger('provider:edit', this.model.id);
    },

    removeThis: function(){
        var model = this.model;
        bootbox.dialog({
            message: "Вы уверены, что хотите удалить поставщика  \"" + this.model.get('name') + "\" ?",
            buttons: {
                success: {
                    label: "Отмена",
                    className: "btn-default",
                    callback: function() {
                    }
                },
                danger: {
                    label: "Удалить",
                    className: "btn-danger",
                    callback: function() {
                        model.destroy();
                        $.bootstrapGrowl("Поставщик \"" + model.get('name') + "\" был удален!",
                            {ele: 'body', type: 'success', width: 350});
                    }
                }
            }
        });
    }
});

var ProvidersView = Backbone.View.extend({
    tagName: 'table',
    className: 'table table-striped',

    initialize : function () {
        var tags = this.collection;
        tags.on('add', this.render, this);
        tags.on('remove', this.render, this);
    },

    addOne : function ( item ) {
        var view = new ProviderView({model:item});
        this.$el.append(view.render().el);
    },

    render: function(){
        console.log('Rendering of ProvidersView...');
        this.$el.empty();
        this.renderHeader();
        this.collection.each(this.addOne, this);
        return this;
    },

    renderHeader: function(){
        var thead = $('<thead/>');
        var tr = $('<tr/>');

        var th = $('<th class="col-md-1"></th>');
        tr.append(th);
        tr.append('<th  class="col-md-4">Название поставщика</th>');
        tr.append('<th class="col-md-4">Источник</th>');
        tr.append('<th class="col-md-3"></th>');
        this.$el.append(tr);
        console.log("Header is rendered...");
    }
});


