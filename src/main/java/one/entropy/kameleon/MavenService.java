package one.entropy.kameleon;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("")
@RegisterRestClient
public interface MavenService {

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    Response select();
}
