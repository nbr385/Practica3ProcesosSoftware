package es.unican.ps.uc_hoteles.dao;

import es.unican.ps.uc_hoteles.dominio.Hotel;
import jakarta.ejb.Local;

import java.util.List;

@Local 
public interface IHotelesDAO {
    public Hotel hotel(String nombre);
    public List<Hotel> hoteles();
    public Hotel crearHotel(Hotel h);
    public boolean eliminaHotel(Hotel h);
    public Hotel actualizaHotel(Hotel h);
}