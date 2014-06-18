currentGroupId = -1;
currentCategoryId = -1;
clientId = Cookie.get('clientId');

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
                window.location.href = '/';
            }});
    });
});