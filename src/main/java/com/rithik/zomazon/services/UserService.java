package com.rithik.zomazon.services;

import com.google.common.hash.Hashing;
import com.rithik.zomazon.model.Product;
import com.rithik.zomazon.model.User;
import jakarta.annotation.security.RolesAllowed;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private static UserService userService;

    private static Connection connection = Connector.getInstance();
    private UserService(){}

    public static UserService getInstance(){
        if (userService==null){
            userService = new UserService();
        }
        return userService;
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        try{
            Connection connection = Connector.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from postgres.public.user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
                userList.add(user);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : getAllUsers");
        }
        return userList;
    }


    public User getUserById(String id) {
        User user = null;
        try {
            Connection connection = Connector.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from postgres.public.user where user_id = ?");
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),resultSet.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error : getUserById");
        }
        return user;
    }
//
    public User createUser(User body){
        User user = null;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("insert into postgres.public.user values(?,?,?,?)");
            preparedStatement.setString(1,body.getUser_id());
            preparedStatement.setString(2,body.getUser_name());
            preparedStatement.setString(3, body.getUser_email());
            String sha256hex = Hashing.sha256()
                    .hashString(body.getUser_password(), StandardCharsets.UTF_8)
                    .toString();
            preparedStatement.setString(4,sha256hex);
            preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error : creatUser");
            return new User();
        }

        return body;
    }

    public User updateUser(String userId,User body){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("update postgres.public.user set user_name = ?,user_email=? where user_id = ?");
            preparedStatement.setString(1, body.getUser_name());
            preparedStatement.setString(2,body.getUser_email());
            preparedStatement.setString(3,userId);
            preparedStatement.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error : updateUser");
            return new User();
        }
        return new User();
    }
}
