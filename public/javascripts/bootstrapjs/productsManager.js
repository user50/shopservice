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

        this.model.set('productName', changedName);

        var product = this.model.toJSON();

        this.model.save(product, {wait: true,
            error: function(model, response){
                $.bootstrapGrowl("Ошибка! " + errorMessages[response.responseJSON.code],
                    {ele: '#editProductModal', type: 'danger', width: 350});
            },
            success: function(model, response){
                $.bootstrapGrowl("Изменения успешно сохранены!",
                    {ele: '#editProductModal', type: 'success', width: 350});
            }});
    },

    render: function(){
        this.$el.find('#productNameInputEdit').val(this.model.get('productName'));
    }
});