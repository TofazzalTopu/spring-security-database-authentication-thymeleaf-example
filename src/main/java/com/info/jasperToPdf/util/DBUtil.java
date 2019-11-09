package com.info.jasperToPdf.util;

import com.info.jasperToPdf.pdfService.DBConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {

    @Autowired
    private static DBConfigService dbConfigService;

    public static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
//    public static String DB_HOST = "172.16.200.201"; //Database server IP

    public static String getHostIp() {
        InetAddress ip;
        String hostIp = null;
        try {
            ip = InetAddress.getLocalHost();
            hostIp = ip.getHostAddress();
            return hostIp;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostIp;
    }

    public static Connection dbConnection() {

        Connection conn = null;
        String url = dbConfigService.getUrl();
        String user =dbConfigService.getUserName();
        String pass = dbConfigService.getPassword();

        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
            conn = DriverManager.getConnection(url, user, pass);

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                return null;
            }
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            logger.info(e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
