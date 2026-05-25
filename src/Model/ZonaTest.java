package Model;

import Model.Enums.EstadoAsiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZonaTest {

    private Zona zona;

    @BeforeEach
    void setUp() {
        zona = new Zona("Z1", "VIP", 100, 50.0, "10x10");
    }

    @Test
    @DisplayName("getOcupacion() retorna 0 cuando no hay asientos")
    void testOcupacionSinAsientos() {
        assertEquals(0, zona.getOcupacion());
    }

    @Test
    @DisplayName("getOcupacion() retorna 0 cuando ningún asiento está vendido")
    void testOcupacionNingunVendido() {
        zona.agregarAsiento(new Asiento("A1", "A", 1, EstadoAsiento.DISPONIBLE));
        zona.agregarAsiento(new Asiento("A2", "A", 2, EstadoAsiento.DISPONIBLE));

        assertEquals(0, zona.getOcupacion());
    }

    @Test
    @DisplayName("getOcupacion() retorna 50 cuando la mitad de asientos están vendidos")
    void testOcupacionMitadVendida() {
        zona.agregarAsiento(new Asiento("A1", "A", 1, EstadoAsiento.VENDIDO));
        zona.agregarAsiento(new Asiento("A2", "A", 2, EstadoAsiento.VENDIDO));
        zona.agregarAsiento(new Asiento("A3", "A", 3, EstadoAsiento.DISPONIBLE));
        zona.agregarAsiento(new Asiento("A4", "A", 4, EstadoAsiento.DISPONIBLE));

        assertEquals(50, zona.getOcupacion());
    }

    @Test
    @DisplayName("getOcupacion() retorna 100 cuando todos los asientos están vendidos")
    void testOcupacionTotal() {
        zona.agregarAsiento(new Asiento("A1", "A", 1, EstadoAsiento.VENDIDO));
        zona.agregarAsiento(new Asiento("A2", "A", 2, EstadoAsiento.VENDIDO));

        assertEquals(100, zona.getOcupacion());
    }
}