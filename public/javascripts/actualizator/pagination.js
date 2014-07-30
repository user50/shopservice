var app1 = app1 || {};

var PaginationView = Backbone.View.extend({
    tagName: 'div',
    className: 'row',

    events: {
        'click a.servernext': 'nextResultPage',
        'click a.serverprevious': 'previousResultPage',
        'click a.serverlast': 'gotoLast',
        'click a.page': 'gotoPage',
        'click a.serverfirst': 'gotoFirst',
        'click a.serverpage': 'gotoPage',
        'click .btn-group button': 'changeCount'
    },

    template: _.template($('#paginationTpl').html()),

    initialize: function () {
        this.listenTo(actVent, 'autolink:finished',
            function(){
                this.gotoFirst();
            }, this);

        this.collection.on('reset', this.render, this);
        this.collection.on('sync', this.render, this);
    },

    render: function () {
        var html = this.template(this.collection.info());
        this.$el.html(html);
        return this;
    },

    nextResultPage: function (e) {
        e.preventDefault();
        this.collection.requestNextPage();
    },

    previousResultPage: function (e) {
        e.preventDefault();
        this.collection.requestPreviousPage();
    },

    gotoFirst: function () {
//        e.preventDefault();
        this.collection.goTo(this.collection.information.firstPage);
    },

    gotoLast: function (e) {
        e.preventDefault();
        this.collection.goTo(this.collection.information.lastPage);
    },

    gotoPage: function (e) {
        e.preventDefault();
        var page = $(e.target).text();
        this.collection.goTo(page);
    },

    changeCount: function (e) {
        e.preventDefault();
        var per = $(e.target).text();
        this.collection.howManyPer(per);
    }
});