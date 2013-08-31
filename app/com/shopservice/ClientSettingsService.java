package com.shopservice;

import com.shopservice.queries.Query;
import com.shopservice.queries.Update;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neuser50
 * Date: 26.08.13
 * Time: 13:28
 * To change this template use File | Settings | File Templates.
 */
public class ClientSettingsService {

    private DatabaseManager databaseManager;

    public ClientSettingsService() {
        databaseManager = new DatabaseManager(new ConnectionPool() {
            @Override
            public Connection getConnection() {
                return DB.getConnection();
            }

            @Override
            public void releaseConnection(Connection connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // todo log that the connection does not close
                    e.printStackTrace();
                }
            }
        });
    }

    public List<String> getProductIds(final String clientId) throws SQLException {
        return databaseManager.executeQueryForList(new Query<String>() {
            @Override
            public String getRawSql() {
                return "SELECT productIds FROM productiDs " +
                        "WHERE clientSettingsId =  ?";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientId);
            }

            @Override
            public String fill(ResultSet resultSet) throws SQLException {
                return resultSet.getString("productIds");
            }
        });

    }

    public void setProductIds(String clientId, List<String> productIds) throws SQLException {
        removeProductIds(clientId);

        for (String productId : productIds)
            addProductId(clientId, productId);

    }

    private void addProductId(final String clientId, final String productId) throws SQLException {
        databaseManager.executeUpdate(new Update() {
            @Override
            public String getRawSql() {
                return "INSERT INTO `ProductIDs` (`productIds`, `clientSettingsId`) VALUES (?, ?);";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, productId);
                statement.setObject(2, clientId);
            }
        });
    }

    private void removeProductIds(final String clientId) throws SQLException {
        databaseManager.executeUpdate(new Update() {
            @Override
            public String getRawSql() {
                return "DELETE ProductIDs.* FROM ProductIDs " +
                        "WHERE clientSettingsId = ?";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientId);
            }
        });
    }

    public String getSiteName(final String clientId) throws SQLException {
        return databaseManager.executeQueryForOne(new Query<String>(){

            @Override
            public String fill(ResultSet resultSet) throws SQLException {
                return resultSet.getString("result");
            }

            @Override
            public String getRawSql() {
                return "SELECT siteName AS result FROM ClientSettings " +
                        "WHERE id = ?";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientId);
            }
        });
    }

    public String getSiteUrl(final String clientId) throws SQLException {
        return databaseManager.executeQueryForOne(new Query<String>(){

            @Override
            public String fill(ResultSet resultSet) throws SQLException {
                return resultSet.getString("result");
            }

            @Override
            public String getRawSql() {
                return "SELECT siteUrl AS result FROM ClientSettings " +
                        "WHERE id = ?";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientId);
            }
        });
    }

    public String getDatabaseUrl(final String clientId) throws SQLException {
        return databaseManager.executeQueryForOne(new Query<String>(){

            @Override
            public String fill(ResultSet resultSet) throws SQLException {
                return resultSet.getString("result");
            }

            @Override
            public String getRawSql() {
                return "SELECT databaseUrl AS result FROM ClientSettings " +
                        "WHERE id = ?";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientId);
            }
        });
    }

    private abstract static class ClientSettingsQuery implements Query<String>
    {
        private String clientId;

        protected ClientSettingsQuery(String clientId) {
            this.clientId = clientId;
        }

        @Override
        public void prepare(PreparedStatement statement) throws SQLException {
            statement.setObject(1, clientId);
        }

        @Override
        public String fill(ResultSet resultSet) throws SQLException {
            return resultSet.getString("result");
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
