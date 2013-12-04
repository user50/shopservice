
function fillGroupSelect() {
    var clientId = $.cookie("clientId");
    var url = "/clients/"+ clientId +"/groups";
    $.get(url, function(groups){
        var selectForGroups = $('#group');

        for (i = 0; i < groups.length; i++){
            var option = $('<option/>');
            option.attr('value', groups[i].id);
            option.text(groups[i].name);

            selectForGroups.append(option);
        }
    })
}
function downloadPrice(){
    clientId = $.cookie("clientId");

    var groupId = $("#group option:selected").val();
    var format = $("#format option:selected").val();

    var url = "/client/"+clientId + "/groups/" + groupId + "/pricelists/" + format;
    var href = document.getElementById("downloadHref").setAttribute("href",url);
    href.click();
}

function generatePrice(){
    clientId = $.cookie("clientId");

    var groupId = $("#group option:selected").val();
    var format = $("#format option:selected").val();

    var url = "/client/"+clientId + "/groups/" + groupId + "/pricelists/" + format;
    $.ajax({url: url,
        type: 'post',
        beforeSend: function(){
            $('#loader').css('display', 'block');
        }}).done(function(){
            $('#loader').css('display', 'none');
        });
}

