package dev.kameleon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.stream.Collectors;

@Path("/configuration")
public class ConfigurationResource {

    private String configuration;
    private KameleonConfiguration kc;

    void onStart(@Observes StartupEvent ev) {
        readConfiguration();
    }

    @GET
    @Produces("application/json")
    public Response get() throws Exception {
        return Response.ok(kc).build();
    }

    public KameleonConfiguration getKc() {
        readConfiguration();
        return kc;
    }

    private void readConfiguration() {
        if (kc == null) {
            try (InputStream inputStream = getClass().getResourceAsStream("/kameleon.json");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                configuration = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objectMapper = new ObjectMapper();
                kc = objectMapper.readValue(configuration, KameleonConfiguration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


