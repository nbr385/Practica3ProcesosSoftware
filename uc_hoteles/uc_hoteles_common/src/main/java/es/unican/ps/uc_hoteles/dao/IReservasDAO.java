package es.unican.ps.uc_hoteles.dao;

import es.unican.ps.uc_hoteles.dominio.Reserva;
import java.util.List;

public interface IReservasDAO {
    public Reserva reserva(int id);
    public List<Reserva> reservas();
    public Reserva creaReserva(Reserva r);
    public boolean eliminaReserva(Reserva r);
    public Reserva actualizaReserva(Reserva r);
}