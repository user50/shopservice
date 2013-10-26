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
    jQuery.post(url);
}

function showCategories(){
    if ($('input[name=site]:checked', '#sites').attr('siteId') != undefined){
        clientId = $.cookie("clientId");
        var url = "/clients/"+clientId+"/categories";
        $('#toCategoriesLink').css('display', 'none');
        jQuery.get(url, {}, displayCategories, "json");
    }
}

function displayCategories(categories){
    var categoriesUrl = "/clients/"+clientId+"/categories/";
    var form = document.getElementById("updateForm");
    form.innerHTML = "";
    form = document.getElementById("categories");
    form.innerHTML = "";

    for (i=0; i<categories.length; i++) {
        var category = categories[i];
        var span = document.createElement("span");
        span.setAttribute("id", category.id);
        span.setAttribute("class", "category");
        span.setAttribute("onClick", "showProducts(this.id)");
        span.innerHTML = category.name;
        form.appendChild(span);
        form.appendChild(document.createElement("br"));
    }
}

function showProducts(categoryId){
    var form = document.getElementById("categories");
    form.innerHTML = "";
    $('#toCategoriesLink').css('display', 'inline');
    var siteId = $.cookie("siteId");
    var productsUrl = "/clients/"+clientId+ "/sites/" + siteId + "/categories/"+categoryId+"/products";
    jQuery.get(productsUrl, {}, displayProducts, "json");
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
    var url = "/clients/"+clientId + "/sites/" + siteId + "/categories/"+categoryId+"/products/"+productId+"?checked=true";
    jQuery.ajax({
        url: url,
        type:"put"
    })
}

function deleteProduct(productId){
    if ($('#check_all').prop('checked') == true)
        $('#check_all').prop('checked', false);
    var siteId = $.cookie("siteId");
    var url = "/clients/"+clientId + "/sites/" + siteId + "/categories/"+categoryId+"/products/"+productId+"?checked=false";
    jQuery.ajax({
        url:url,
        type:"put"
    })
}

function selectAllProductsForCategory(){
    var checkedFlag = $('#check_all').prop('checked');
    var siteId = $.cookie("siteId");
    var url = "/clients/"+clientId + "/sites/" + siteId + "/categories/"+categoryId+"/products?checked=" + checkedFlag;
    jQuery.ajax({
        url:url,
        type:"put"
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
    var url = "/clients/" + clientId + "/sites";
    $.get(url, function(sites){
        var sitesDiv = $('#sites');
        sitesDiv.innerHTML = '';

        for (i = 0; i < sites.length; i++){
            var input = $('<input/>');
            input.attr('siteId', sites[i].id);
            input.attr('type', 'radio');
            input.attr('name', 'site');
            input.attr('id', sites[i].name);
            input.attr('onChange', 'setSite(this)');
            sitesDiv.append(input);

            var label = $('<label/>');
            label.attr('for', sites[i].name);
            label.text(sites[i].name);
            sitesDiv.append(label);
        }
    })
}

function addNewSite() {
    clientId = $.cookie("clientId");
    var url = "/clients/" + clientId + "/sites";
    jQuery.ajax({
        url:url,
        type:"post",
        contentType: "application/json",
        data: JSON.stringify($('#addNewSiteForm').serializeObject())
    });
    showSites();

}

function setSite (siteId) {
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

