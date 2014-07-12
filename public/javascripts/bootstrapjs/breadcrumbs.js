var app = app || {};

var BreadcrumbsView = Backbone.View.extend({
    model: app.Category,

    el: '#breadcrumbsView',
    className: 'breadcrumb',

    events: {
        'click #goToCategoriesLink' : function(){
                                app.router.navigate('groups/' + currentGroupId , {trigger: true});
                            }
    },

    template: _.template($('#breadcrumbsViewTemplate').html()),

    initialize: function(options) {
        this.options = options || {};
        this.render();
    },

    render: function(){
        this.$el.html(this.template(this.options));
    }
});

new BreadcrumbsView({categoryName: null, groupName: null});