var app = app || {};

var Counter = Backbone.Model.extend({
    defaults: {
        count: -1
    }
});

var CounterView = Backbone.View.extend({
    initialize: function(){
        this.model.on('change:count', this.render, this)
    },

    render: function(){
//        this.$('.countProducts').text(this.model.get('count'));
        return this;
    }
});

app.Counter = new Counter();
app.CounterView = new CounterView({model: app.Counter});
var vent = _.extend({}, Backbone.Events);
