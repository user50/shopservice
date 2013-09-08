function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null
}

function downloadPrice(){
  alert("Hello!");
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
    var productsUrl = "/clients/"+clientId+"/categories/"+categoryId+"/products";
    jQuery.get(productsUrl, {}, displayProducts, "json");
}

function displayProducts(products){
    var form = document.getElementById("updateForm");
    form.innerHTML = "";
    for (i=0; i<products.length; i++) {
        var product = products[i];
        var input = document.createElement("input");
        input.setAttribute("id", product.id);
        input.setAttribute("categoryId", product.categoryId);
        input.setAttribute("type", "checkbox");
        if(product.checked == true){
            input.setAttribute("checked","checked");
        }
        input.setAttribute("onChange", "updateChecked(this.id,"+product.categoryId+")");
        form.appendChild(input);

        var span = document.createElement("span");
        span.setAttribute("class", "product");
        span.innerHTML = product.productName;

        form.appendChild(span);
        form.appendChild(document.createElement("br"));
    }
}

function updateChecked(productId, categoryId){
    input = document.getElementById(productId);
    if (input.getAttribute("checked") == null){
        addProduct(productId, categoryId);
        input.setAttribute("checked","checked");
    } else  {
        deleteProduct(productId, categoryId);
        input.removeAttribute("checked");
    }
}

function addProduct(productId, categoryId){
    var url = "/clients/"+clientId+"/categories/"+categoryId+"/products/"+productId+"?checked=true";
    jQuery.ajax({
        url: url,
        type:"put"
    })
}

function deleteProduct(productId, categoryId){
    var url = "/clients/"+clientId+"/categories/"+categoryId+"/products/"+productId+"?checked=false";
    jQuery.ajax({
        url:url,
        type:"put"
    })
}
