import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_URL = "jdbc:derby:ConnectDB;create=true"; //ProjectName=DbName
	
	Connection conn;
	Statement stmt;
	String tableName = "DatePlan";
	
	public DBConnection() {
		try {
			this.conn = DriverManager.getConnection(JDBC_URL);
			if(this.conn != null) {
				System.out.println("Connection Sucessful");
			}
		} catch(SQLException e) {
			System.out.println("Connection Failed");
		}
	}
	public void createTable() {
		try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE "+tableName+"(DATE INT,"
                    + "NUM INT, PLAN VARCHAR(30))");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
	}
	public void deleteTable() {
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE " + tableName);
		}
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
	}
	public int isExistThenGetNum(int dateValue) {
		int num = 0;
		try {
			stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery("select * from "+ tableName + " where date = " + dateValue + " order by DATE+NUM");
			while(results.next()) {
			num = results.getInt(2);
			}
		}
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
		return num+1;	
	}
	public void insertPlans(int date, int num, String plan)
	    {
	        try
	        {
	            stmt = conn.createStatement();
	            stmt.execute("insert into " + tableName + " values (" +
	                    date + "," + num + ",'" + plan + "')");
	            stmt.close();
	        }
	        catch (SQLException sqlExcept)
	        {
	            sqlExcept.printStackTrace();
	        }
	    }
	public void selectPlans()
	    {
	        try
	        {
	            stmt = conn.createStatement();
	            ResultSet results = stmt.executeQuery("select * from " + tableName + " order by DATE+NUM"); 
	            ResultSetMetaData rsmd = results.getMetaData();
	            int numberCols = rsmd.getColumnCount();
	            for (int i=1; i<=numberCols; i++)
	            {
	                //print Column Names
	                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
	            }

	            System.out.println("\n-------------------------------------------------");

	            while(results.next())
	            {
	                int date = results.getInt(1);
	                int num = results.getInt(2);
	                String plan = results.getString(3);
	                System.out.println(date + "\t" + num + "\t\t" + plan);
	            }
	            results.close();
	            stmt.close();
	        }
	        catch (SQLException sqlExcept)
	        {
	            sqlExcept.printStackTrace();
	        }
	    }
	
	    
	public void shutdown()
	    {
	        try
	        {
	        	System.out.println("Bye~!");
	            if (stmt != null)
	            {
	                stmt.close();
	            }
	            if (conn != null)
	            {
	                DriverManager.getConnection(JDBC_URL + ";shutdown=true");
	                conn.close();
	            }           
	        }
	        catch (SQLException sqlExcept)
	        {
	            
	        }

	    }
}
