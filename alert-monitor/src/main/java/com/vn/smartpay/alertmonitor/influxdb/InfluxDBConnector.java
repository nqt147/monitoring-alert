package com.vn.smartpay.alertmonitor.influxdb;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfluxDBConnector {
    private String userName;
    private String password;
    private String url;
    public String database;
    private String retentionPolicy;
    // InfluxDB examples
    private InfluxDB influxDB;

    Logger logger = LoggerFactory.getLogger(InfluxDBConnector.class);

    // data retention policy
    public static String policyNamePix = "logmonitor";

    public InfluxDBConnector(String userName, String password, String url, String database,
                             String retentionPolicy) {
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.database = database;
        this.retentionPolicy = retentionPolicy == null || "".equals(retentionPolicy) ? "autogen" : retentionPolicy;
        this.influxDB = influxDbBuild();
    }


    /**
     * Connect to the database, if there is created
     *
     * @Return influxDb examples
     */
    private InfluxDB influxDbBuild() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(url, userName, password);
        }
        try {
            createDB(database);
            influxDB.setDatabase(database);
        } catch (Exception e) {
            logger.error("create influx db failed, error: {}", e.getMessage());
        } finally {
            influxDB.setRetentionPolicy(retentionPolicy);
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }

    /****
     * Create a database
     * @param database
     */
    private void createDB(String database) {
        influxDB.query(new Query("CREATE DATABASE " + database));
    }
}
