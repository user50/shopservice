tpl = {

    // Hash of preloaded templates for the app
    templates:{},

    // Recursively pre-load all the templates for the app.
    // This implementation should be changed in a production environment. All the template files should be
    // concatenated in a single file.
    loadTemplates:function (callback) {

        var that = this;

        $.get('/assets/templates.html', function (data) {
            console.log("Loading templates..");
            var tpls = $('<div/>').html(data);
            var tepmlatesL = tpls.get(0);
            var tepmlatesArray = tepmlatesL.getElementsByTagName('script');
            for (var i = 0; i < tepmlatesArray.length; i++){
                var templateName = tepmlatesArray[i].id;
                that.templates[templateName] = tepmlatesArray[i];
            }
            callback();
        });

    },

    // Get template by name from hash of preloaded templates
    get:function (name) {
        return this.templates[name];
    }

};