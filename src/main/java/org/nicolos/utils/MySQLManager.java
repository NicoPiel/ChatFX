package org.nicolos.utils;



import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {
      protected static java.sql.Connection connection;
      private static String server = "jdbc:mysql://172.30.96.172/logindata";
      private static String username = "logindata";
      private static String password = "Test1234";

      public java.sql.Connection getConnect() {
            return connection;
      }

      public MySQLManager() {
            startSQL();
      }

      public static void startSQL() {
            try { //We use a try catch to avoid errors, hopefully we don't get any.
                  Class.forName("com.mysql.jdbc.Driver"); //this accesses Driver in jdbc.
                  System.out.println("jdbc driver successfully accessed.");
            } catch (ClassNotFoundException e) {
                  e.printStackTrace();
                  System.err.println("jdbc driver unavailable!");
                  return;
            }
            try { //Another try catch to get any SQL errors (for example connections errors)
                  connection = DriverManager.getConnection(server, username, password);
                  //with the method getConnection() from DriverManager, we're trying to set
                  //the connection's url, username, password to the variables we made earlier and
                  //trying to get a connection at the same time. JDBC allows us to do this.
                  System.out.println("Opened MySQL connection.");
            } catch (SQLException e) { //catching errors)
                  e.printStackTrace(); //prints out SQLException errors to the console (if any)
                  System.err.println("Could not open MySQL connection.");
            }
      }

      public static void stopSQL() {
            // invoke on disable.
            try { //using a try catch to catch connection errors (like wrong sql password...)
                  if (connection!=null && !connection.isClosed()){ //checking if connection isn't null to
                        //avoid receiving a nullpointer
                        connection.close(); //closing the connection field variable.
                  }
            } catch(Exception e) {
                  e.printStackTrace();
            }
      }

      public ResultSet executeWithResult(String sql) {
            ResultSet results = null;
            try {
                  PreparedStatement stmt = this.getConnect().prepareStatement(sql);
                  results = stmt.executeQuery();
            }catch(SQLException e) {
                  e.printStackTrace();
            }
            return results;
      }

      public boolean executeUpdate(String sql) {
            try {
                  PreparedStatement stmt = this.getConnect().prepareStatement(sql);
                  stmt.executeUpdate();
                  return true;
            } catch(SQLException e) {
                  e.printStackTrace();
                  return false;
            }
      }

      public String getPassword_Hash(String username) {
            String sql = "Select password_hash FROM userlogin WHERE username= '"+username+"';";

            try {
                  PreparedStatement stmt = this.getConnect().prepareStatement(sql);
                  ResultSet result = stmt.executeQuery();
                  if (result.next()) {
                        return result.getString("password_hash");
                  }
            }catch(SQLException e) {
                  e.printStackTrace();
            }
            return null;

      }
      public String getSalt(String username) {
            String sql = "Select salt1 FROM userlogin WHERE username= '"+username+"';";

            try {
                  PreparedStatement stmt = this.getConnect().prepareStatement(sql);
                  ResultSet result = stmt.executeQuery();
                  if (result.next()) {
                        return result.getString("salt1");
                  }
            }catch(SQLException e) {
                  e.printStackTrace();
            }
            return null;

      }


      public boolean isUsernameAvailable(String username) {
            String sql = "Select * FROM userlogin WHERE username= '"+username+"';";
            try {
                  if(this.executeWithResult(sql).next()) {
                        return false;
                  }
            }catch(SQLException e) {
                  e.printStackTrace();
            }
            return true;
      }
      public boolean isEmailAvailable(String email) {
            String sql = "Select * FROM userlogin WHERE email= '"+email+"';";
            try {
                  if(this.executeWithResult(sql).next()) {
                        return false;
                  }
            }catch(SQLException e) {
                  e.printStackTrace();
            }
            return true;

      }
      
}

