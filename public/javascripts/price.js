function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}

function downloadPrice(){
  alert("Download!");
}

function generatePrice(){
    alert("Generate!");
}

function showCategories(){
    clientId = getURLParameter('clientId');
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

    var buttons = document.getElementById("selectButtons");
    buttons.style.display = 'none';
}

function showProducts(categoryId){
    var productsUrl = "/clients/"+clientId+"/categories/"+categoryId+"/products";
    jQuery.get(productsUrl, {}, displayProducts, "json");
}

function displayProducts(products){
    var form = document.getElementById("categories");
    form.innerHTML = "";
    form = document.getElementById("updateForm");
    form.innerHTML = "";
    for (i=0; i<products.length; i++) {
        var product = products[i];
        categoryId = product.categoryId;
        var input = document.createElement("input");
        input.setAttribute("id", product.id);
        input.setAttribute("categoryId", product.categoryId);
        input.setAttribute("type", "checkbox");
        if(product.checked == true){
            input.setAttribute("checked","checked");
        }
        input.setAttribute("onChange", "updateChecked(this.id,"+categoryId+")");
        form.appendChild(input);

        var span = document.createElement("span");
        span.setAttribute("class", "product");
        span.innerHTML = product.productName;

        form.appendChild(span);
        form.appendChild(document.createElement("br"));
    }
    var selectButtons = document.getElementById("selectButtons");
    selectButtons.style.display = 'block';

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
    var url = "/clients/"+clientId+"/categories/"+categoryId+"/products/"+productId+"?checked=true";
    jQuery.ajax({
        url: url,
        type:"put"
    })
}

function deleteProduct(productId){
    var url = "/clients/"+clientId+"/categories/"+categoryId+"/products/"+productId+"?checked=false";
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
