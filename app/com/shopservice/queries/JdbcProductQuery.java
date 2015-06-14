package com.shopservice.queries;

import com.shopservice.ProductConditions;
import com.shopservice.Queries;
import com.shopservice.Services;
import com.shopservice.transfer.Category;
import com.shopservice.transfer.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
public class JdbcProductQuery implements Query<Product> {
    protected String clientId;
    protected ProductConditions conditions;

    public JdbcProductQuery(String clientId, ProductConditions conditions) {
        this.clientId = clientId;
        this.conditions = conditions;
    }

    @Override
    public Product fill(ResultSet resultSet) throws SQLException {
        Product product = new Product();

        product.id = resultSet.getString("id");
        product.manufacturer = resultSet.getString("manufacturer");
        product.name = resultSet.getString("name");
        product.price = resultSet.getDouble("price");
        product.usdPrice = resultSet.getDouble("usdPrice");
        //product.available = resultSet.getBoolean("available");
        product.shortDescription = resultSet.getString("shortDescription");
        product.description = resultSet.getString("description");
        //product.warranty = resultSet.getString("warranty");
        product.url = resultSet.getString("url");
        product.imageUrl = resultSet.getString("imageUrl");
        product.published = resultSet.getBoolean("published");
        product.available = resultSet.getBoolean("available");

        Category category = new Category();
              category.id = resultSet.getString("categoryId");
        category.name = resultSet.getString("categoryName");
        category.parentId = resultSet.getString("categoryParentId");

        product.category = category;

        product.url = Services.getUrlGenerator(clientId).generateProductUrl(product);
        product.imageUrl = Services.getUrlGenerator(clientId).generateProductImageUrl(product);

        return product;
    }

    @Override
    public String getRawSql() {
        String sql = Queries.getInstance().getProductQuery(clientId) ;

        List<String> conditions = new ArrayList<>();

        if (this.conditions.categoryId != null || !this.conditions.productIds.isEmpty() || !this.conditions.words.isEmpty())
            sql += " HAVING ";

        if (this.conditions.categoryId != null)
            conditions.add("categoryId = ?");

        if (this.conditions.productIds != null && !this.conditions.productIds.isEmpty() ) {
            String[] abc = new String[this.conditions.productIds.size()];
            Arrays.fill(abc, "?");

            conditions.add("id IN("+Arrays.asList(abc).toString().replace("[","").replace("]","")+")");
        }

        if (this.conditions.words != null )
            for (String word : this.conditions.words)
                conditions.add("name LIKE ?");

        Iterator<String> iterator = conditions.iterator();
        while (iterator.hasNext())
            sql += " "+iterator.next() + (iterator.hasNext() ? " AND": "" );

        if (this.conditions.limit !=null && this.conditions.offset != null)
            sql += " LIMIT ? OFFSET ?";

        return sql;
    }

    @Override
    public void prepare(PreparedStatement statement) throws SQLException {
        int i = 1;

        if (conditions.categoryId != null)
            statement.setString(i++, conditions.categoryId);

        for (String productId : conditions.productIds)
            statement.setString(i++, productId);

        for (String word : conditions.words)
            statement.setString(i++, "%"+word+"%");

        if (this.conditions.limit !=null && this.conditions.offset != null) {
            statement.setInt(i++, this.conditions.limit);
            statement.setInt(i, this.conditions.offset);
        }

    }
}
