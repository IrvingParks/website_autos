package parainfo.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaDb {

    private String database;

    public Connection getConnection() {
        Connection cn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database,
                    "root", "mysql");
        } catch (SQLException e) {
        } catch (Exception e) {
        }

        return cn;
    }

    public ConectaDb() {
        this.database = "autos";
    }

    public ConectaDb(String database) {
        this.database = database;
    }
}

