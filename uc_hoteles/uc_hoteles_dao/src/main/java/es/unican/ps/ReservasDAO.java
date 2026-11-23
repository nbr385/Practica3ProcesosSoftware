package es.unican.ps;

import java.util.List;

import es.unican.ps.uc_hoteles.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.dominio.Reserva;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ReservasDAO implements IReservasDAO {

    @PersistenceContext(unitName = "UCHoteles")
    private EntityManager em;

    @Override
    public Reserva reserva(int id) {
        return em.find(Reserva.class, id);
    }

    @Override
    public List<Reserva> reservas() {
        return em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
    }

    @Override
    public Reserva creaReserva(Reserva r) {
        if (r == null) {
            return null;
        }
        em.persist(r);
        return r;
    }

    @Override
    public boolean eliminaReserva(Reserva r) {
        if (r == null) {
            return false;
        }

        Reserva gestionada;
        if (em.contains(r)) {
            gestionada = r;
        } else {
            gestionada = em.find(Reserva.class, r.getId());
        }

        if (gestionada == null) {
            return false;
        }

        em.remove(gestionada);
        return true;
    }

    @Override
    public Reserva actualizaReserva(Reserva r) {
        if (r == null) {
            return null;
        }
        return em.merge(r);
    }
}