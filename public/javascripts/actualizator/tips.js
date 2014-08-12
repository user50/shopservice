var TipView = Backbone.View.extend({
    className: 'row',

    initialize: function(options) {
        this.options = options || {};
        this.template = _.template(tpl.get('tipViewTpl').text);
        this.text = this.options.text;
    },

    render: function(){
        var template = this.template({text: this.text});
        this.$el.html( template );
        return this;

    }
})