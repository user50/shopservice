package com.shopservice.queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public interface Query<T> extends SqlOperation {

    public T fill(ResultSet resultSet) throws SQLException;
}
