var app = app || {};
$(function() {
    var groups = new Groups();
    groups.fetch();

    var addGroupView = new AddGroup({collection: groups});

//    categories.listenTo(groups, 'change', categories.meta('currentGroupId', groups.find(function(model) { return model.get('checked') == true; })));
});
