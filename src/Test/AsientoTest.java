package Test;

import Model.Asiento;
import Model.EstadoAsiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas unitarias - Clase Asiento")
public class AsientoTest {

    private Asiento asiento;

    @BeforeEach
    void setUp() {
        asiento = new Asiento("A1", "A", 1, EstadoAsiento.DISPONIBLE);
    }

    @Test
    @DisplayName("Constructor inicializa todos los campos correctamente")
    void testConstructor() {
        assertEquals("A1", asiento.getIdAsiento());
        assertEquals("A", asiento.getFila());
        assertEquals(1, asiento.getNumero());
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    @DisplayName("setEstado cambia el estado del asiento correctamente")
    void testSetEstado() {
        asiento.setEstado(EstadoAsiento.RESERVADO);
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstado());
    }

    @Test
    @DisplayName("setFila y setNumero actualizan los valores correctamente")
    void testSetFilaYNumero() {
        asiento.setFila("B");
        asiento.setNumero(5);
        assertEquals("B", asiento.getFila());
        assertEquals(5, asiento.getNumero());
    }
}