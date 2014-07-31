var UnlinkedBreadcrumbsView = Backbone.View.extend({
    tagName: 'ol',
    className: 'breadcrumb',

    template: _.template($('#unlinkedProductsBreadcrumbsTpl').html()),

    initialize: function(options) {
//        this.options = options || {};
        this.model.bind("change", this.render, this);
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    }
});