package si.fri.rso.zddt.priljubljeni_izdelki.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Priljubljeni izdelki API", version = "v1",
        contact = @Contact(email = "zd7631@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing popular products."),
        servers = @Server(url = "http://localhost:8082/"))

@ApplicationPath("/v1")
public class PriljubljeniIzdelkiAplication extends Application{

}
