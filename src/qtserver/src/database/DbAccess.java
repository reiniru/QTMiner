package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Si occupa della gestione della comunicazione tra il server ed il database
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class DbAccess {
    private final String DBMS = "jdbc:mysql";
    private final String SERVER = "localhost";
    private final String DATABASE = "MapDB";
    private final int PORT = 3306;
    private final String USER_ID = "MapUser";
    private final String PASSWORD = "map";
    private String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver";
    private Connection conn;

    public void initConnection() throws DatabaseConnectionException {
        try {
            //Class.forName(DRIVER_CLASS_NAME);
            String url = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE;
            conn = DriverManager.getConnection(url, USER_ID, PASSWORD);

        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/ catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseConnectionException(e.getMessage());
        }

    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
