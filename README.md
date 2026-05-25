 EventosOnline — Plataforma de Gestión de Eventos

 Integrantes

| Nombre | Rama |
|---|---|
| Julian David Patiño | Julian-Rama |
| Sara Sofía Salazar | Sarasofiasalazar |
| Oscar Mateo Moreno | OscarProyect |



 Descripción del Proyecto

**EventosOnline** es una plataforma de gestión de eventos desarrollada en Java con interfaz gráfica JavaFX. Permite a administradores crear y gestionar eventos, recintos, zonas y asientos; y a usuarios registrarse, explorar eventos, comprar entradas y gestionar sus compras. El sistema implementa 9 patrones de diseño y principios SOLID.

---


Patrones de Diseño Implementados

Patrones Creacionales

 1. Singleton — `GestionEventos`
- **Requisito:** Una única instancia que centralice toda la lógica del sistema
- **Problema:** Múltiples instancias de GestionEventos causarían inconsistencia en los datos
- **Propósito:** Garantizar una sola instancia global accesible desde cualquier controlador
- **Solución:** Constructor privado con método `getInstancia()`
```java
public class GestionEventos {
    private static GestionEventos instancia;
    private GestionEventos() {}
    public static GestionEventos getInstancia() {
        if (instancia == null) instancia = new GestionEventos();
        return instancia;
    }
}
```

 Builder — `Compra.CompraBuilder`
- **Requisito:** Crear objetos Compra con múltiples parámetros opcionales
- **Problema:** Un constructor con muchos parámetros es difícil de usar y propenso a errores
- **Propósito:** Construir objetos complejos paso a paso de forma legible
- **Solución:** Clase interna `CompraBuilder` con métodos encadenados
```java
Compra compra = new Compra.CompraBuilder()
    .idCompra("C001")
    .usuario(usuario)
    .evento(evento)
    .fechaCreacion(new Date())
    .build();
```

 Prototype — Clonación de Compras
- **Requisito:** Duplicar compras existentes para reutilizar configuraciones
- **Problema:** Crear una compra similar desde cero es repetitivo
- **Propósito:** Clonar objetos existentes como base para nuevos
- **Solución:** Implementación de `Cloneable` en la clase `Compra`

---

 Patrones Estructurales

 4. Decorator — `EntradaDecorator`
- Requisito: Agregar servicios adicionales a una entrada de forma flexible
- Problema: Heredar todas las combinaciones posibles de servicios generaría explosión de clases
- Propósito: Agregar responsabilidades dinámicamente sin modificar la clase base
- Solución: Decoradores `VipDecorator`, `SeguroDecorator`, `PreferencialDecorator`, `ParqueaderoDecorator`
```java
Entrada entrada = new EntradaBase("E001", zona, precio, EstadoEntrada.ACTIVA);
entrada = new VipDecorator(entrada);
entrada = new SeguroDecorator(entrada);
// Precio y descripción se acumulan dinámicamente
```

5. Proxy — `SistemaProxy`
- **Requisito:** Controlar el acceso a operaciones según el perfil del usuario
- **Problema:** Cualquier usuario podría ejecutar operaciones restringidas a administradores
- **Propósito:** Interponer un control de acceso antes de delegar al objeto real
- **Solución:** `SistemaProxy` verifica el perfil antes de llamar a `SistemaReal`

 6. Facade — `ProcesoPagoFacade`
- **Requisito:** Simplificar el proceso de pago que involucra múltiples subsistemas
- **Problema:** El controlador tendría que coordinar validación, pago y confirmación manualmente
- **Propósito:** Proveer una interfaz simplificada a un conjunto complejo de operaciones
- **Solución:** `ProcesoPagoFacade` encapsula todo el flujo de pago en un método

---

Patrones de Comportamiento

 7. Strategy — Métodos de Pago
- **Requisito:** Soportar múltiples métodos de pago intercambiables
- **Problema:** Condicionales `if/else` para cada método de pago son difíciles de mantener
- **Propósito:** Definir una familia de algoritmos intercambiables en tiempo de ejecución
- **Solución:** Interfaz `MetodoPago` con implementaciones `PagoTarjeta`, `PagoEfectivo`, `PagoPSE`, `PagoNequi`
```java
MetodoPago pago = switch (metodoPago) {
    case "Tarjeta"  -> new PagoTarjeta();
    case "Efectivo" -> new PagoEfectivo();
    case "PSE"      -> new PagoPSE();
    case "Nequi"    -> new PagoNequi();
    default         -> new PagoTarjeta();
};
pago.pagar(compra.getTotal());
```

 8. Observer — Notificación de cambios
- **Requisito:** Notificar a los usuarios cuando hay cambios relevantes en el sistema
- **Problema:** Acoplamiento directo entre el modelo y los observadores
- **Propósito:** Definir dependencia uno-a-muchos para notificación automática
- **Solución:** Interfaz `Observable` implementada en `GestionEventos`, interfaz `Observador` implementada en `Usuario`

 9. State — Ciclo de vida de la Compra
- **Requisito:** Gestionar los estados de una compra (Creada → Pagada → Confirmada → Cancelada)
- **Problema:** Lógica de transición de estados dispersa con múltiples condicionales
- **Propósito:** Encapsular el comportamiento según el estado actual del objeto
- **Solución:** Clases `EstadoCreada`, `EstadoPagada`, `EstadoConfirmada`, `EstadoCancelada` implementando `EstadoCompraInterface`

---

 🏛️ Principios SOLID Aplicados

 S — Single Responsibility
Cada clase tiene una única responsabilidad. Ejemplo: `ProcesoPagoFacade` solo gestiona el flujo de pago, `GestionEventos` solo administra los datos del sistema.

 O — Open/Closed
El sistema de decoradores permite agregar nuevos servicios a las entradas sin modificar las clases existentes. Se agrega un nuevo `Decorator` sin tocar `EntradaBase`.

 L — Liskov Substitution
Todas las implementaciones de `MetodoPago` (`PagoTarjeta`, `PagoEfectivo`, etc.) son intercambiables sin alterar el comportamiento del sistema.

 I — Interface Segregation
Las interfaces `Observable` y `Observador` están separadas. `EstadoCompraInterface` solo define los métodos necesarios para los estados de compra.

 D — Dependency Inversion
Los controladores dependen de la abstracción `GestionEventos.getInstancia()` y no de implementaciones concretas. `MetodoPago` es una abstracción que los controladores usan sin conocer la implementación.





