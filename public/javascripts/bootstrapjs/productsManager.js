var app = app || {};

var EditProduct = Backbone.View.extend({
    el: '#editProductModal',

    initialize: function(){
        var tags = this.model;
        tags.on('change', this.render, this);
    },

    events: {
        'click #editProduct': 'editProduct'
    },

    editProduct: function(){
        var changedName = this.$el.find('#productNameInputEdit').val();
        var changedDesc = this.$el.find('#productDesc').val();
        var changedCustomCategoryId = $('#customProductCategory').val();

        console.log(this.model.get('productName'));
        this.model.set('productName', changedName);
        if (changedDesc != null && changedDesc != '')
            this.model.set('description', changedDesc);
        this.model.set('customCategoryId', changedCustomCategoryId);

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
        var customCategoryId = app.customCategories.get(this.model.get('customCategoryId'));

        if (customCategoryId != null && customCategoryId != 'undefined')
            this.$el.find('#productCategoryInPrice').val(app.customCategories.get(this.model.get('customCategoryId')).get('name'));
        else
            this.$el.find('#productCategoryInPrice').val('Такая же');

        this.$el.find('#productDesc').val(this.model.get('description'));
    }
});