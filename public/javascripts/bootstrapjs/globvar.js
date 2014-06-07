currentGroupId = -1;
currentCategoryId = -1;
clientId = "client1";

$(function() {
    $( document ).ajaxStart(function() {
        $( "#loader" ).show();
    });
    $( document ).ajaxStop(function() {
        $( "#loader" ).hide();
    });
})