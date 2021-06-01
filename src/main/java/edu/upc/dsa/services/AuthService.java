package edu.upc.dsa.services;

import edu.upc.dsa.FactoryImpl;
import edu.upc.dsa.models.Badges;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/auth", description = "Endpoint to authentication service")
@Path("/auth")
public class AuthService {

    private FactoryImpl session;
    private User user1 = new User("marc", "Marc", "Lopez", "asdf", "", 23, 2);
    private List<Badges> totalBadges = new ArrayList<>();



    public AuthService() {
        this.session = FactoryImpl.getInstance();

        Badges badge1 = new Badges("http://10.0.2.2:8080/insignia-oro-o-escudo-hojas-oro_1017-30512.jpeg","el mas jugon");
        Badges badge2 = new Badges("http://10.0.2.2:8080/Insignias.png","el mas epico");
        Badges badge3 = new Badges("http://10.0.2.2:8080/insignias-%28badges%29-y-microcreditos.jpeg","el mas sostenible");


        totalBadges.add(badge1);
        totalBadges.add(badge2);
        totalBadges.add(badge3);

        List<Badges> badgesMarc = new ArrayList<>();
        badgesMarc.add(badge1);
        badgesMarc.add(badge2);

        user1.setBadgesList(badgesMarc);
        session.save(user1);

    }
    @POST
    @ApiOperation(value = "authenticate user ", notes = "authenticate user given an id and a password ")
    @ApiResponses(value = {
            @ApiResponse( code = 201, message = "Operation Successfull"),
            @ApiResponse( code = 404, message = "user not found ")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response authenticateUser(User user){
       User u = (User)this.session.findById( user.getId(), "usuario");
       if(u != null){
           if(u.getPassword().equals(user.getPassword())){
               return Response.status(201).build();
           }
       }
        return Response.status(404).build();
    }
    @POST
    @ApiOperation(value = "sign up user ", notes = "Sign up user given an id and a password ")
    @ApiResponses(value = {
            @ApiResponse( code = 201, message = "Operation Successfull"),
            @ApiResponse( code = 404, message = "user not found ")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/signup")
    public Response signUpUser(User user){
        User u = (User)this.session.findById( user.getId(), "usuario");
        if(u == null){
            session.save(user);
            return Response.status(201).build();
        }
        return Response.status(404).build();
    }

    @GET
    @ApiOperation(value = "get a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class ),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        User u = (User)this.session.findById( id, "usuario");

        return Response.status(201).entity(u).build();
    }

    @GET
    @ApiOperation(value = "get a User", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful",response = Badges.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Track not found")
    })
    @Path("/badges")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadges() {

        return Response.status(201).entity(totalBadges).build();
    }



    /*
    @DELETE
    @ApiOperation(value = "delete a User", notes = "Delete a user given an id and a password")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Operation Successful"),
            @ApiResponse(code = 404, message = "user not found")
    })
    @Path("/delete")
    public Response deleteUser(User user) {

    }

     */
}