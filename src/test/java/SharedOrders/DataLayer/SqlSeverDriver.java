package SharedOrders.DataLayer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class SqlSeverDriver {
    public static Connection connectToDB()
    {
        Connection conn = null;
        String host="comm-tech-1.cebqlfzeensc.eu-west-1.rds.amazonaws.com";
        String dbName="gcotest";
        String dbUrl = "jdbc:sqlserver://"+host+":1433;"+"databaseName="+dbName;
        try
        {
           //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            DriverManager.registerDriver(new SQLServerDriver());

           conn= DriverManager.getConnection(dbUrl,"gcotesters","7w)v6~v'(M(Ltu2+");
           if(conn !=null)
           {
               DatabaseMetaData dbmd = (DatabaseMetaData) conn.getMetaData();
               System.out.println("Driver name: " + dbmd.getDriverName());
               System.out.println("Driver version: " + dbmd.getDriverVersion());
               System.out.println("Product name: " + dbmd.getDatabaseProductName());
               System.out.println("Prodcut version: " + dbmd.getDatabaseProductVersion());

           }
        }  catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
