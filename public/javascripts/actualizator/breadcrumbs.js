var UnlinkedBreadcrumbsView = Backbone.View.extend({
    tagName: 'ol',
    className: 'breadcrumb',


    initialize: function() {
        this.template = _.template(tpl.get('unlinkedProductsBreadcrumbsTpl').text);
        this.model.bind("change", this.render, this);
    },

    render: function(){
        var template = this.template(this.model.toJSON());
        this.$el.html( template );
        return this;
    }
});