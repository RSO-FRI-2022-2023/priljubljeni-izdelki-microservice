package si.fri.rso.zddt.priljubljeni_izdelki.api.v1.resources;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.zddt.common.models.PriljubljenIzdelek;
import si.fri.rso.zddt.priljubljeni_izdelki.services.beans.PriljubljenIzdelekBean;
import si.fri.rso.zddt.priljubljeni_izdelki.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Log
@Path("fault-tolerance")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, HEAD, OPTIONS")
public class FaultToleranceDemoResource {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PriljubljenIzdelekBean priljubljenIzdelekBean;

    @GET
    @Path("fallback")
    public Response fault_tolerance_fallback_demo() {
        List<PriljubljenIzdelek> priljubljenIzdelki = priljubljenIzdelekBean.vrniPriljubljeneIzdelke_fault_tolerance_fallback();
        return Response.status(Response.Status.OK).entity(priljubljenIzdelki.size()).build();
    }
    @GET
    @Path("circuit-breaker")
    public Response fault_tolerance_circuit_breaker_timeout_demo() {
        List<PriljubljenIzdelek> priljubljenIzdelki = priljubljenIzdelekBean.vrniPriljubljeneIzdelke_fault_tolerance_circuit_and_timeout();
        return Response.status(Response.Status.OK).entity(priljubljenIzdelki.size()).build();
    }

    @Inject
    private RestProperties restProperties;

    @POST
    @Path("simulate")
    public Response test() {

        restProperties.setSimulation(true);

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("normal")
    public Response normal() {

        restProperties.setSimulation(false);

        return Response.status(Response.Status.OK).build();
    }
}
