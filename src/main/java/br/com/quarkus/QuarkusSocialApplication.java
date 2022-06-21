package br.com.quarkus;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
    tags = {
        @Tag(name = "User Resource", description = "User Resource."),
        @Tag(name = "Follower Resource", description = "Follower Resource."),
        @Tag(name = "Post Resource", description = "Post Resource.")
    },
    info = @Info(
        title = "Quarkus API",
        version = "1.0.0",
        contact = @Contact(
            name = "Universo Tecnologia",
            url = "https://github.com/andersonsrsilva")),
    servers = {
        @Server(url = "http://localhost:8080")
    }
)
public class QuarkusSocialApplication extends Application {

}
