package nu.misano.microprofile.configclient.springconfig.client;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.Map;

// records don't work yet with CDI in Payara 6
// public record PropertySource(String name, Map<String, String> source) {
public class PropertySource {

    private final String name;

    private final Map<String, String> source;

    @JsonbCreator
    public PropertySource(@JsonbProperty("name") String name, @JsonbProperty("source") Map<String, String> source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "PropertySource{" +
                "name='" + name + '\'' +
                ", source=" + source +
                '}';
    }
}