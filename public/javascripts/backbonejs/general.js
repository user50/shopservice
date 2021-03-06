var app = app || {};
var ENTER_KEY = 13;
var currentGroupId = -1;
var currentCategoryId = -1;

$(function() {
    $('#mergeGroups').on('change', function() {
        mergeGroups(this.value, currentGroupId);
    });

    $('#excludeGroups').on('change', function() {
        excludeGroups(this.value, currentGroupId);
    });

    $('#downloadYMLBtn').on('click', function() {
        downloadPrice();
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
        vent.trigger('selectedGroup');

    }

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
        vent.trigger('selectedGroup');
    }

    $( document ).ajaxStart(function() {
        $( "#loader" ).show();
    });
    $( document ).ajaxStop(function() {
        $( "#loader" ).hide();
    });

    function downloadPrice(){
        var url = "/client/"+clientId + "/groups/" + currentGroupId + "/pricelists/YML";
        var href = document.getElementById("downloadYMLBtn").setAttribute("href",url);
        href.click();
    }
});
