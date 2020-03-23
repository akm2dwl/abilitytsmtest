package com.ability.dao;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

public class Db {

    public static DataSource getSQLDataSource() {
        SQLServerDataSource ds = new SQLServerDataSource();
//        ds.setURL("jdbc:sqlserver://192.168.1.54;DatabaseName=TaskMgm");
        ds.setURL("jdbc:sqlserver://.;DatabaseName=TaskMgm");
//        ds.setURL("jdbc:sqlserver://192.168.1.54;DatabaseName=TaskMgmLive");
        ds.setUser("sa");
        ds.setPassword("SQL");
//        ds.setUser("nsh");
//        ds.setPassword("nshnsh");
//                ds.setColumnEncryptionSetting("Enabled");
//                ds.getColumnEncryptionSetting();

        return ds;
    }

    public static Connection getConnection() throws SQLException {
//        String url="jdbc:sqlserver://192.168.1.54;DatabaseName=TaskMgm";
//        String url = "jdbc:sqlserver://192.168.1.54;DatabaseName=TaskMgmLive";
//        String name = "TSL";
//        String password = "tzl";
        String url="jdbc:sqlserver://192.168.1.54;DatabaseName=TaskMgm";
        String name = "sa";
        String password = "SQL";
        try {
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, name, password);

            return con;
        } catch (ClassNotFoundException ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            System.out.println("Your DB is not Connected");
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (SQLException ex) {
        }
    }
}
