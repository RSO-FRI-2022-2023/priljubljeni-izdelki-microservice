package si.fri.rso.zddt.priljubljeni_izdelki.services.beans;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.persistence.exceptions.EclipseLinkException;
import si.fri.rso.zddt.common.models.PriljubljenIzdelek;
import si.fri.rso.zddt.priljubljeni_izdelki.services.config.RestProperties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@RequestScoped
public class PriljubljenIzdelekBean {

    @PostConstruct
    private void init() {
        log.info("Incializacija zrna " + PriljubljenIzdelekBean.class.getSimpleName());
    }

    @PersistenceContext(unitName = "priljubljeni-izdelki-jpa")
    private EntityManager em;

    public List<PriljubljenIzdelek> vrniPriljubljeneIzdelke() {
        Query q = em.createNamedQuery("PriljubljenIzdelek.getAll");
        List<PriljubljenIzdelek> resultSet = (List<PriljubljenIzdelek>) q.getResultList();
        log.info("Vračam priljubljene izdelke");
        return resultSet;
    }


    // FAULT TOLERANCE

    @Inject
    private RestProperties restProperties;

    @Timeout(value = 5, unit = ChronoUnit.SECONDS)
    @Fallback(fallbackMethod = "fallback")
    public List<PriljubljenIzdelek> vrniPriljubljeneIzdelke_fault_tolerance_fallback(){

        log.info("SEM V METODI");

        if (restProperties.getSimulation()){
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet request = new HttpGet("http://localhost:8089" + "/api/priljubljeni-izdelki");
            try {
                HttpResponse response = httpClient.execute(request);
            } catch (IOException e) {
                throw new InternalServerErrorException();
            }

        }

        log.info("EXECUTING NORMALLY");
        Query q = em.createNamedQuery("PriljubljenIzdelek.getAll");
        List<PriljubljenIzdelek> resultSet = (List<PriljubljenIzdelek>) q.getResultList();
        return resultSet;
    }
    public List<PriljubljenIzdelek> fallback() {
        log.info("HI FROM FALLBACK");
        return new ArrayList<>();
    }

    @CircuitBreaker(delay=10000, requestVolumeThreshold = 1, failureRatio = 0.5, successThreshold = 1)
    @Fallback(fallbackMethod = "fallback_circuit")
    public List<PriljubljenIzdelek> vrniPriljubljeneIzdelke_fault_tolerance_circuit_and_timeout() {

        log.info("SEM V METODI");

        if (restProperties.getSimulation()){
            throw new InternalServerErrorException();
        }

        log.info("EXECUTING NORMALLY");
        Query q = em.createNamedQuery("PriljubljenIzdelek.getAll");
        List<PriljubljenIzdelek> resultSet = (List<PriljubljenIzdelek>) q.getResultList();
        return resultSet;
    }

    public List<PriljubljenIzdelek> fallback_circuit() {
        log.info("HI FROM FALLBACK CIRCUIT BREAKER");
        return new ArrayList<>();
    }




    public List<Object> vrniNajboljPriljubljeneIzdelke() {
        Query q = em.createNamedQuery("PriljubljenIzdelek.getNajboljPriljubljene");
        List<Object> resultSet = (List<Object>) q.getResultList();
        return resultSet;
    }



    public PriljubljenIzdelek vrniPriljubljenIzdelek(int priljubljenIzdelekId) {
        Query q = em.createNamedQuery("PriljubljenIzdelek.getById");
        q.setParameter("idPriljubljenIzdelek", priljubljenIzdelekId);
        PriljubljenIzdelek priljubljenIzdelek = null;
        try {
            priljubljenIzdelek = (PriljubljenIzdelek) q.getSingleResult();
        } catch (Exception ignored) {
        }
        return priljubljenIzdelek;
    }

    public List<PriljubljenIzdelek> vrniPriljubljeneIzdelkeUporabnika(int uporabnikId) {
        Query q = em.createNamedQuery("PriljubljenIzdelek.getByUser");
        q.setParameter("uporabnikId", uporabnikId);
        List<PriljubljenIzdelek> resultSet = (List<PriljubljenIzdelek>) q.getResultList();
        try {
            resultSet = (List<PriljubljenIzdelek>) q.getResultList();
        } catch (Exception ignored) {
        }
        return resultSet;
    }

    @Transactional
    public boolean odstraniPriljubljenIzdelek(int idIzdelka) {
        PriljubljenIzdelek priljubljenIzdelek = vrniPriljubljenIzdelek(idIzdelka);
        if (priljubljenIzdelek != null) {
            em.remove(priljubljenIzdelek);
            return true;
        }
        return false;
    }

    @Transactional
    public PriljubljenIzdelek dodajPriljubljenIzdelek(PriljubljenIzdelek priljubljenIzdelek) {
        if (priljubljenIzdelek != null) {
            em.persist(priljubljenIzdelek);
        }
        return priljubljenIzdelek;
    }
}
