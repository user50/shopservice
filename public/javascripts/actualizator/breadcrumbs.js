var app = app || {};

var UnlinkedBreadcrumbsView = Backbone.View.extend({
    el: '#unlinkedProductsBreadcrumbs',
    className: 'breadcrumb',

    template: _.template($('#unlinkedProductsBreadcrumbsTpl').html()),

    initialize: function(options) {
        this.options = options || {};
        this.render();
    },

    render: function(){
        this.$el.html(this.template(this.options));
    }
});