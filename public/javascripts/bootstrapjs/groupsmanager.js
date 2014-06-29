var app = app || {};

var PriceManager = Backbone.View.extend({
    el: '#priceManagerDiv',

    initialize: function(){
        this.listenTo(vent, 'group: deleted', this.disableButtons);
        this.listenTo(vent, 'group: selected', this.enableButtons);
        this.AddGroup = new AddGroup({collection: app.Groups});
        this.EditGroup = new EditGroup({collection: app.Groups});
        this.DeleteGroup = new DeleteGroup({collection: app.Groups});
        if (app. Groups.size() == 0){
            this.disableButtons();
        }
    },

    events: {
        'click #createNewPriceBtn': function(){this.AddGroup.clear()},
        'click #editPriceBtn': function(){this.EditGroup.render()},
        'click #downloadPriceBtn': 'downloadPrice'
    },

    downloadPrice: function(){
        console.log('downloading price...');
        var url = "/client/"+clientId + "/groups/" + currentGroupId + "/pricelist";
        window.open(url);
    },

    disableButtons: function(){
        this.$el.find('#downloadPriceBtn').attr('disabled', true);
        this.$el.find('#editPriceBtn').attr('disabled', true);
        this.DeleteGroup.disable();
    },

    enableButtons: function(){
        this.$el.find('#downloadPriceBtn').attr('disabled', false);
        this.$el.find('#editPriceBtn').attr('disabled', false);
        this.DeleteGroup.enable();
    }
});

var AddGroup = Backbone.View.extend({
    el: '#createPriceModal',

    initialize: function() {
    },

    events: {
        'click #saveNewPrice': 'addGroup',
        'change #priceFormatSelect' : function(e){this.setFormat(e.currentTarget.value)},
        'change #priceRegCurrencySelect' : function(e){this.setRegionCurrency(e.currentTarget.value)},
        'change #priceCurrencySelect' : function(e){this.setCurrency(e.currentTarget.value)},
        'change #priceCurrencyRateSelect' : function(e){this.setRateType(e.currentTarget.value)}
    },

    initialize: function(){
    },

    addGroup: function(e){
        //TODO validation
        var name = this.$el.find('#priceNameInput').val();
        var format = this.$el.find('#priceFormatSelect option:selected').val();
        var regionalCurrency = this.$el.find('#priceRegCurrencySelect option:selected').val();
        var currency = this.$el.find('#priceCurrencySelect option:selected').val();
        var rate = 1;
        if (regionalCurrency != currency){
            rate = this.$el.find('#priceCurrencyRateSelect').val();
            if (rate == 'custom')
                rate = this.$el.find('#priceCurrencyRate').val();
        } else {
            currency = null;
        }
        var newGroup = new Group({name: name, format: format,
            regionalCurrency: regionalCurrency, productCurrency: currency, rate: rate});

        this.collection.create(newGroup, {wait: true,
            error: function(model, response){
                $('#saveNewPriceAlert').show();
                $('#saveNewPriceAlert').attr("class","alert alert-danger");
                $('#saveNewPriceAlert').text("Ошибка! " + response.responseText);
            },
            success: function(model, response){
                $('#saveNewPriceAlert').show();
                $('#saveNewPriceAlert').attr("class","alert alert-success");
                $('#saveNewPriceAlert').text("Изменения успешно сохранены!");
            }});
    },

    setFormat: function(format){
        console.log('selected format is ' + format);
        this.resetCurrency(false);
        this.resetRateType(true);
        this.resetRate(true);
        if (format == 'price'){
            this.$el.find('option[value=RUB]').attr("disabled", true);
            this.$el.find('option[value=BYR]').attr("disabled", true);
            this.$el.find('option[value=KZT]').attr("disabled", true);
            this.$el.find('option[value=EUR]').attr("disabled", true);
            this.$el.find('option[value=NBU]').attr("disabled", true);
            this.$el.find('option[value=CBRF]').attr("disabled", true);
            this.$el.find('option[value=NBK]').attr("disabled", true);
        } else {
            this.$el.find('option[value=RUB]').attr("disabled", false);
            this.$el.find('option[value=BYR]').attr("disabled", false);
            this.$el.find('option[value=KZT]').attr("disabled", false);
            this.$el.find('option[value=EUR]').attr("disabled", false);
            this.$el.find('option[value=NBU]').attr("disabled", false);
            this.$el.find('option[value=CBRF]').attr("disabled", false);
            this.$el.find('option[value=NBK]').attr("disabled", false);
        }
    },

    setRegionCurrency: function(regionCurrency){
        console.log('selected regionCurrency is ' + regionCurrency);
        this.resetCurrency(false);
        this.resetRateType(true);
        this.resetRate(true);
    },

    setCurrency: function(currency){
        console.log('selected currency is ' + currency);
        var regionCurrency = this.$el.find('#priceRegCurrencySelect option:selected').val();
        if (regionCurrency != currency){
            this.resetRateType(false);
            this.resetRate(true);
        } else {
            this.resetRateType(true);
            this.resetRate(true);
        }
    },

    setRateType: function(rateType){
        console.log('selected rate type is ' + rateType);
        if (rateType == 'custom'){
            this.resetRate(false)
        } else {
            this.resetRate(true);
        }
    },

    resetCurrency: function(disabled){
        this.$el.find('#priceCurrencySelect').attr('disabled', disabled);
        this.$el.find('#priceCurrencySelect').val('none');
    },

    resetRateType: function(disabled){
        this.$el.find('#priceCurrencyRateSelect').attr('disabled', disabled);
        this.$el.find('#priceCurrencyRateSelect').val('none');
    },

    resetRate: function(disabled){
        this.$el.find('#priceCurrencyRate').attr('disabled', disabled);
        this.$el.find('#priceCurrencyRate').val('');
    },

    clear: function(){
        console.log('clear form for creating a price');
        this.$el.find('#invalidNewPriceName').hide();
        this.$el.find('#priceNameInput').val('');
        this.$el.find('#priceFormatSelect').val('YML');
        this.$el.find('#priceCurrencySelect').val('none');
        this.$el.find('#priceRegCurrencySelect').val('none');
        $('#saveNewPriceAlert').hide();
        this.resetRateType(true);
        this.resetRate(true);
    }
});

var EditGroup = Backbone.View.extend({
    el: '#editPriceModal',

    events: {
        'click #saveEditedPrice': 'editGroup',
        'change #priceFormatSelectEdit' : function(e){this.setFormat(e.currentTarget.value)},
        'change #priceRegCurrencySelectEdit' : function(e){this.setRegionCurrency(e.currentTarget.value)},
        'change #priceCurrencySelectEdit' : function(e){this.setCurrency(e.currentTarget.value)},
        'change #priceCurrencyRateSelectEdit' : function(e){this.setRateType(e.currentTarget.value)}
    },

    editGroup: function(e){
        //TODO validation
        var changedName = this.$el.find('#priceNameInputEdit').val();
        var changedFormat = this.$el.find('#priceFormatSelectEdit option:selected').val();
        var changedRegionalCurrency = this.$el.find('#priceRegCurrencySelectEdit option:selected').val();
        var changedCurrency = this.$el.find('#priceCurrencySelectEdit option:selected').val();
        var changedRate = 1;
        if (changedRegionalCurrency != changedCurrency){
            changedRate = this.$el.find('#priceCurrencyRateSelectEdit').val();
            if (changedRate == 'custom')
                changedRate = this.$el.find('#priceCurrencyRateEdit').val();
        }  else {
            changedCurrency = null;
        }
        var changedGroup = this.collection.get(currentGroupId);
        changedGroup.save({name: changedName, format: changedFormat,
            regionalCurrency: changedRegionalCurrency, productCurrency: changedCurrency, rate: changedRate}, {wait: true,
            error: function(model, response){
                $('#saveEditAlert').show();
                $('#saveEditAlert').attr("class","alert alert-danger");
                $('#saveEditAlert').text("Ошибка! " + response.responseText);
            },
            success: function(model, response){
                $('#saveEditAlert').show();
                $('#saveEditAlert').attr("class","alert alert-success");
                $('#saveEditAlert').text("Изменения успешно сохранены!");
            }});
    },

    render: function(){
        this.$el.find('#invalidPriceName').hide();
        var group = app.Groups.get(currentGroupId);
        this.$el.find('#priceNameInputEdit').val(group.get('name'));
        this.$el.find('#priceFormatSelectEdit').val(group.get('format'));
        this.renderCurrencySettings();
        var url = "http://mservice.herokuapp.com/client/"+clientId + "/groups/" + currentGroupId + "/pricelist";
        this.$el.find('#priceLink').text(url);
        $('#saveEditAlert').hide();
    },

    renderCurrencySettings: function(){
        var group = app.Groups.get(currentGroupId);
        this.setRegionCurrency(group.get('regionalCurrency'));
        if (group.get('productCurrency') == null){
            this.resetCurrency(false, group.get('regionalCurrency'));
            this.resetRateType(true, 'none');
            this.resetRate(true, '');
            return;
        }
        this.setCurrency(group.get('productCurrency'));
        var rate = group.get('rate');
        if (rate != 1){
            this.resetRateType(false, 'none');
            switch (rate)
            {
                case 'NBU' :
                    this.resetRateType(false, rate);
                    this.resetRate(true, '');
                    break;
                case 'CBRF' :
                    this.resetRateType(false, rate);
                    this.resetRate(true, '');
                    break;
                case 'NBK' :
                    this.resetRateType(false, rate);
                    this.resetRate(true, '');
                    break;
                default: {
                    this.resetRateType(false, 'custom');
                    this.resetRate(false, rate);
                }
            }
        }
    },

    setFormat: function(format){
        console.log('selected format is ' + format);
        this.resetCurrency(false, 'none');
        this.resetRateType(true, 'none');
        this.resetRate(true, '');
        if (format == 'price'){
            this.$el.find('option[value=RUB]').attr("disabled", true);
            this.$el.find('option[value=BYR]').attr("disabled", true);
            this.$el.find('option[value=KZT]').attr("disabled", true);
            this.$el.find('option[value=EUR]').attr("disabled", true);
            this.$el.find('option[value=NBU]').attr("disabled", true);
            this.$el.find('option[value=CBRF]').attr("disabled", true);
            this.$el.find('option[value=NBK]').attr("disabled", true);
        } else {
            this.$el.find('option[value=RUB]').attr("disabled", false);
            this.$el.find('option[value=BYR]').attr("disabled", false);
            this.$el.find('option[value=KZT]').attr("disabled", false);
            this.$el.find('option[value=EUR]').attr("disabled", false);
            this.$el.find('option[value=NBU]').attr("disabled", false);
            this.$el.find('option[value=CBRF]').attr("disabled", false);
            this.$el.find('option[value=NBK]').attr("disabled", false);
        }
    },

    setRegionCurrency: function(regCurrency){
        console.log('selected regionCurrency is ' + regCurrency);
        this.$el.find('#priceRegCurrencySelectEdit').val(regCurrency);
        this.resetCurrency(false, 'none');
        this.resetRateType(true, 'none');
        this.resetRate(true, '');
    },

    setCurrency: function(currency){
        console.log('selected currency is ' + currency);
        this.$el.find('#priceCurrencySelectEdit').val(currency);
        var regionCurrency = this.$el.find('#priceRegCurrencySelectEdit option:selected').val();
        if (regionCurrency != currency){
            this.resetRateType(false, 'none');
            this.resetRate(true, '');
        } else {
            this.resetRateType(true, 'none');
            this.resetRate(true, '');
        }
    },

    setRateType: function(rateType){
        console.log('selected rate type is ' + rateType);
        if (rateType == 'custom'){
            this.resetRate(false, '');
        } else {
            this.resetRate(true, '');
        }
    },

    resetCurrency: function(disabled, currency){
        this.$el.find('#priceCurrencySelectEdit').attr('disabled', disabled);
        this.$el.find('#priceCurrencySelectEdit').val(currency);
    },

    resetRateType: function(disabled, rateType){
        this.$el.find('#priceCurrencyRateSelectEdit').attr('disabled', disabled);
        this.$el.find('#priceCurrencyRateSelectEdit').val(rateType);
    },

    resetRate: function(disabled, rate){
        this.$el.find('#priceCurrencyRateEdit').attr('disabled', disabled);
        this.$el.find('#priceCurrencyRateEdit').val(rate);
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
            message: "Вы уверены, что хотите удалить прайс-лист  \"" + groupName + "\" ?",
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
                        app.Groups.get(currentGroupId).destroy();
                        app.categories.reset();
                        new BreadcrumbsView({categoryName: null, groupName: null});
                        app.ProductsView.$el.hide();
                        app.pagination.$el.hide();
                        vent.trigger('group: deleted');
                    }
                }
            }
        });
    },

    enable: function(){
        this.$el.attr('disabled', false);
    },

    disable: function(){
        this.$el.attr('disabled', true);
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