package one.entropy.kameleon;

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
    @Path("{type}/{archetypeVersion}/{groupId}/{artifactId}/{version}")
    public Response generate(
            @PathParam("type") String type,
            @PathParam("archetypeVersion") String archetypeVersion,
            @PathParam("groupId") String groupId,
            @PathParam("artifactId") String artifactId,
            @PathParam("version") String version
    ) throws Exception {
        return generate(type, archetypeVersion, groupId, artifactId, version, "");
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("{type}/{archetypeVersion}/{groupId}/{artifactId}/{version}/{components}")
    public Response generate(
            @PathParam("type") String type,
            @PathParam("archetypeVersion") String archetypeVersion,
            @PathParam("groupId") String groupId,
            @PathParam("artifactId") String artifactId,
            @PathParam("version") String version,
            @PathParam("components") String components
    ) throws Exception {
        String fileName = generatorService.generate(type, archetypeVersion, groupId, artifactId, version, components);
        File nf = new File(fileName);
        if (nf.exists()){
            return  Response.ok((Object) nf)
                    .header("Content-Disposition", "attachment;filename=" + nf.getName())
                    .header("Filename", nf.getName()).build();
        }
        return Response.serverError().build();
    }
}


