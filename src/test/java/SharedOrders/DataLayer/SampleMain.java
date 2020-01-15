package SharedOrders.DataLayer;

import java.util.List;
import java.sql.Connection;
import java.util.ArrayList;

public class SampleMain {
    public static void main(String args[])
    {
        Connection conn = SqlSeverDriver.connectToDB();
        List<List<String>> data = new ArrayList<List<String>>();
        data = DatabaseOperations.fetchResults(conn,"");
        System.out.println(data);

    }
}
