package nu.misano.microprofile.configclient.springconfig.client;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

// records don't work yet with CDI in Payara 6
// public record SpringConfigResponse(String name, List<String> profiles, List<PropertySource> propertySources) {
public class SpringConfigResponse {

    private final String name;

    private final List<String> profiles;

    private final List<PropertySource> propertySources;

    @JsonbCreator
    public SpringConfigResponse(@JsonbProperty("name") String name, @JsonbProperty("profiles") List<String> profiles, @JsonbProperty("propertySources") List<PropertySource> propertySources) {
        this.name = name;
        this.profiles = profiles;
        this.propertySources = propertySources;
    }

    public String getName() {
        return name;
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public List<PropertySource> getPropertySources() {
        return propertySources;
    }

    @Override
    public String toString() {
        return "SpringConfigResponse{" +
                "name='" + name + '\'' +
                ", profiles=" + profiles +
                ", propertySources=" + propertySources +
                '}';
    }
}
