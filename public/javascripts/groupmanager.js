
function fillGroupSelect() {
    var clientId = $.cookie("clientId");
    var url = "/clients/"+ clientId +"/groups";
    $.get(url, function(groups){
        var selectForGroups = $('#mergeGroups');
            for (i = 0; i < groups.length; i++){
                var option = $('<option/>');
                option.attr('value', groups[i].id);
                option.text(groups[i].name);
                selectForGroups.append(option);
            }
            var selectForGroups = $('#excludeGroups');
            for (i = 0; i < groups.length; i++){
                var option = $('<option/>');
                option.attr('value', groups[i].id);
                option.text(groups[i].name);
                selectForGroups.append(option);
            };
    });
}

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

function mergeGroups(groupId){
    clientId = $.cookie("clientId");
    var siteId = $.cookie("siteId");

    var body = {resourceGroupId: groupId};
    var url = "/clients/" + clientId + "/groups/" + siteId + "/merge";
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

function differenceGroups(groupId){
    clientId = $.cookie("clientId");
    var siteId = $.cookie("siteId");

    var body = {resourceGroupId: groupId};
    var url = "/clients/" + clientId + "/groups/" + siteId + "/diff";
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
