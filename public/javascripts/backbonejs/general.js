var app = app || {};
var ENTER_KEY = 13;
var currentGroupId = -1;

$(function() {
    $('#mergeGroups').on('change', function() {
        mergeGroups(this.value, currentGroupId);
    });

    $('#excludeGroups').on('change', function() {
        excludeGroups(this.value, currentGroupId);
    });

    function mergeGroups(groupId, targetGroupId){
        var body = {resourceGroupId: groupId};
        var url = "/clients/" + clientId + "/groups/" + targetGroupId + "/merge";
        $.ajax({url: url,
            type: 'put',
            data: JSON.stringify(body),
            contentType: "application/json",
            async: false
            });
        app.categories.trigger('mergeIsHappened', currentGroupId);
    };

    function excludeGroups(groupId, targetGroupId){
        var body = {resourceGroupId: groupId};
        var url = "/clients/" + clientId + "/groups/" + targetGroupId + "/diff";
        $.ajax({url: url,
            type: 'put',
            data: JSON.stringify(body),
            contentType: "application/json",
            async: false
            });
        app.categories.trigger('excludeIsHappened', currentGroupId);
    }
});
