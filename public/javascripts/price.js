function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}

function downloadPrice(){
    clientId = $.cookie("clientId");
    var url = "/client/"+clientId+"/formats/price/pricelist";
    var href = document.getElementById("downloadHref").setAttribute("href",url);
    href.click();
}

function generatePrice(){
    clientId = $.cookie("clientId");
    var url = "/client/"+clientId+"/formats/price/pricelist";
    jQuery.post(url);
}

function showCategories(){
    clientId = $.cookie("clientId");
    var url = "/clients/"+clientId+"/categories";
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
    var siteId = $.cookie("siteId");
    var productsUrl = "/clients/"+clientId+ "/sites/" + siteId + "/categories/"+categoryId+"/products";
    jQuery.get(productsUrl, {}, displayProducts, "json");
    $.cookie('categoryId', null);
    $.cookie('categoryId', categoryId);
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
          productNameCell.text(product.productName);

//        product price
        var productPriceCell = $('<td/>');
        productPriceCell.text('0.00');

//      published flag
        var publishFlag = $('<td/>');
        var imageUrl;
        if (i%3 == 0){
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

    $('#productsTable').oneSimpleTablePagination({rowsPerPage: 10});
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
            var button = $('<button/>').addClass('blue-btn').text(sites[i].name);
            button.attr('siteId', sites[i].id);
            button.attr('onClick', 'setSite(this)');
            sitesDiv.append(button);
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
    $.cookie("siteId", null);
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
