package Test;

import Model.Compra;
import Model.TipoCompra;
import Model.EstadoPagada;
import Model.EstadoCancelada;
import Model.EstadoCompraInterface;
import Model.Usuario;
import Model.Evento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    private Compra compra;

    @BeforeEach
    void setUp() {
        Usuario usuario = new Usuario("U1", "Juan", "juan@email.com", "3001234567");
        Evento evento = new Evento("E1", "Concierto", "Música", "Descripción",
                "Bogotá", new java.util.Date(), new java.util.Date(),
                null, 0, 0.0, "");

        compra = new Compra.CompraBuilder()
                .idCompra("C001")
                .usuario(usuario)
                .evento(evento)
                .tipoCompra(TipoCompra.GENERAL)
                .build();
    }

    @Test
    @DisplayName("pagar() cambia el estado de la compra a EstadoPagada")
    void testPagar() {
        compra.pagar();
        assertInstanceOf(EstadoPagada.class, compra.getEstado());
    }

    @Test
    @DisplayName("cancelar() cambia el estado de la compra a EstadoCancelada")
    void testCancelar() {
        compra.cancelar();
        assertInstanceOf(EstadoCancelada.class, compra.getEstado());
    }
}