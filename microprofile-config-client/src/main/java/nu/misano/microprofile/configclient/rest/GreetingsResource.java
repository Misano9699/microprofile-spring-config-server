package nu.misano.microprofile.configclient.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Path("/greetings")
public class GreetingsResource {

    @Inject
    @ConfigProperty(name = "environment", defaultValue = "test")
    protected String environment;

    @GET
    @Path("{user}")
    public String greetings(@PathParam("user") String user) {
        return String.format("Hello %s, you are in environment: %s", user, environment);
    }
}