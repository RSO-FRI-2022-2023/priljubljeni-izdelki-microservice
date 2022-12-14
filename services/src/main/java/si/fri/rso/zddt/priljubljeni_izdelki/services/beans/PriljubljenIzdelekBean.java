package si.fri.rso.zddt.priljubljeni_izdelki.services.beans;

import lombok.extern.slf4j.Slf4j;
import si.fri.rso.zddt.common.models.PriljubljenIzdelek;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

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
        return resultSet;
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
