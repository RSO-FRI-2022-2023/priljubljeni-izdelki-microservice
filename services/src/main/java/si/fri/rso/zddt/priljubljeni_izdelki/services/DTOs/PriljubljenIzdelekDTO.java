package si.fri.rso.zddt.priljubljeni_izdelki.services.DTOs;

import si.fri.rso.zddt.common.models.Izdelek;
import si.fri.rso.zddt.common.models.Uporabnik;

public class PriljubljenIzdelekDTO {
    private Integer id;
    private Uporabnik uporabnik;
    private Izdelek izdelek;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Izdelek getIzdelek() {
        return izdelek;
    }

    public void setIzdelek(Izdelek izdelek) {
        this.izdelek = izdelek;
    }
}
