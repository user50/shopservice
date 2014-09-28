currentGroupId = -1;
currentCategoryId = -1;
clientId = $.cookie('clientId');

var errorMessages = {
    101: "Укажите название для прайс-листа.",
    102: "Прайс-лист с указанным названием уже сущевствует. Укажите другое название для прайс-листа.",
    103: "Формат парйс-листа должен быть указан.",
    104: "Укажите валюту страны, региона и валюту товаров в прайс-листе.",
    105: "Укажите курс валюты.",
    106: "Вы пытаетесь удалить категорию, у которой есть дочерние категории."
};

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