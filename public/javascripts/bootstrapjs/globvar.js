currentGroupId = -1;
currentCategoryId = -1;
clientId = $.cookie('clientId');

$(function() {
    $( document ).ajaxStart(function() {
        $( "#loader" ).show();
    });
    $( document ).ajaxStop(function() {
        $( "#loader" ).hide();
    });

    $('#logoutLink').on('click', function(){
        $.ajax({url: '/logout',
                type: 'POST',
            success: function(data, status, xhr) {
                $.cookie('key', null);
                window.location.href = '/';
            }});
    });

});