$(function(){
    var app = App || {};
    app.collection = App.Collections || {};
window.groups = new app.collection.Groups();
groups.fetch();
//var groupsView = new App.Views.Groups({collection: groups});
//var addGroup = new App.Views.AddGroup({collection: groups});
//
//window.categories = new App.Collections.Category({});
//categories.fetch({ data: { groupId: 1}});
//window.categoriesView = new App.Views.Categories({collection: categories});
//$('#categories').append(categoriesView.el);
});
