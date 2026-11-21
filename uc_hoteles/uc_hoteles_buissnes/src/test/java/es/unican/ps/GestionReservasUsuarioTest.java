package es.unican.ps;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.unican.ps.uc_hoteles.dao.IHotelesDAO;
import es.unican.ps.uc_hoteles.dao.IReservasDAO;
import es.unican.ps.uc_hoteles.dominio.Habitacion;
import es.unican.ps.uc_hoteles.dominio.Hotel;
import es.unican.ps.uc_hoteles.dominio.Reserva;
import es.unican.ps.uc_hoteles.dominio.ReservaTipoHabitacion;
import es.unican.ps.uc_hoteles.dominio.TipoHabitacion;

public class GestionReservasUsuarioTest {

    private GestionReservasUsuario gestionReservas;
    private IHotelesDAO hotelesDao;
    private IReservasDAO reservasDao;

    private Hotel hotel;
    private Habitacion doble;
    private Habitacion suite;

    @BeforeEach
    void setUp() {
        hotelesDao = mock(IHotelesDAO.class);
        reservasDao = mock(IReservasDAO.class);

        gestionReservas = new GestionReservasUsuario(hotelesDao, reservasDao);

        doble = new Habitacion();
        doble.setTipo(TipoHabitacion.DOBLE);
        doble.setPrecioPorNoche(85.0f);
        doble.setDisponibles(2);

        suite = new Habitacion();
        suite.setTipo(TipoHabitacion.SUITE);
        suite.setPrecioPorNoche(160.0f);
        suite.setDisponibles(1);

        hotel = new Hotel();
        hotel.setNombre("Hotel Sardinero");
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        habitaciones.add(doble);
        habitaciones.add(suite);
        hotel.setHabitaciones(habitaciones);
    }

    @Test
    void testConsultarDisponibilidadSinReservas() {
        Date entrada = aDate(2026, 7, 10);
        Date salida = aDate(2026, 7, 15);

        when(hotelesDao.hotel("Hotel Sardinero")).thenReturn(hotel);
        when(reservasDao.reservas()).thenReturn(Collections.emptyList());

        List<Habitacion> disponibles = gestionReservas.consultarDisponibilidad("Hotel Sardinero", entrada, salida);

        assertNotNull(disponibles);
        assertEquals(2, disponibles.size());

        Habitacion habDoble = disponibles.get(0);
        Habitacion habSuite = disponibles.get(1);

        assertEquals(TipoHabitacion.DOBLE, habDoble.getTipo());
        assertEquals(2, habDoble.getDisponibles());
        assertEquals(85.0, habDoble.getPrecioPorNoche());
        assertNotSame(doble, habDoble);

        assertEquals(TipoHabitacion.SUITE, habSuite.getTipo());
        assertEquals(1, habSuite.getDisponibles());
        assertEquals(160.0, habSuite.getPrecioPorNoche());
        assertNotSame(suite, habSuite);

        // Comprueba que no se ha modificado el stock original de las habitaciones del hotel
        assertEquals(2, doble.getDisponibles());
        assertEquals(1, suite.getDisponibles());

        verify(hotelesDao).hotel("Hotel Sardinero");
        verify(reservasDao).reservas();
    }

    @Test
    void testConsultarDisponibilidadReservaSolapadaDescuentaYAgotaTipo() {
        Date entrada = aDate(2026, 7, 10);
        Date salida = aDate(2026, 7, 15);

        ReservaTipoHabitacion lineaDoble = crearLinea(doble, 1);
        ReservaTipoHabitacion lineaSuite = crearLinea(suite, 1);

        List<ReservaTipoHabitacion> lineas = new ArrayList<>();
        lineas.add(lineaDoble);
        lineas.add(lineaSuite);

        // Reserva del 12 al 14 (solapa con 10-15): descuenta 1 DOBLE y agota la SUITE
        Reserva reservaSolapada = crearReserva(
                hotel,
                aDate(2026, 7, 12),
                aDate(2026, 7, 14),
                lineas
        );

        when(hotelesDao.hotel("Hotel Sardinero")).thenReturn(hotel);
        when(reservasDao.reservas()).thenReturn(List.of(reservaSolapada));

        List<Habitacion> disponibles = gestionReservas.consultarDisponibilidad("Hotel Sardinero", entrada, salida);

        assertNotNull(disponibles);
        assertEquals(1, disponibles.size());
        assertEquals(TipoHabitacion.DOBLE, disponibles.get(0).getTipo());
        assertEquals(1, disponibles.get(0).getDisponibles());
    }

    @Test
    void testConsultarDisponibilidadReservaFueraDeRangoNoSolapa() {
        Date entrada = aDate(2026, 7, 10);
        Date salida = aDate(2026, 7, 15);

        List<ReservaTipoHabitacion> lineas = new ArrayList<>();
        lineas.add(crearLinea(suite, 1));

        // Reserva del 1 al 10: r.getFechaSalida().after(fechaEntrada) es false -> no solapa
        Reserva reservaAnterior = crearReserva(
                hotel,
                aDate(2026, 7, 1),
                aDate(2026, 7, 10),
                lineas
        );

        when(hotelesDao.hotel("Hotel Sardinero")).thenReturn(hotel);
        when(reservasDao.reservas()).thenReturn(List.of(reservaAnterior));

        List<Habitacion> disponibles = gestionReservas.consultarDisponibilidad("Hotel Sardinero", entrada, salida);

        assertNotNull(disponibles);
        assertEquals(2, disponibles.size());
        assertEquals(2, disponibles.get(0).getDisponibles());
        assertEquals(1, disponibles.get(1).getDisponibles());
    }

    @Test
    void testConsultarDisponibilidadReservaDeOtroHotelNoAfecta() {
        Date entrada = aDate(2026, 7, 10);
        Date salida = aDate(2026, 7, 15);

        Hotel otroHotel = new Hotel();
        otroHotel.setNombre("Otro Hotel");

        List<ReservaTipoHabitacion> lineas = new ArrayList<>();
        lineas.add(crearLinea(suite, 1));

        Reserva reservaOtroHotel = crearReserva(
                otroHotel,
                aDate(2026, 7, 11),
                aDate(2026, 7, 13),
                lineas
        );

        when(hotelesDao.hotel("Hotel Sardinero")).thenReturn(hotel);
        when(reservasDao.reservas()).thenReturn(List.of(reservaOtroHotel));

        List<Habitacion> disponibles = gestionReservas.consultarDisponibilidad("Hotel Sardinero", entrada, salida);

        assertNotNull(disponibles);
        assertEquals(2, disponibles.size());
        assertEquals(1, disponibles.get(1).getDisponibles());
    }

    @Test
    void testConsultarDisponibilidadFechasInvertidasONulasDevuelveNull() {
        Date entrada = aDate(2026, 7, 15);
        Date salida = aDate(2026, 7, 10);

        when(hotelesDao.hotel("Hotel Sardinero")).thenReturn(hotel);

        assertNull(gestionReservas.consultarDisponibilidad("Hotel Sardinero", entrada, salida));
        assertNull(gestionReservas.consultarDisponibilidad("Hotel Sardinero", null, salida));
        assertNull(gestionReservas.consultarDisponibilidad("Hotel Sardinero", entrada, null));
    }

    @Test
    void testConsultarDisponibilidadHotelNoEncontradoDevuelveListaVacia() {
        Date entrada = aDate(2026, 7, 10);
        Date salida = aDate(2026, 7, 15);

        when(hotelesDao.hotel("Hotel Inexistente")).thenReturn(null);

        List<Habitacion> disponibles = gestionReservas.consultarDisponibilidad("Hotel Inexistente", entrada, salida);

        assertNotNull(disponibles);
        assertTrue(disponibles.isEmpty());
    }

    private Reserva crearReserva(Hotel h, Date fEntrada, Date fSalida, List<ReservaTipoHabitacion> lineas) {
        Reserva r = new Reserva();
        r.setHotelReservado(h);
        r.setFechaEntrada(fEntrada);
        r.setFechaSalida(fSalida);
        r.setReservasPorTipo(lineas);
        return r;
    }

    private ReservaTipoHabitacion crearLinea(Habitacion hab, int cantidad) {
        ReservaTipoHabitacion rth = new ReservaTipoHabitacion();
        // Ajusta estos dos setters a los nombres exactos de tu clase ReservaTipoHabitacion
        rth.setTipoHabitacion(hab);
        rth.setNumHabitaciones(cantidad);
        return rth;
    }

    private Date aDate(int anio, int mes, int dia) {
        return Date.from(LocalDate.of(anio, mes, dia)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
    }
}