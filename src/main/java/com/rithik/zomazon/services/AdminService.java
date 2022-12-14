package com.rithik.zomazon.services;

import com.google.common.hash.Hashing;
import com.rithik.zomazon.model.Admin;
import com.rithik.zomazon.model.Error;
import jakarta.ws.rs.core.Response;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private static AdminService adminService;

    private AdminService(){}

    public static AdminService getInstance(){
        if (adminService==null){
            adminService = new AdminService();
        }
        return adminService;
    }

   public List<Admin> getAllAdmins(){
        List<Admin> adminList = new ArrayList<>();
        try{
            Connection connection = Connector.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from postgres.public.admin");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Admin admin = new Admin(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                adminList.add(admin);
                System.out.println("Admin : "+admin);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : getAllAdmins");
        }
        return adminList;
    }

    public Admin getAdminById(String id){
        Admin admin = null;
        try{
            Connection connection = Connector.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from postgres.public.admin where admin_id = ?");
            preparedStatement.setString(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                admin = new Admin(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                System.out.println("Admin : "+admin);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : getAdminById");
        }
        return admin;
    }

    public Response createAdmin(Admin admin){
        try{
            Connection connection = Connector.getInstance();

            PreparedStatement preparedStatement1 = connection.prepareStatement("select count(*) from admin");
            ResultSet resultSet = preparedStatement1.executeQuery();
            int count=0;
            while (resultSet.next()){
                count= resultSet.getInt(1);
            }
            count++;
            System.out.println("Admin Table count : "+count);

            admin.setAdmin_id("admin_"+count);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into postgres.public.admin values (?,?,?,?,?)");
            preparedStatement.setString(1, admin.getAdmin_id());
            preparedStatement.setString(2,admin.getAdmin_name());
            preparedStatement.setString(3,admin.getAdmin_email());
            preparedStatement.setString(4, admin.getAdmin_role());
            String pass = Hashing.sha256()
                    .hashString(admin.getAdmin_pass(), StandardCharsets.UTF_8)
                    .toString();

            preparedStatement.setString(5,pass);
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : createAdmin");
            Error error = new Error("error in creating admin",e.getMessage());
            Response.status(Response.Status.CREATED).entity(error).build();
        }
        return Response.status(Response.Status.CREATED).entity(admin).build();
    }

    public Response updateAdmin(Admin admin){
        try{
            Connection connection = Connector.getInstance();

            PreparedStatement preparedStatement1 = connection.prepareStatement("select count(*) from admin");
            ResultSet resultSet = preparedStatement1.executeQuery();
            int count=0;
            while (resultSet.next()){
                count= resultSet.getInt(1);
            }
            count++;
            System.out.println("Admin Table count : "+count);

            admin.setAdmin_id("admin_"+count);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into postgres.public.admin values (?,?,?,?,?)");
            preparedStatement.setString(1, admin.getAdmin_id());
            preparedStatement.setString(2,admin.getAdmin_name());
            preparedStatement.setString(3,admin.getAdmin_email());
            preparedStatement.setString(4, admin.getAdmin_role());
            String pass = Hashing.sha256()
                    .hashString(admin.getAdmin_pass(), StandardCharsets.UTF_8)
                    .toString();

            preparedStatement.setString(5,pass);
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : createAdmin");
            Error error = new Error("error in creating admin",e.getMessage());
            Response.status(Response.Status.CREATED).entity(error).build();
        }
        return Response.status(Response.Status.CREATED).entity(admin).build();
    }

    public Response deleteAdmin(Admin admin){
        try{
            Connection connection = Connector.getInstance();

            PreparedStatement preparedStatement1 = connection.prepareStatement("select count(*) from admin");
            ResultSet resultSet = preparedStatement1.executeQuery();
            int count=0;
            while (resultSet.next()){
                count= resultSet.getInt(1);
            }
            count++;
            System.out.println("Admin Table count : "+count);

            admin.setAdmin_id("admin_"+count);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into postgres.public.admin values (?,?,?,?,?)");
            preparedStatement.setString(1, admin.getAdmin_id());
            preparedStatement.setString(2,admin.getAdmin_name());
            preparedStatement.setString(3,admin.getAdmin_email());
            preparedStatement.setString(4, admin.getAdmin_role());
            String pass = Hashing.sha256()
                    .hashString(admin.getAdmin_pass(), StandardCharsets.UTF_8)
                    .toString();

            preparedStatement.setString(5,pass);
            preparedStatement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error : createAdmin");
            Error error = new Error("error in creating admin",e.getMessage());
            Response.status(Response.Status.CREATED).entity(error).build();
        }
        return Response.status(Response.Status.CREATED).entity(admin).build();
    }

}