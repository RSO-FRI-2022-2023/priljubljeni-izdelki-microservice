package si.fri.rso.zddt.priljubljeni_izdelki.api.v1.resources;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.zddt.common.models.Izdelek;
import si.fri.rso.zddt.common.models.PriljubljenIzdelek;
import si.fri.rso.zddt.priljubljeni_izdelki.services.DTOs.PriljubljenIzdelekDTO;
import si.fri.rso.zddt.priljubljeni_izdelki.services.beans.PriljubljenIzdelekBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
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
    public Response vrniSteviloPriljubljenihIzdelkov() {
        List<PriljubljenIzdelek> priljubljenIzdelki = priljubljenIzdelekBean.vrniPriljubljeneIzdelke();
        return Response.status(Response.Status.OK).entity(priljubljenIzdelki.size()).build();
    }
}
