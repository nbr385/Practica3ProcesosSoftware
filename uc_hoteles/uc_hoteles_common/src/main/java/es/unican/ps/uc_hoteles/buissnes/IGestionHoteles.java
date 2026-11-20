package es.unican.ps.uc_hoteles.buissnes;

import jakarta.ejb.Remote;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import es.unican.ps.uc_hoteles.dominio.TipoHabitacion;
import es.unican.ps.uc_hoteles.dominio.Habitacion;

@Remote
public interface IGestionHoteles {
    public boolean anhadirHabitacion(Habitacion habitacion, Hotel hotel);
    public Habitacion modificarHabitacion(Habitacion habitacion, Hotel hotel);
    public boolean eliminarHabitacion(TipoHabitacion tipo, Hotel hotel);
    public Hotel consultarHotel(String nombre);
}