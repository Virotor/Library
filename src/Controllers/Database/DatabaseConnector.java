package Controllers.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {






    public Connection connectToDatabase(){
        try{
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:53212;database=Library;integratedSecurity=true;";
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            return DriverManager.getConnection(url);
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        return null;
    }




}
