package nu.misano.microprofile.configclient.springconfig;

import nu.misano.microprofile.configclient.springconfig.client.PropertySource;
import nu.misano.microprofile.configclient.springconfig.client.SpringCloudConfigServerApi;
import nu.misano.microprofile.configclient.springconfig.client.SpringConfigResponse;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptySet;

public class SpringCloudConfigSource implements ConfigSource {

    /* name of this ConfigSource */
    private static final String NAME = "SpringCloudConfigSource";

    /* ordinal value (priority) of this ConfigSource */
    private static final int ORDINAL_VALUE = 500;

    /* prefix of the keys we need to load the config properties */
    private static final String CONFIG_PREFIX = "spring.config.";

    /* properties for loading the properties from the Spring Cloud Config Server */
    private static final String URL = "url";
    private static final String APPLICATION_NAME = "application";
    private static final String PROFILE_NAME = "profile";

    /* map with Spring Cloud Config properties */
    private Map<String, String> configProperties = null;

    /* to prevent an infinite loop as the RestClientBuilder also uses the Config API */
    private boolean isLoading = false;

    @Override
    public Map<String, String> getProperties() {
        if (configProperties == null || !isLoading) {
            configProperties = loadProperties();
        }
        return configProperties;
    }

    @Override
    public Set<String> getPropertyNames() {
        return getProperties() != null ? getProperties().keySet() : emptySet();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getOrdinal() {
        return ORDINAL_VALUE;
    }

    @Override
    public String getValue(String key) {
        if (key.startsWith(CONFIG_PREFIX)) {
            // we are not interested in the properties needed fot the REST Client to call the Spring Cloud Config Server
            return null;
        }
        return getProperties() != null ? getProperties().get(key) : null;
    }

    /**
     * Load the properties from the Spring Cloud Config server
     */
    private Map<String, String> loadProperties() {
        // read the properties needed from the default Config Sources
        Config config = getConfig();
        String url = config.getValue(CONFIG_PREFIX + URL, String.class);
        String application = config.getValue(CONFIG_PREFIX + APPLICATION_NAME, String.class);
        String profile = config.getValue(CONFIG_PREFIX + PROFILE_NAME, String.class);

        if (url != null && application != null && profile != null) {
            try {
                isLoading = true;
                SpringConfigResponse response = getRestClient(url).getConfig(application, profile);
                return response != null ? mapResponse(response) : null;
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } finally {
                isLoading = false;
            }
        }
        return null;
    }

    private Map<String, String> mapResponse(SpringConfigResponse response) {
        return response.getPropertySources().stream()
                .findFirst()
                .map(PropertySource::getSource)
                .orElse(null);
    }

    private SpringCloudConfigServerApi getRestClient(String url) throws MalformedURLException {
        return RestClientBuilder.newBuilder()
                .baseUrl(new URL(url))
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .build(SpringCloudConfigServerApi.class);
    }

    /**
     * get the config or create one with the default config sources.
     */
    private Config getConfig() {
        return ConfigProviderResolver.instance().getBuilder().addDefaultSources().build();
    }
}
