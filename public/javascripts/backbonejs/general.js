var app = app || {};

$(function() {
    var groups = new app.Groups();
    groups.fetch();

    var groupsView = new app.GroupsView({collection: groups});

    var categories = new app.Categories();
    categories.fetch();

    var categoriesView = new app.CategoriesView({collection: categories});
    categoriesView.render();

    var addGroupView = new app.AddGroup({collection: groups});
});
