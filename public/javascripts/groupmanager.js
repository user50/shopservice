function showGroups(){
    clientId = $.cookie("clientId");
    siteId = $.cookie("siteId");
    var url = "/clients/"+ clientId +"/groups";
    $.get(url, function(groups){
        var selectForFirstGroup = $('#firstGroup');
        var selectForSecondGroup = $('#secondGroup');

        for (var i = 0; i < groups.length; i++){
            var option = $('<option/>');
            option.attr('value', groups[i].id);
            option.text(groups[i].name);

            selectForFirstGroup.append(option);
        }

        for (var i = 0; i < groups.length; i++){
            var option = $('<option/>');
            option.attr('value', groups[i].id);
            option.text(groups[i].name);

            selectForSecondGroup.append(option);
        }
    })
}

function mergeGroups(){
    clientId = $.cookie("clientId");

    var firstGroupId = $("#firstGroup option:selected").val();
    var secondGroupId = $("#secondGroup option:selected").val();
    var body = {resourceGroupId: secondGroupId};
    var url = "/clients/" + clientId + "/groups/" + firstGroupId + "/merge";
    $.ajax({url: url,
        type: 'put',
        data: JSON.stringify(body),
        contentType: "application/json",
        beforeSend: function(){
            $('#loader').css('display', 'block');
        }}).done(function(){
            $('#loader').css('display', 'none');
        });
}

function differenceGroups(){
    clientId = $.cookie("clientId");

    var firstGroupId = $("#firstGroup option:selected").val();
    var secondGroupId = $("#secondGroup option:selected").val();
    var body = {resourceGroupId: secondGroupId};
    var url = "/clients/" + clientId + "/groups/" + firstGroupId + "/diff";
    $.ajax({url: url,
        type: 'put',
        data: JSON.stringify(body),
        contentType: "application/json",
        beforeSend: function(){
            $('#loader').css('display', 'block');
        }}).done(function(){
            $('#loader').css('display', 'none');
        });

}