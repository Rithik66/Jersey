package com.rithik.zomazon.controller;

import com.rithik.zomazon.model.Product;
import com.rithik.zomazon.model.User;
import com.rithik.zomazon.services.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.checkerframework.common.util.report.qual.ReportInherit;

import java.util.List;

@PermitAll
@Path("user")
public class UserController {

    UserService userService = UserService.getInstance();

    @GET
    @RolesAllowed({"ADMIN","SUPER"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        System.out.println("All Users : "+allUsers);
        return allUsers;
    }

    @GET
    @RolesAllowed({"ADMIN","SUPER","USER"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getAdminById(@PathParam("id") String id){
        User user = userService.getUserById(id);
        System.out.println("User by id ("+id+") : "+user);
        return user;
    }

    @POST
    @RolesAllowed({"ADMIN","SUPER","USER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User createUser(User body){
        return userService.createUser(body);
    }

    @PUT
    @RolesAllowed("USER")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("id") String id,User user){
        return userService.updateUser(id,user);
    }
}
