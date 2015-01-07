var SwitchButtons = Backbone.View.extend({
    tagName: 'div',
    className: 'row clearfix',

    initialize: function(options){
        this.options = options;
        this.template = _.template(tpl.get('switchBtnTpl').text);
    },

    events: {
        'click #manufacturersBtn' : function(){ currenciesRouter.navigate("/manufacturers", {trigger: true});},
        'click #productsBtn' : function(){ currenciesRouter.navigate("/categories", {trigger: true});}
    },

    render: function(){
        var template = this.template;
        this.$el.html( template );
        return this;
    }
});