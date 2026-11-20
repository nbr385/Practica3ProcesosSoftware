package es.unican.ps;

import jakarta.ejb.Stateless;
import jakarta.ejb.EJB;
import es.unican.ps.uc_hoteles.buissnes.IGestionReservasUsuario;
import es.unican.ps.uc_hoteles.dominio.*;
import es.unican.ps.uc_hoteles.dao.*;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Stateless
public class GestionReservasUsuario implements IGestionReservasUsuario {

    @EJB
    private IHotelesDAO hotelesDao;

    @EJB
    private IReservasDAO reservasDao;

    public GestionReservasUsuario(IHotelesDAO hoteles, IReservasDAO reservas) {
        this.hotelesDao = hoteles;
        this.reservasDao = reservas;
    }

    //Constructor vacio para la inyecion de dependencias
    public GestionReservasUsuario() {}

    @Override
    public Reserva realizaReserva(Reserva r) {
        // Lógica para registrar y persistir la reserva completa
        return reservasDao.creaReserva(r);
    }

    @Override
    public boolean cancelarReserva(int id) {
        Reserva r = reservasDao.reserva(id);
        if (r != null) {
            return reservasDao.eliminaReserva(r);
        }
        return false;
    }

    @Override
    public Reserva consultarReserva(int id, String dni) {
        Reserva r = reservasDao.reserva(id);
        // Verificar que la reserva existe y que el DNI del cliente coincide
        if (r != null && r.getCliente() != null && dni.equals(r.getCliente().getDni())) {
            return r;
        }
        return null;
    }

    @Override
    public Hotel buscarHotelPorNombre(String nombre) {
        return hotelesDao.hotel(nombre);
    }

    @Override
    public List<Hotel> buscarHotelPorLocalidad(String localidad) {
        List<Hotel> hoteles = hotelesDao.hoteles();
        List<Hotel> res = new ArrayList<>();
        for (Hotel h : hoteles) {
            if (localidad.equals(h.getLocalidad())) {
                res.add(h);
            }
        }
        return res;
    }

    @Override
    public List<Habitacion> consultarDisponibilidad(String nombreHotel, Date fechaEntrada, Date fechaSalida) {
        if (fechaEntrada == null || fechaSalida == null || !fechaSalida.after(fechaEntrada)) {
            return null;
        }
        Hotel h = hotelesDao.hotel(nombreHotel);
        if (h == null) {
            return new ArrayList<>(); //Si el hotel no existe se devuelve una lista vacia
        }
        
        List<Habitacion> habitacionesDisponibles = new ArrayList<>();
        List<Reserva> todasLasReservas = reservasDao.reservas();

        // Recorrer cada tipo de habitación del hotel
        for (Habitacion habitacion : h.getHabitaciones()) {
            int stockTotal = habitacion.getDisponibles(); // Supongo que 'disponibles' almacena el total de unidades de este tipo
            int unidadesReservadas = 0;

            // Comprobar las reservas existentes para ver cuáles solapan en fechas
            for (Reserva r : todasLasReservas) {
                // Validar que la reserva pertenece a este hotel
                if (r.getHotelReservado() != null && r.getHotelReservado().getNombre().equalsIgnoreCase(nombreHotel)) {           
                    // Condición de solapamiento de fechas: 
                    // (InicioReserva < FinSolicitado) Y (FinReserva > InicioSolicitado)
                    if (r.getFechaEntrada().before(fechaSalida) && r.getFechaSalida().after(fechaEntrada)) {
                        // Buscar en la clase de asociación cuantas unidades de esta habitación se reservaron
                        if (r.getReservasTipoHabitacion() != null) {
                            for (ReservaTipoHabitacion rth : r.getReservasTipoHabitacion()) {
                                // Comparamos si la línea de reserva corresponde a esta habitación
                                if (rth.getHabitacion() != null && rth.getHabitacion().equals(habitacion)) {
                                    unidadesReservadas += rth.getNumHabitaciones();
                                }
                            }
                        }
                    }
                }
            }

            // Si el stock total menos lo que ya está reservado es mayor que 0, hay disponibilidad
            int restantes = stockTotal - unidadesReservadas;
            if (restantes > 0) {
                //Se crea una copia temporal del tipo de habitacion modificando sus disponibles en funcion de las reservas ya exsitentes
                Habitacion copia = new Habitacion();
                copia.setTipo(habitacion.getTipo());
                copia.setPrecioPorNoche(habitacion.getPrecioPorNoche());
                copia.setDisponibles(restantes);
                habitacionesDisponibles.add(copia);
            }
        }

        return habitacionesDisponibles;
    }

    @Override
    public Reserva iniciarReserva(String nombreHotel, Date fechaEntrada, Date fechaSalida) {
        Hotel h = hotelesDao.hotel(nombreHotel);
        if (h != null) {
            Reserva r = new Reserva();
            r.setFechaEntrada(fechaEntrada);
            r.setFechaSalida(fechaSalida);
            r.setHotelReservado(h);
            return r;
        }
        return null;
    }

    @Override
    public void confirmarReserva(Reserva r) {
        reservasDao.creaReserva(r);
    }
}