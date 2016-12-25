//9.12.16 15:48 v0.2
package com.io.src;

import java.sql.*;
/**
 *
 * @author Richard Strauss
 */
public class DataBaseControl {
    private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    private final String DB_URL = "jdbc:mysql://localhost/tagger";
    private final String USER = "root";
    private final String PASS = "";
      
    public String getDatabaseData(String querryString){
        Connection con = null;
        Statement stmt = null;
        String sql;
        String DBData = "No Tag";
        try{
            if(querryString != null && querryString.length() > 0){
                Class.forName(JDBC_DRIVER);
                //System.out.println("Connecting to database...");
                con = DriverManager.getConnection(DB_URL, USER, PASS);

                sql = "SELECT * FROM noun WHERE noun_name LIKE '%" + querryString + "%'";
                stmt = con.createStatement();

                try (ResultSet rs = stmt.executeQuery(sql)) {
                    if (rs != null) {
                        while (rs.next()) {
                            //int id_noun = rs.getInt("id_noun");
                            String noun_name = rs.getString("noun_name");
                            String pos = rs.getString("pos");
                            String type = rs.getString("type");
                            String gender = rs.getString("gender");
                            String number = rs.getString("number");
                            String caz = rs.getString("caz");
                            String articol = rs.getString("articol");
                            String lemma = rs.getString("lemma");
                            String actual = rs.getString("actual");

                            DBData = noun_name + " - " + pos + ", " + type + ", " + gender + ", " + number 
                                    + " " + caz + ", " + articol + ", " + lemma; //+ ", " + actual +";";
                            
                            
                            //System.out.println("ID: " + id_noun + "\n,Noun Name: " + noun_name + "\nPOS: " + pos + "\nType: " + type
                            //        + "\nGender: " + gender + "\nNumber: " + number + "\nCaz: " + caz + "\nArticol: " + articol
                            //        + "\nLemma: " + lemma + "\nActual: " + actual + "\n");

                        }
                    } else {
                        System.out.println("Result is null");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace(System.err);
                }
            }
            return DBData;
            
        }catch(SQLException | ClassNotFoundException se){
        }finally{
            try{
                if(stmt!=null)
                stmt.close();
            }catch(SQLException se2){}
            try{
                if(con!=null){
                    con.close();
                }
            }catch(SQLException se){}
        }        
        return DBData;
    }
}
