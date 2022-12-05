package nu.misano.microprofile.configclient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationScoped
@ApplicationPath("/")
public class ConfigClient extends Application {
}
