package nu.misano.microprofile.configclient.springconfig.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

/**
 * REST Client Interface which represents the API from the Spring Cloud Config Server
 */
@Path("/")
public interface SpringCloudConfigServerApi {

    @GET
    @Path("{application}/{profile}")
    @Produces("application/json")
    SpringConfigResponse getConfig(@PathParam("application") String application, @PathParam("profile") String profile);
}
