var app = app || {};

var EditProduct = Backbone.View.extend({
    el: '#editProductModal',

    initialize: function(){
        var tags = this.model;
        tags.on('change', this.render, this);
    },

    events: {
        'click #editProduct': 'editProduct',
        'click #showImageBtn' : function(){
                                    window.open(this.$el.find('#productImageUrl').val());
                                }
    },

    editProduct: function(){
        var changedName = this.$el.find('#productNameInputEdit').val();
        if (changedName != null && changedName != '')
            this.model.set('productName', changedName);

        var changedDesc = this.$el.find('#productDesc').val();
        if (changedDesc != null && changedDesc != '')
            this.model.set('description', changedDesc);

        var changedCustomCategoryId = $('#customProductCategory').val();
        if (changedCustomCategoryId != null && changedCustomCategoryId != 'null')
            this.model.set('customCategoryId', changedCustomCategoryId);

        var changedImageUrl = $('#productImageUrl').val();
        if (changedImageUrl != null && changedImageUrl != '')
            this.model.set('imageUrl', changedImageUrl);

        var product = this.model.toJSON();

        var that = this;
        this.model.save(product, {wait: true,
            error: function(model, response){
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: '#editProductModal', type: 'danger', width: 350});
            },
            success: function(model, response){
                $.bootstrapGrowl("Изменения успешно сохранены!",
                    {ele: '#editProductModal', type: 'success', width: 350});
                that.render();
            }});
    },

    updateModel: function(modelId){
        this.model = app.Products.get(modelId);
    },

    render: function(){
        this.$el.find('#productNameInputEdit').val(this.model.get('productName'));
        this.$el.find('#productCategory').val(this.model.get('categoryName'));
        var imageUrl = this.model.get('imageUrl');
        this.$el.find('#productImageUrl').val(imageUrl);
        this.$el.find('#productImageUrlLink').attr('href', imageUrl);
        this.$el.find('#productImageUrlLink').text(imageUrl);
        var customCategoryId = app.customCategories.get(this.model.get('customCategoryId'));

        if (customCategoryId != null && customCategoryId != 'undefined')
            this.$el.find('#productCategoryInPrice').val(app.customCategories.get(this.model.get('customCategoryId')).get('name'));
        else
            this.$el.find('#productCategoryInPrice').val('Не определена');

        this.$el.find('#productDesc').val(this.model.get('description'));
    }
});