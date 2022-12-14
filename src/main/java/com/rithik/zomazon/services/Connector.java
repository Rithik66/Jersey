package com.rithik.zomazon.services;

import jakarta.ws.rs.core.Response;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    private static Connection connection;

    private Connector(){}

    public static Connection getInstance(){
        if(connection==null){
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager
                        .getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "postgres");
            }catch (Exception e){
                PrintWriter printWriter = null;
                Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                printWriter.println("Connection failed");
            }
        }
        return connection;
    }
}
