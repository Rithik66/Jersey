package com.rithik.zomazon.controller;

import com.rithik.zomazon.model.Admin;
import com.rithik.zomazon.services.AdminService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Path("/admin")
public class AdminController {

    AdminService adminService = AdminService.getInstance();

    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAdmins(){
        System.out.println("in admin 1");
        List<Admin> allAdmins = adminService.getAllAdmins();
        System.out.println("All Admins : "+allAdmins);
        return Response.ok(allAdmins).header("Test","Success").build();
    }

    @GET
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdminById(@PathParam("id") String id){
        System.out.println("in admin 2");
        Admin admin = adminService.getAdminById(id);
        System.out.println("Admin by id ("+id+") : "+admin);
        return Response.ok(admin).build();
    }
//
//    @POST
//    @RolesAllowed("ADMIN")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createAdmin(Admin admin){
//        return adminService.createAdmin(admin);
//    }
//
//    @POST
//    @RolesAllowed("ADMIN")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response updateAdmin(Admin admin){
//        return adminService.updateAdmin(admin);
//    }
//
//    @POST
//    @RolesAllowed("ADMIN")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response deleteAdmin(Admin admin){
//        return adminService.deleteAdmin(admin);
//    }
}