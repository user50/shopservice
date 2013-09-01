package com.shopservice;

import com.shopservice.domain.ClientSettings;
import com.shopservice.queries.Query;
import com.shopservice.queries.Update;
import play.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    public ClientSettings getClientSettings(final String id) throws SQLException {
        return databaseManager.executeQueryForOne(new Query<ClientSettings>() {
            @Override
            public ClientSettings fill(ResultSet resultSet) throws SQLException {
                ClientSettings clientSettings = new ClientSettings();

                clientSettings.id = resultSet.getString("id");
                clientSettings.databaseUrl = resultSet.getString("databaseUrl");
                clientSettings.siteName = resultSet.getString("siteName");
                clientSettings.siteUrl = resultSet.getString("siteUrl");
                clientSettings.productIds = Arrays.asList( resultSet.getString("products").split(",") );

                return clientSettings;
            }

            @Override
            public String getRawSql() {
                return "SELECT ClientSettings.*, GROUP_CONCAT(`productIds`) AS products FROM ClientSettings " +
                        "JOIN ProductIDs ON ProductIDs.clientSettingsId = ClientSettings.id " +
                        "WHERE ClientSettings.id = ?";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, id);
            }
        });
    }

    public void updateClientSettings(final ClientSettings clientSettings) throws SQLException {
        if (clientSettings.id == null)
            throw new IllegalStateException("The field 'ClientSetting.id' can not be null for update operation");

        if (getClientSettings(clientSettings.id) == null)
            throw new IllegalStateException("A ClientSettings with specified id does not exist");

        databaseManager.executeUpdate(new Update() {
            @Override
            public String getRawSql() {
                return "UPDATE `ClientSettings` SET `siteName`=?, `siteUrl`=?, `databaseUrl`=? WHERE `id`=?;";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientSettings.siteName);
                statement.setObject(2, clientSettings.siteUrl);
                statement.setObject(3, clientSettings.databaseUrl);
                statement.setObject(4, clientSettings.id);
            }
        });
    }

    public ClientSettings createClientSettings(final ClientSettings clientSettings) throws SQLException {
        if (clientSettings.id != null && getClientSettings(clientSettings.id) != null)
            throw new IllegalStateException("A ClientSettings with specified id already exists");

        if (clientSettings.id == null)
            clientSettings.id = UUID.randomUUID().toString();

        databaseManager.executeUpdate(new Update() {
            @Override
            public String getRawSql() {
                return "INSERT INTO `ClientSettings` (`id`, `siteName`, `siteUrl`, `databaseUrl`) VALUES (?, ?, ?, ?);";
            }

            @Override
            public void prepare(PreparedStatement statement) throws SQLException {
                statement.setObject(1, clientSettings.id);
                statement.setObject(2, clientSettings.siteName);
                statement.setObject(3, clientSettings.siteUrl);
                statement.setObject(4, clientSettings.databaseUrl);
            }
        });

        return clientSettings;
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
        if (getClientSettings(clientId) == null)
            throw new IllegalStateException("A ClientSettings with specified id does not exist");

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

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
