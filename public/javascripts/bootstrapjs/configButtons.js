var app = app || {};

var ConfigButtons = Backbone.View.extend({
    el: '#optionsConf',

    events: {
        'click #syncOption' : 'doSync'
    },

    doSync: function(){
        console.log("Sync products for " + clientId);

        $.ajax({
            url: '/clients/' + clientId + '/products/sync',
            type: 'POST',
            global: false
        });

        $.bootstrapGrowl("Процесс синхронизации запущен \n и будет завершен в течении 5 минут.",
            {ele: 'body', type: 'success', width: 350});
    }

});

app.ConfigButtons = new ConfigButtons();