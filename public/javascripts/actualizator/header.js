var HeaderView = Backbone.View.extend({

    initialize: function(){
        this.template = _.template(tpl.get('headerTpl').text);
    },

    render: function(){
        var template = this.template();
        this.$el.html( template );
        return this;
    }
});