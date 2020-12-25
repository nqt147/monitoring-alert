package com.vn.smartpay.alertmonitor.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.SimpleDateFormat;

public class MySQLConnector {

    private static final Logger logger = LoggerFactory.getLogger(MySQLConnector.class);
    private static final SimpleDateFormat dateFormatFile = new SimpleDateFormat("yyy-MM-dd");
    private static Connection conn;
    private static MySQLConnector instance;
    private static String COUNT_ROW = "SELECT count(*) as counter FROM fec_repayment202012 WHERE trans_time >= '%s' and  trans_time <= '%s'";

    private MySQLConnector() {

    }

    private static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection("jdbc:mysql://10.5.18.110:3306/sn_utility_reconciliation?useSSL=false",
                    "operation_tool", "qEBuSdNSYfohYJwQPgSUTet2ofD97pRy");
        }

        return conn;
    }

    public static MySQLConnector getInstance() throws SQLException {
        getConnection();
        if (instance == null) {
            instance = new MySQLConnector();
        }

        return instance;
    }

    public synchronized Integer executeQuery() throws SQLException {

        PreparedStatement pst = null;
        ResultSet rs = null;
        int counter = 0;
        long yesterdayMili = System.currentTimeMillis() - 86400000L;
        String yesterday = dateFormatFile.format(new Date(yesterdayMili));
        String startDay = yesterday + "T00:00:00.000+07:00";
        String endDay = yesterday + "T23:59:59.999+07:00";
        try {
            logger.info("=============== CONNECTED");
            String query = String.format(COUNT_ROW, startDay, endDay);
            logger.info("Query: {}", query);
            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();
            logger.info("=============== EXECUTE");

            rs.next();
            logger.info(String.valueOf(rs.getInt("counter")));
            counter = rs.getInt("counter");

        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                logger.info(ex.getMessage());
            }
        }
        return counter;
    }
}
