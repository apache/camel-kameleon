package dev.kameleon.generator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/generator")
public class GeneratorResource {

    @Inject
    GeneratorService generatorService;

    @GET
    @Produces("application/zip")
    public Response get (@QueryParam("camelType") String camelType,
                @QueryParam("camelVersion") String camelVersion,
                @QueryParam("javaVersion") String javaVersion,
                @QueryParam("groupId") String groupId,
                @QueryParam("artifactId") String artifactId,
                @QueryParam("version") String version,
                @QueryParam("components") String components) throws Exception {
        String fileName = generatorService.generate(camelType,camelVersion, groupId, artifactId, version, javaVersion, components);
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


