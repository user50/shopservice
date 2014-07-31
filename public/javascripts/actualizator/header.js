var app1 = app1 || {};

var HeaderView = Backbone.View.extend({
    template: _.template($('#headerTpl').html()),

    render: function(){
        var template = this.template;
        this.$el.html( template );
        return this;
    }
});