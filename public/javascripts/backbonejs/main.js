$(function(){
    window.App = {
        Models: {},
        Views: {},
        Collections: {},
        Router: {}
    };

    window.template = function (id){
        return _.template( $('#' + id).html());
    };

    var vent = _.extend({}, Backbone.Events);

    App.Views.SpecialTasks = Backbone.View.extend({
        initialize: function(){
            vent.on('specialTask:show', this.show, this)
        },

        show: function(id){
            console.log("Show task: " + id)
        }
    });

    App.Router = Backbone.Router.extend({
        routes: {
            ''           : 'start',
            'specialTask/:id': 'showSpecialTask'
        },

        start: function(){
            console.log('Start page))');
        } ,

        showSpecialTask: function(id){
            vent.trigger('specialTask:show', id);
        }
    });

    new App.Router();
    new App.Views.SpecialTasks();
    Backbone.history.start();
});