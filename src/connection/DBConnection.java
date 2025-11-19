package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private Connection connect;
    private String dbName;
    private String activedbName;

    private DBConnection(){
        dbName = "";
        activedbName = "";
    }

    public static synchronized DBConnection getInstance(){
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getConnection() {
        System.out.println(!instance.activedbName.equals(instance.dbName));
        if (!instance.activedbName.equals(instance.dbName)) instance.createConnection();
        return connect;
    }

    private void createConnection() {
        // variables de connection
        String userName = "root";
        String password;
        String serverName;
        String portNumber;
        String tableName;
        if (System.getProperty("os.name").contains("Windows")) {
            password = "";
            serverName = "127.0.0.1";
            //String portNumber = "3306";
            portNumber = "3306"; // Port par défaut sur XAMPP
            tableName = "personne";

            // il faut une base nommee testPersonne !
//            dbName = "testpersonne";
        } else {
            password = "root";
            serverName = "127.0.0.1";
            //String portNumber = "3306";
            portNumber = "8889"; // Port par défaut sur MAMP
            tableName = "personne";

            // il faut une base nommee testPersonne !
//            dbName = "testpersonne";
        }
        try {
            activedbName = dbName;
            // chargement du driver jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");

            // creation de la connection
            Properties connectionProps = new Properties();
            connectionProps.put("user", userName);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + dbName;
            System.out.println(urlDB);
            connect = DriverManager.getConnection(urlDB, connectionProps);
        } catch (SQLException e) {
            System.out.println("*** ERREUR SQL ***");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("*** ERREUR lors du chargement du driver ***");
            e.printStackTrace();
        }
    }

    public void setNomDB(String nomDB) {
        dbName = nomDB;
    }
}
