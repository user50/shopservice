var app = app || {};
var ENTER_KEY = 13;
var currentGroupId = -1;

$(function() {
    $('#mergeGroups').on('change', function() {
        console.log(this.value+ "  "+ currentGroupId);
        mergeGroups(this.value, currentGroupId);
    });

    function mergeGroups(groupId, targetGroupId){
        var body = {resourceGroupId: groupId};
        var url = "/clients/client1/groups/" + targetGroupId + "/merge";
        $.ajax({url: url,
            type: 'put',
            data: JSON.stringify(body),
            contentType: "application/json",
            sync: false
            });
        app.GroupsView.trigger('mergeIsHappened');
    }
});
