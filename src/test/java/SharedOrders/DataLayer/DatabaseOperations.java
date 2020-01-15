package SharedOrders.DataLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {


    public static List<List<String>> fetchResults( Connection conn,String sql) {
        ResultSet rSet = null;
        Statement stmt = null;
        List<List<String>> data = new ArrayList<List<String>>();
        try {
            //String sql = "  SELECT *  FROM BillingLine.SharedOrders.BillingLines";
            stmt = conn.createStatement();
            rSet = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rSet.getMetaData();
            int columnCount=rsmd.getColumnCount();
            while(rSet.next())
            {
                int j=1;
                List<String> data1 = new ArrayList<String>();
                while(j<=columnCount){
                    data1.add(rSet.getString(j++));
                }
                data.add(data1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
