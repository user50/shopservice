var HeaderView = Backbone.View.extend({

    initialize: function(){
        this.template = _.template(tpl.get('headerTpl').text);
    },

    events: {
        'click #logoutLink' : function(){
            $.ajax({url: '/logout',
                type: 'POST',
                success: function(data, status, xhr) {
                    $.cookie('key', null);
                    window.location.href = '/';
                }});
        }
    },

    render: function(){
        var template = this.template();
        this.$el.html( template );
        return this;
    }
});