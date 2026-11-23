package es.unican.ps.uc_hoteles.buissnes;

import es.unican.ps.uc_hoteles.dominio.Reserva;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import es.unican.ps.uc_hoteles.dominio.Habitacion;
import java.util.Date;
import java.util.List;


public interface IGestionReservasUsuario {
    public Reserva realizaReserva(Reserva r);
    public boolean cancelarReserva(int id);
    public Reserva consultarReserva(int id, String dni);
    public Hotel buscarHotelPorNombre(String nombre);
    public List<Hotel> buscarHotelPorLocalidad(String localidad);
    public List<Habitacion> consultarDisponibilidad(String nombreHotel, Date fechaEntrada, Date fechaSalida);
    public Reserva iniciarReserva(String nombreHotel, Date fechaEntrada, Date fechaSalida);
    public void confirmarReserva(Reserva r);
}