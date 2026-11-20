package es.unican.ps.uc_hoteles.dominio;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaTipoHabitacion implements Serializable {
    private static final long serialVersionUID = 1L;

    private int numHabitaciones;
    
    // Referencias opcionales hacia los extremos de la asociación si lo requiere tu lógica
    private Habitacion habitacion;
    private Reserva reserva;
}