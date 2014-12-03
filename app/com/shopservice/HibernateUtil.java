package com.shopservice;

import com.shopservice.domain.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import snaq.db.DBPoolDataSource;

import java.sql.SQLException;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    private static HikariConnectionPool pool;

    static {
        try {
            DBPoolDataSource ds = new DBPoolDataSource();
            ds.setName("pool-ds");
            ds.setDescription("Pooling DataSource");
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://us-cdbr-east-05.cleardb.net:3306/heroku_20e5b087480e48d?useUnicode=yes&characterEncoding=utf8");
            ds.setUser("b02276676df1a5");
            ds.setPassword("2c270044");
            ds.setMinPool(1);
            ds.setMaxPool(3);
            ds.setMaxSize(5);
            ds.setIdleTimeout(3600);  // Specified in seconds.
            ds.setValidationQuery("SELECT COUNT(*) FROM Replicants");

//            pool = new HikariConnectionPool("jdbc:mysql://us-cdbr-east-05.cleardb.net:3306/heroku_20e5b087480e48d?useUnicode=yes&characterEncoding=utf8" +
//                    "&user=b02276676df1a5&password=2c270044");

//            pool = new HikariConnectionPool("jdbc:mysql://localhost:3306/shopservice?" +
//                    "user=root&password=neuser50");

            Configuration configuration = new Configuration();
            addAnnotatedClasses( configuration );
            addProperties( configuration );
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(ssrb.applySetting(Environment.DATASOURCE, ds).build());



        } catch (ExceptionInInitializerError ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void addAnnotatedClasses(Configuration configuration)
    {
        configuration.addAnnotatedClass( ClientSettings.class )
                .addAnnotatedClass(ClientsCategory.class)
                .addAnnotatedClass(Group2Product.class)
                .addAnnotatedClass(LinkedProductEntry.class)
                .addAnnotatedClass(ProductEntry.class)
                .addAnnotatedClass(ProductGroup.class)
                .addAnnotatedClass(ProductProvider.class);
    }

    private static void addProperties(Configuration configuration)
    {
        configuration
                .setProperty("show_sql", "true")
                .setProperty( "dialect", "org.hibernate.dialect.MySQLDialect" );
//                .setProperty( "hibernate.c3p0.min_size", "5" )
//                .setProperty( "hibernate.c3p0.max_size", "20" )
//                .setProperty( "hibernate.c3p0.timeout", "1800" )
//                .setProperty( "hibernate.c3p0.max_statements", "50" )
//                .setProperty("hibernate.hbm2ddl.auto", "update");
    }

    /**
     * Gets hiberante session factory that was initialized at application startup.
     *
     * @return hibernate session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static <T> T execute(Query query)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T result = (T) query.execute(session);
        session.flush();
        session.close();

        return result;

    }

    public static void execute(Update update)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        update.execute(session);
        session.flush();
        session.close();
    }

    public static interface Query<T>
    {
        public <T> T execute(Session session);
    }

    public static interface Update
    {
        public void execute(Session session);
    }
}