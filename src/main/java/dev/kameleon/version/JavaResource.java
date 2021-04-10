package dev.kameleon.version;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/java")
public class JavaResource {

    @ConfigProperty(name = "kameleon.java.version.3")
    List<String> camel3;

    @ConfigProperty(name = "kameleon.java.version.2")
    List<String> camel2;

    @GET
    @Path("/{camelVersion}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getVersions(@PathParam("camelVersion") String camelVersion) {
        return camelVersion.startsWith("2") ? camel2 :camel3;
    }
}