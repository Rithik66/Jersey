package com.rithik.zomazon.filter;

import com.google.common.hash.Hashing;
import com.rithik.zomazon.model.User;
import com.rithik.zomazon.services.Connector;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.json.JSONArray;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

@Provider
public class SecurityFilter implements ContainerRequestFilter {
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();
    private static final Response SERVER_ERROR = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    private static String role;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if(!method.isAnnotationPresent(PermitAll.class)) {
            if(method.isAnnotationPresent(DenyAll.class)) {
                System.out.println("DenyAll");
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();
            final List<String> authorization = headers.get("Authorization");
            if(authorization == null || authorization.isEmpty()){
                System.out.println("authorization");
                requestContext.abortWith(ACCESS_DENIED);
                return;
            }
            final String encodedUserPassword = authorization.get(0).replaceFirst( "Basic ", "");
            StringTokenizer tokenizer = null;
            try{
                tokenizer = new StringTokenizer(new String(Base64.getDecoder().decode(encodedUserPassword)),":");
            }catch(Exception e){
                requestContext.abortWith(SERVER_ERROR);
                return;
            }
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            System.out.println("Cred before : "+username+" "+password);
            String passHash = Hashing.sha256()
                    .hashString(password, StandardCharsets.UTF_8)
                    .toString();

            System.out.println("Cred after : "+username+" "+passHash);
            if(!isValidEntry(username,passHash)){
                System.out.println("NotValid");
                requestContext.abortWith(ACCESS_DENIED);
            }else{
                if(method.isAnnotationPresent(RolesAllowed.class)){
                    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                    Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                    if(!isUserAllowed(rolesSet)){
                        System.out.println("NotAllowed");
                        requestContext.abortWith(ACCESS_DENIED);
                    }
                    System.out.println("Allowed");
                }
            }

        }
    }
    private boolean isUserAllowed(Set<String> rolesSet){
        boolean isAllowed = false;
        System.out.println("Roles : "+rolesSet);
        if(rolesSet.contains(role)){
            isAllowed = true;
        }
        return isAllowed;
    }
    private boolean isValidEntry(String username,String password){
        try {
            role=null;
            Connection connection = Connector.getInstance();
            PreparedStatement preparedStatement = connection.prepareStatement("select admin_role from postgres.public.admin where admin_email = ? and admin_pass = ?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            System.out.println(username+" "+password);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("check 1");
            while (resultSet.next()) {
                role=resultSet.getString(1);
                System.out.println(role);
            }
            if(role==null){
                PreparedStatement preparedStatement1 = connection.prepareStatement("select user_id from postgres.public.user where user_email = ? and password = ?");
                preparedStatement1.setString(1,username);
                preparedStatement1.setString(2,password);
                System.out.println(username+" "+password);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                System.out.println("check 2");
                while (resultSet1.next()) {
                    role="USER";
                    System.out.println(role);
                }
            }
            if (role!=null){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error : getUserById");
        }
        return false;
    }
}