function showGroups(){
    $('#firstGroup').find('option:not(:first)').remove();
    $('#secondGroup').find('option:not(:first)').remove();
    $('#deleteGroupId').find('option:not(:first)').remove();

    clientId = $.cookie("clientId");
    siteId = $.cookie("siteId");
    var url = "/clients/"+ clientId +"/groups";
    $.get(url, function(groups){
        var selectForFirstGroup = $('#firstGroup');
        var selectForSecondGroup = $('#secondGroup');
        var selectForDeleteGroup = $('#deleteGroupId');

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

        for (var i = 0; i < groups.length; i++){
            var option = $('<option/>');
            option.attr('value', groups[i].id);
            option.text(groups[i].name);

            selectForDeleteGroup.append(option);
        }
    })
}

function addNewSite() {
    clientId = $.cookie("clientId");
    var url = "/clients/" + clientId + "/groups";
    $.ajax({
        url:url,
        type:"post",
        contentType: "application/json",
        data: JSON.stringify($('#addNewSiteForm').serializeObject())
    });
    showSites();

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

function deleteGroup() {
    clientId = $.cookie("clientId");

    var deleteId = $("#deleteGroupId option:selected").val();
    var url = "/clients/" + clientId + "/groups/" + deleteId;
    $.ajax({ url: url,
            type: 'delete'});
    showGroups();
}

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
