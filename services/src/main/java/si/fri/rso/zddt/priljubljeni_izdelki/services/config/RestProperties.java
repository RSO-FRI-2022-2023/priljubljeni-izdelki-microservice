package si.fri.rso.zddt.priljubljeni_izdelki.services.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ConfigBundle("rest-properties")
@ApplicationScoped
public class RestProperties {

    private Boolean simulation;

    public Boolean getSimulation() {
        return simulation;
    }

    public void setSimulation(Boolean simulation) {
        this.simulation = simulation;
    }
}