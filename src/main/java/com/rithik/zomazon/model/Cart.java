package com.rithik.zomazon.model;

import com.google.common.hash.Hashing;
import com.rithik.zomazon.services.Connector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    static int product_id;
    static JSONArray jsonArray;

    public static void main(String[] args) {
        try{
            Connection connection = Connector.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from postgres.public.user");
            ResultSet resultSet = preparedStatement.executeQuery();
            JSONObject jsonObject = new JSONObject();
            while (resultSet.next()){
                jsonObject.put("user_id",resultSet.getString(1));
                jsonObject.put("user_name",resultSet.getString(2));
                jsonObject.put("user_email",resultSet.getString(3));
                jsonObject.put("password",resultSet.getString(5));
                PGobject object = (PGobject) resultSet.getObject(4);
                JSONObject jsonObject2 = new JSONObject(object.toString());
                System.out.println("Final "+jsonObject2.getJSONArray("cart"));
            }
            System.out.println(jsonObject);
            PGobject jsonObject1 = new PGobject();
            jsonObject1.setType("json");
            jsonObject1.setValue(jsonObject.toString());

            System.out.println(jsonObject1);
            System.out.println(jsonObject1.getValue());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String sha256hex = Hashing.sha256()
                .hashString("rohith@g.c", StandardCharsets.UTF_8)
                .toString();
//        System.out.println(sha256hex);
//        System.out.println(product_id+" : "+jsonArray);
    }
}
