package es.unican.ps;

import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import es.unican.ps.uc_hoteles.dominio.Reserva;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import es.unican.ps.uc_hoteles.buissnes.IGestionReservasGerente;
import es.unican.ps.uc_hoteles.dao.IReservasDAO;

import java.util.List;
import java.util.ArrayList;

@Stateless
public class GestionReservasGerente implements IGestionReservasGerente {

    @EJB
    private IReservasDAO reservasDao;

    @Override
    public List<Reserva> consultarReservas(Hotel hotel) {
        if (hotel == null) {
            return new ArrayList<>();
        }

        // Obtener todas las reservas del sistema y filtrar por el hotel indicado
        List<Reserva> todasLasReservas = reservasDao.reservas();
        List<Reserva> reservasHotel = new ArrayList<>();

        for (Reserva r : todasLasReservas) {
            if (r.getHotelReservado() != null && r.getHotelReservado().getNombre().equalsIgnoreCase(hotel.getNombre())) {
                reservasHotel.add(r);
            }
        }

        // Ordenar las reservas por fecha de entrada si se requiere
        reservasHotel.sort((r1, r2) -> r1.getFechaEntrada().compareTo(r2.getFechaEntrada()));

        return reservasHotel;
    }
}