package es.unican.ps.uc_hoteles.buissnes;

import jakarta.ejb.Remote;
import es.unican.ps.uc_hoteles.dominio.Reserva;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import java.util.List;

@Remote
public interface IGestionReservasGerente {
    public List<Reserva> consultarReservas(Hotel hotel);
}