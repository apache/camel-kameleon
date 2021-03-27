package one.entropy;

import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/generator")
public class GeneratorResource {

    @Inject
    GeneratorService generatorService;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{type}/{version}/{groupId}/{artifactId}/{components}")
    public Response generate(
            @PathParam("type") String type,
            @PathParam("version") String version,
            @PathParam("groupId") String groupId,
            @PathParam("artifactId") String artifactId,
            @PathParam("components") String components
    ) throws Exception {
        String fileName = generatorService.generate(type, version, groupId, artifactId, components);
        File nf = new File(fileName);
        Response.ResponseBuilder response = Response.ok((Object) nf)
                .header("Content-Disposition", "attachment;filename=" + nf.getName());
        return response.build();
    }
}


