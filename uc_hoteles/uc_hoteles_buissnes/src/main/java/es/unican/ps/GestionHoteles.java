package es.unican.ps;

import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import es.unican.ps.uc_hoteles.dominio.TipoHabitacion;
import es.unican.ps.uc_hoteles.dominio.Habitacion;
import es.unican.ps.uc_hoteles.buissnes.IGestionHoteles;
import es.unican.ps.uc_hoteles.dao.IHotelesDAO;

@Stateless
public class GestionHoteles implements IGestionHoteles {

    @EJB
    private IHotelesDAO hotelesDao;

    @Override
    public boolean anhadirHabitacion(Habitacion habitacion, Hotel hotel) {
        Hotel h = consultarHotel(hotel.getNombre());
        if (h != null) {
            // Añadir la habitación a la colección del hotel y actualizar
            h.getHabitaciones().add(habitacion);
            hotelesDao.actualizaHotel(h);
            return true;
        }
        return false;
    }

    @Override
    public Habitacion modificarHabitacion(Habitacion habitacion, Hotel hotel) {
        // Lógica para actualizar los datos de una habitación existente
        Hotel h = consultarHotel(hotel.getNombre());
        if (h != null) {
            for (Habitacion hab : h.getHabitaciones()) {
                if (hab.getTipo() == habitacion.getTipo()) {
                    hab.setPrecioPorNoche(habitacion.getPrecioPorNoche());
                    hab.setDisponibles(habitacion.getDisponibles());
                    hotelesDao.actualizaHotel(h);
                    return hab;
                }
            }
        }
        return null;
    }

    @Override
    public boolean eliminarHabitacion(TipoHabitacion tipo, Hotel hotel) {
        Hotel h = hotelesDao.hotel(hotel.getNombre());
        if (h != null) {
            boolean borrado = h.getHabitaciones().removeIf(hab -> hab.getTipo() == tipo);
            if (borrado) {
                hotelesDao.actualizaHotel(h);
                return true;
            }
        }
        return false;
    }

    @Override
    public Hotel consultarHotel(String nombre) {
        return hotelesDao.hotel(nombre);
    }
}