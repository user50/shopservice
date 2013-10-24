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
    clientId = $.cookie("clientId");
    var url = "/clients/"+clientId+"/categories";
    $('#toCategoriesLink').css('display', 'none');
    jQuery.get(url, {}, displayCategories, "json");
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

    var table = $('<table></table>').attr('id', 'productsTable').addClass('simple-little-table').attr('cellspacing','0');
    table.append($('<col width="20px">'));
    table.append($('<col>'));
    table.append($('<col width="10px">'));
    table.append($('<col width="20px">'));
    tr = $('<tr/>');

    var checkAll = $('<input>');
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
        var input = document.createElement("input");
        input.setAttribute("id", product.id);
        input.setAttribute("categoryId", product.categoryId);
        input.setAttribute("type", "checkbox");
        if(product.checked == true){
            input.setAttribute("checked","checked");
        }
        input.setAttribute("onChange", "updateChecked(this.id,"+categoryId+")");
        checkBoxCell.append(input);

//        product Name
        var productNameCell = $('<td/>').addClass('product');
        var productNameLink = $('<a></a>').attr('href', product.url).text(product.productName);
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
    input = document.getElementById(productId);
    if (input.getAttribute("checked") == null){
        addProduct(productId);
        input.setAttribute("checked","checked");
    } else  {
        deleteProduct(productId);
        input.removeAttribute("checked");
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
    var siteId = $.cookie("siteId");
    var url = "/clients/"+clientId + "/sites/" + siteId + "/categories/"+categoryId+"/products/"+productId+"?checked=false";
    jQuery.ajax({
        url:url,
        type:"put"
    })
}

function selectAllProductsForCategory(){
    var url = "/clients/"+clientId+"/categories/"+categoryId+"/products?checked=true";
    jQuery.ajax({
        url:url,
        type:"put"
    });
    var inputs = document.getElementById('updateForm').getElementsByTagName('input');
    for (i=0; i<inputs.length;i++){
        var input = inputs[i];
        var checked = input.getAttribute("checked");
        if (checked==null)
            input.setAttribute("checked", "true");
    }
}

function showSites(){
    clientId = $.cookie("clientId");
    var url = "/clients/" + clientId + "/sites";
    $.get(url, function(sites){
        var sitesDiv = $('#sites');
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
}

function setSite (siteId) {
    var oldSiteId = $.cookie("siteId");
    var allButtons = document.getElementsByTagName('button');
    for (var i = 0; i < allButtons.length; i++)
    {
        if (allButtons[i].getAttribute('siteId') == oldSiteId);
        {
            allButtons[i].attributes.getNamedItem('class').value = 'blue-btn';
        }
    }
    siteId.attributes.getNamedItem('class').value = 'blue-btn-select';
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

