package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Date fechaEntrada;
    private Date fechaSalida;
    
    // Atributo derivado en el UML
    private float importe; 

    private DatosCliente cliente;
    private DatosPago datosPago;
    private Hotel hotelReservado;
    
    // Representacion de  que una reserva puede tener varios tipos de habitaciones reservadas
    private List<ReservaTipoHabitacion> reservasPorTipo = new ArrayList<>();

    public void addReservaTipoHabitacion(ReservaTipoHabitacion reservaTipoHabitacion) {
        this.reservasPorTipo.add(reservaTipoHabitacion);
    }

    public List<ReservaTipoHabitacion> getReservasTipoHabitacion() {
        return reservasPorTipo;
    }
}