package dev.kameleon;

import io.quarkus.cache.CacheResult;
import io.vertx.core.json.JsonArray;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/component")
public class ComponentResource {

    @Inject
    ComponentService componentService;

    @GET
    @Path("/{type}/{version}")
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "components")
    public JsonArray components(@PathParam("type") String type, @PathParam("version") String version) throws Exception {
        return componentService.components(type, version);
    }

}