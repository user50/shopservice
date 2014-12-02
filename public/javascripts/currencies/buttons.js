var BackButton = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    events : {
        'click #toManufacturersList' : function(){ window.location = "/currencies"}
    },

    initialize: function(){
        this.template = _.template(tpl.get('toManufacturerBtn').text);
    },

    render: function(){
        var template = this.template();
        this.$el.html( template );
        return this;
    }
});