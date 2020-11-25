package ca.com.rlsp.microprofile;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.config.spi.ConfigSource;

import ca.com.rlsp.microprofile.config.Email;

@Path("/mp-config")
@ApplicationScoped
public class MicroProfilesConfigResource {

    @Inject
    private Config config;

    @Inject
    @ConfigProperty(name = "quantidade.elementos.pagina", defaultValue = "44")
    Integer quantidadeElementosPagina;

    @Inject
    @ConfigProperty(name = "quantidade.elementos.pagina")
    Optional<Integer> quantidadeElementosPaginaOp;

    @Inject
    @ConfigProperty(name = "quantidade.elementos.pagina")
    Provider<Integer> quantidadeElementosPaginaProvider;

    // @Inject
    // @ConfigProperty(name = "custom.config.source")
    // String customConfigSource;

    @Inject
    @ConfigProperty(name = "custom.config.source")
    Provider<String> customConfigSourceProvider;


    @Inject
    @ConfigProperty(name = "email.corporativo")
    Email email;

    @GET
    @Path("config-sources")
    @Produces(MediaType.TEXT_PLAIN)
    public String methodname() {
        customConfigSourceProvider.get();
        config = ConfigProvider.getConfig();
        Iterable<ConfigSource> configSources = config.getConfigSources();
        StringBuilder sb = new StringBuilder();
        for (ConfigSource configSource : configSources) {
            sb.append("\nNOME: ").append(configSource.getName())
            .append("\n ORDINAL: ").append(configSource.getOrdinal())
            .append("\n PropertyNames: ").append(configSource.getPropertyNames());
        }
        return sb.toString();
    }
}