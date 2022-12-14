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
@Path("priljubljeni")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, HEAD, OPTIONS")
public class PriljubljenIzdelekResource {

    @Context
    protected UriInfo uriInfo;

    @Inject
    private PriljubljenIzdelekBean priljubljenIzdelekBean;

    @Operation(description = "Vrne seznam priljubljenih izdelkov.", summary = "Seznam priljubljenih izdelkov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam priljubljenih izdelkov.",
                    content = @Content(
                            schema = @Schema(implementation = PriljubljenIzdelek.class))
            ),
            @APIResponse(responseCode = "404", description = "PriljubljenIzdelek not found")
    })
    @GET
    public Response vrniPriljubljeneIzdelke() {
        List<PriljubljenIzdelek> priljubljenIzdelki = priljubljenIzdelekBean.vrniPriljubljeneIzdelke();
        return Response.status(Response.Status.OK).entity(priljubljenIzdelki).build();
    }

    @Operation(description = "Vrne seznam najbolj priljubljenih izdelkov.", summary = "Seznam priljubljenih najboljizdelkov")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Seznam najbolj priljubljenih izdelkov.",
                    content = @Content(
                            schema = @Schema(implementation = PriljubljenIzdelek.class))
            ),
            @APIResponse(responseCode = "404", description = "PriljubljenIzdelek not found")
    })
    @Path("najbolj")
    @GET
    public Response vrniNajboljPriljubljeneIzdelke() {
        List<Object> priljubljenIzdelki = priljubljenIzdelekBean.vrniNajboljPriljubljeneIzdelke();
        return Response.status(Response.Status.OK).entity(priljubljenIzdelki).build();
    }

    @Operation(description = "Vrni podrobnosti priljubljenega izdelka.", summary = "Podrobnosti priljubljenega izdelka")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Podrobnosti priljubljenega izdelka.",
                    content = @Content(
                            schema = @Schema(implementation = PriljubljenIzdelek.class))
            ),
            @APIResponse(responseCode = "404", description = "priljubljenega izdelek ne obstaja")
    })
    @GET
    @Path("{id}")
    public Response vrniIzdelek(@Parameter(
            description = "Identifikator priljubljenega izdelka.",
            required = true)
                                @PathParam("id") int id) {
        PriljubljenIzdelek priljubljeneIzdelek = priljubljenIzdelekBean.vrniPriljubljenIzdelek(id);
        if (priljubljeneIzdelek != null) {
            return Response.status(Response.Status.OK).entity(priljubljeneIzdelek).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }



    @Operation(description = "Vrni PRILJUBLJENE  izdelke uporabnika.", summary = "PRILJUBLJENi  izdelki uporabnika")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "PRILJUBLJENi  izdelki uporabnika",
                    content = @Content(
                            schema = @Schema(implementation = PriljubljenIzdelek.class))
            ),
            @APIResponse(responseCode = "404", description = "PriljubljenIzdelek ne obstaja")
    })
    @GET
    @Path("/uporabnik/{idUporabnika}")
    public Response vrniIzdelkeUporabnika(@Parameter(
            description = "Izdelki uporabnika.",
            required = true) @PathParam("idUporabnika") int uporabnikId) {
        List<PriljubljenIzdelek> priljubljenIzdelek = priljubljenIzdelekBean.vrniPriljubljeneIzdelkeUporabnika(uporabnikId);
        if (priljubljenIzdelek != null) {
            return Response.status(Response.Status.OK).entity(priljubljenIzdelek).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Operation(description = "Dodaj priljubljen izdelek.", summary = "Dodajanje priljubljenega izdelka")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Priljubljen zdelek uspešno dodan."
            ),
            @APIResponse(responseCode = "405", description = "Validacijska napaka."),
    })
    @POST
    public Response dodajIzdelek(@RequestBody(
            description = "DTO objekt s podrobnostmi izdelka.",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = PriljubljenIzdelekDTO.class))) PriljubljenIzdelekDTO priljubljenIzdelekDTO) {
        PriljubljenIzdelek i = new PriljubljenIzdelek();
        i.setUporabnik(priljubljenIzdelekDTO.getUporabnik());
        i.setIzdelek(priljubljenIzdelekDTO.getIzdelek());
        i = priljubljenIzdelekBean.dodajPriljubljenIzdelek((i));
        if (i != null) {
            return Response.status(Response.Status.CREATED).entity(i).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(i).build();
        }
    }

    @Operation(description = "Izbriši priljubljen izdelek.", summary = "Brisanje priljubljenega izdelka")
    @APIResponses({
            @APIResponse(
                    responseCode = "204",
                    description = "Priljubljen izdelek uspešno izbrisan."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Priljubljen izdelek ne obstaja.")
    })
    @DELETE
    @Path("{id}")
    public Response odstraniIzdelek(@Parameter(
            description = "Identifikator priljublnegea izdelka za brisanje.",
            required = true)
                                    @PathParam("id") int id) {
        var success = priljubljenIzdelekBean.odstraniPriljubljenIzdelek(id);
        if (success) {
            return Response.status(Response.Status.OK).entity(true).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
