package com.shopservice.queries;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlOperation {
    public String getRawSql();

    public void prepare(PreparedStatement statement) throws SQLException;
}
