function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}

function downloadPrice(){
    clientId = $.cookie("clientId");
    var siteId = $.cookie("siteId");
    var url = "/client/"+clientId + "/sites/" + siteId + "/formats/price/pricelist";
//    $.get(url);
    var href = document.getElementById("downloadHref").setAttribute("href",url);
    href.click();
}

function generatePrice(){
    clientId = $.cookie("clientId");
    var siteId = $.cookie("siteId");
    var url = "/client/"+clientId+ "/sites/" + siteId + "/formats/price/pricelist";
    $.ajax({url: url,
            type: 'post',
            beforeSend: function(){
                $('#loader').css('display', 'block');
            }}).done(function(){
                 $('#loader').css('display', 'none');
        });
}

function showCategories(){
    $('#updateForm').empty();
    var siteId = $.cookie("siteId");
    if ($.cookie("siteId") != undefined){
        clientId = $.cookie("clientId");
        var url = "/clients/"+clientId+"/categories?groupId=" + siteId;
        $('#toCategoriesLink').css('display', 'none');
        $.ajax({url: url,
                type: 'get',
                success: displayCategories,
                dataType: "json",
                beforeSend: function(){
                    $('#loader').css('display', 'block');
                }}).done(function(){
                $('#loader').css('display', 'none');
            });
    }
}

function displayCategories(categories){
    var categoriesList = categories.categories;
    $(".countProducts").empty();
    $(".countProducts").text(categories.totalCount);
    var categoriesDiv = $('#categories').empty().css('display', 'block');

    var ol = $('<ol/>');
    ol.attr('class', 'rectangle-list');

    for (var i=0; i<categoriesList.length; i++) {
        var category = categoriesList[i];
        var li = $('<li/>');
        var a = $('<a/>');
        a.attr('id', category.id);
        a.attr("onClick", "showProducts(this.id)");
        a.text(category.name + " ( " + categoriesList[i].count + " )");
        li.append(a);
        ol.append(li);
//        form.appendChild(span);
//        form.appendChild(document.createElement("br"));
    }
    categoriesDiv.append(ol);
}

function showProducts(categoryId){
    var categoriesDiv = $('#categories').empty();
    categoriesDiv.css('display', 'none');
    $('#toCategoriesLink').css('display', 'inline');
    var siteId = $.cookie("siteId");
    var productsUrl = "/clients/"+clientId+ "/groups/" + siteId + "/products";
    $.ajax({
        url: productsUrl,
        type: 'get',
        data: {categoryId: categoryId},
        success: displayProducts,
        dataType: 'json',
        beforeSend: function(){
            $('#loader').css('display', 'block');
        }}).done(function(){
            $('#loader').css('display', 'none');
        });
}

function displayProducts(products){

    var form = document.getElementById("productsTable");
    if (form != null)
     form.innerHTML = "";

    var table = $('<table></table>').attr('id', 'productsTable').addClass('simple-little-table').attr('cellspacing','0');
    table.append($('<col width="20px">'));
    table.append($('<col>'));
    table.append($('<col width="10px">'));
    table.append($('<col width="20px">'));
    tr = $('<tr/>');

    var checkAll = $('<input>');
    checkAll.attr('id', 'check_all');
    checkAll.attr('onChange','selectAllProductsForCategory()');
    checkAll.attr('type', 'checkbox');
    var th = $('<th></th>');
    th.append(checkAll);
    tr.append(th);
    tr.append("<th>Product Name</th>");
    tr.append("<th>Price, UAH</th>");
    tr.append("<th>Published</th>");
    table.append(tr);

    if (products.length == 0){
        var emptyRow = $('<tr/>');
        emptyRow.append($('<td/>'));
        emptyRow.append($('<td/>').text('Products not found.'));
        emptyRow.append($('<td/>'));
        emptyRow.append($('<td/>'));
        table.append(emptyRow);
        $("#updateForm").append(table);
        return;
    }
    for (i=0; i<products.length; i++) {
        var product = products[i];
        categoryId = product.categoryId;

        var productRow = $('<tr/>');
        if (i%2 == 0)
            productRow.addClass('even');

        // checkbox for product
        var checkBoxCell = $('<td/>');
        var input = $('<input>');
        input.attr("id", product.id);
        input.attr("categoryId", product.categoryId);
        input.attr("type", "checkbox");
        input.attr("class", "productCheck");
        if(product.checked == true){
            input.prop("checked","checked");
        }
        input.attr("onChange", "updateChecked(this.id,"+categoryId+")");
        checkBoxCell.append(input);

//        product Name
        var productNameCell = $('<td/>').addClass('product');
        var productNameLink = $('<a></a>').attr('href', product.url).attr('target', '_blank').text(product.productName);
        productNameCell.append(productNameLink);

//        product price
        var productPriceCell = $('<td/>');
        productPriceCell.text(product.price);

//      published flag
        var publishFlag = $('<td/>');
        var imageUrl;
        if (product.published == false){
           imageUrl = "assets/images/cross.png";
        } else {
            imageUrl = "assets/images/check.png";
        }
        var imageFlag = $('<img>').attr('alt', 'check').attr('src', imageUrl);
        imageFlag.width = 16;
        imageFlag.height = 16;
        publishFlag.append(imageFlag);

        productRow.append(checkBoxCell);
        productRow.append(productNameCell);
        productRow.append(productPriceCell);
        productRow.append(publishFlag);

        table.append(productRow);
    }

    var form = $("#updateForm");
    form.append(table);

    $('#productsTable').oneSimpleTablePagination({rowsPerPage: 15});
    var updateButton = document.getElementsByClassName("updateButton");
}

function updateChecked(productId){
    var input = $('#'+productId);
    if (input.prop("checked") == true){
        addProduct(productId);
    } else  {
        deleteProduct(productId);
    }
}

function addProduct(productId){
    var siteId = $.cookie("siteId");
    var url = "/clients/"+clientId + "/groups/" + siteId + "/products/"+productId+"?categoryId=" + categoryId;
    var body = {checked: true};
    jQuery.ajax({
        url: url,
        type:"put",
        data: JSON.stringify(body),
        contentType: 'application/json'
    })
}

function deleteProduct(productId){
    if ($('#check_all').prop('checked') == true)
        $('#check_all').prop('checked', false);
    var siteId = $.cookie("siteId");
    var url = "/clients/"+clientId + "/groups/" + siteId + "/products/"+productId+"?categoryId=" + categoryId;
    var body = {checked: false};
    jQuery.ajax({
        url:url,
        type:"put",
        data: JSON.stringify(body),
        contentType: 'application/json'
    })
}

function selectAllProductsForCategory(){
    var checkedFlag = $('#check_all').prop('checked');
    var siteId = $.cookie("siteId");
    var url = "/clients/"+clientId + "/groups/" + siteId + "/products?categoryId=" + categoryId;
    var body = {checked: checkedFlag};
    jQuery.ajax({
        url:url,
        type:"put",
        data: JSON.stringify(body),
        contentType: 'application/json'
    });

    $(document).on(' change','input[id="check_all"]',function() {
        $('.productCheck').prop("checked" , this.checked);
    });

//    var c = $('#check_all').checked;
//    $('*[class=productCheck]').each(function(){this.setAttribute('checked',c)})
//    var inputs = document.getElementById('updateForm').getElementsByTagName('input');
//    for (i=0; i<inputs.length;i++){
//        var input = inputs[i];
//        var checked = input.getAttribute("checked");
//        if (checked==null)
//            input.setAttribute("checked", "true");
//    }
}

function showSites(){
    clientId = $.cookie("clientId");
    siteId = $.cookie("siteId");
    var url = "/clients/"+ clientId +"/groups";
    $.get(url, function(sites){
        if (sites.length == 0){
            alert("You must add at least one group!")  ;
            return;
        }

        var sitesDiv = $('#sites');
        sitesDiv.empty();

        for (i = 0; i < sites.length; i++){
            var input = $('<input/>');
            input.attr('siteId', sites[i].id);
            input.attr('type', 'radio');
            input.attr('name', 'site');
            input.attr('id', sites[i].name);
            input.attr('onChange', 'setSite(this)');

            if (siteId == sites[i].id)
                input.prop('checked', 'true');

            sitesDiv.append(input);

            var label = $('<label/>');
            label.attr('for', sites[i].name);
            label.text(sites[i].name);
            sitesDiv.append(label);
        }
        showCategories();

//        var inputAdd = $('<input/>');
//        inputAdd.attr('id', 'inputAdd');
//        inputAdd.attr('type', 'radio');
//        inputAdd.attr('name', 'site');
//        sitesDiv.append(inputAdd);
//        var labelAdd = $('<label/>').attr('onClick', 'showFieldForNewSite()').attr('for', 'inputAdd').text('+');
//        sitesDiv.append(labelAdd);
    })
}

function setSite (siteId) {
    $('#addNewSite').animate({'left': '+=200px', 'opacity': 'hide'}, 'slow');
    $.cookie("siteId", siteId.attributes.getNamedItem('siteId').value);
    var categoryId = $.cookie("categoryId");
    showCategories();
}
//function cancelAllProductsForCategory(){
//    var url = "/clients/"+clientId+"/categories/"+categoryId+"/products?checked=false";
//    jQuery.ajax({
//        url:url,
//        type:"put"
//    });
//    var inputs = document.getElementById('updateForm').getElementsByTagName('input');
//    for (i=0; i<inputs.length;i++){
//        var input = inputs[i];
//        input.removeAttribute("checked");
//    }
//}

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

function getElementByAttributeValue(attribute, value)
{
    var allElements = document.getElementsByTagName('*');
    for (var i = 0; i < allElements.length; i++)
    {
        if (allElements[i].getAttribute(attribute) == value)
        {
            return allElements[i];
        }
    }
}

