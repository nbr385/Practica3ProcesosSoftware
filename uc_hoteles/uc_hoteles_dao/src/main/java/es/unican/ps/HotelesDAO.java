package es.unican.ps;

import java.util.List;

import es.unican.ps.uc_hoteles.dao.IHotelesDAO;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class HotelesDAO implements IHotelesDAO {

    @PersistenceContext(unitName = "UCHoteles")
    private EntityManager em;

    @Override
    public Hotel hotel(String nombre) {
        if (nombre == null) {
            return null;
        }
        return em.find(Hotel.class, nombre);
    }

    @Override
    public List<Hotel> hoteles() {
        return em.createQuery("SELECT h FROM Hotel h", Hotel.class).getResultList();
    }

    @Override
    public Hotel crearHotel(Hotel h) {
        if (h == null) {
            return null;
        }
        em.persist(h);
        return h;
    }

    @Override
    public boolean eliminaHotel(Hotel h) {
        if (h == null || h.getNombre() == null) {
            return false;
        }

        Hotel gestionado;
        if (em.contains(h)) {
            gestionado = h;
        } else {
            gestionado = em.find(Hotel.class, h.getNombre());
        }

        if (gestionado == null) {
            return false;
        }

        em.remove(gestionado);
        return true;
    }

    @Override
    public Hotel actualizaHotel(Hotel h) {
        if (h == null) {
            return null;
        }
        return em.merge(h);
    }
}