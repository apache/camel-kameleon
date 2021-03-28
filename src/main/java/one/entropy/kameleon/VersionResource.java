package one.entropy.kameleon;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/version")
public class VersionResource {

    @Inject
    @RestClient
    MavenService mavenService;

    @GET
    @Path("/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String versions(@PathParam("type") String type) {
        Map<String,Map> entity = mavenService.select().readEntity(Map.class);
        Map<String,Object> response = entity.get("response");
        List<Map> docs = (List) response.get("docs");
        return docs.stream().filter(e -> e.get("a").toString().endsWith(type)).findFirst().get().get("latestVersion").toString();
    }
}