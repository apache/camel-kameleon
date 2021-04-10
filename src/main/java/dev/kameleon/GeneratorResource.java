package dev.kameleon;

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
    @Produces("application/zip")
    @Path("{type}/{archetypeVersion}/{groupId}/{artifactId}/{version}/{javaVersion}")
    public Response generate(
            @PathParam("type") String type,
            @PathParam("archetypeVersion") String archetypeVersion,
            @PathParam("groupId") String groupId,
            @PathParam("artifactId") String artifactId,
            @PathParam("version") String version,
            @PathParam("javaVersion") String javaVersion
    ) throws Exception {
        return generate(type, archetypeVersion, groupId, artifactId, version, javaVersion,"");
    }

    @GET
    @Produces("application/zip")
    @Path("{type}/{archetypeVersion}/{groupId}/{artifactId}/{version}/{javaVersion}/{components}")
    public Response generate(
            @PathParam("type") String type,
            @PathParam("archetypeVersion") String archetypeVersion,
            @PathParam("groupId") String groupId,
            @PathParam("artifactId") String artifactId,
            @PathParam("version") String version,
            @PathParam("javaVersion") String javaVersion,
            @PathParam("components") String components
    ) throws Exception {
        String fileName = generatorService.generate(type, archetypeVersion, groupId, artifactId, version, javaVersion, components);
        File nf = new File(fileName);
        if (nf.exists()){
            return  Response.ok((Object) nf)
                    .type("application/zip")
                    .header("Content-Disposition", "attachment; filename=" + nf.getName())
                    .header("Filename", nf.getName()).build();
        }
        return Response.serverError().build();
    }
}


