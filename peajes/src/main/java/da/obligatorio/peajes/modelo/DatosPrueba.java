package da.obligatorio.peajes.modelo;

import java.util.Date;
import java.util.Calendar;

public class DatosPrueba {

    public static void cargar() throws PeajeException {

        Fachada fachada = Fachada.getInstancia();

        // Datos prueba Estado

        fachada.agregarTipoEstado("Habilitado");
        fachada.agregarTipoEstado("Deshabilitado");
        fachada.agregarTipoEstado("Suspendido");
        fachada.agregarTipoEstado("Penalizado");

        // Datos prueba Bonificacion
        Bonificacion b1 = new Frecuente();
        Bonificacion b2 = new Exonerado();
        Bonificacion b3 = new Trabajador();

        // Datos prueba usuarios
        Administrador a1 = new Administrador("a", "1234567", "Administrador Prueba");
        Administrador a2 = new Administrador("a2", "8765432", "Segundo admin");
        Propietario p2 = new Propietario("prop123", "1122334", "Propietario1", 600.0, 500.0);
        // datos de prueba por letra que tienen que estar precargados:
        Administrador a3 = new Administrador("admin.123", "12345678", "Usuario Administrador");
        Propietario p1 = new Propietario("prop.123", "23456789", "Usuario Propietario", 2000.0, 500.0);

        // Datos prueba Categoria
        Categoria c1 = new Categoria("Automovil");
        Categoria c2 = new Categoria("Moto");
        Categoria c3 = new Categoria("Camion");
        Categoria c4 = new Categoria("Bus");

        // Datos prueba Vehiculos tring matricula, Categoria categoria, String modelo,
        // String color, Propietario propietario
        Vehiculo v1 = new Vehiculo("ABC1234", c1, "Toyota Corolla", "Rojo", p1);
        Vehiculo v2 = new Vehiculo("DEF5678", c2, "Honda CBR500R", "Azul", p1);
        Vehiculo v3 = new Vehiculo("GHI9012", c3, "Ford F-150", "Negro", p1);
        Vehiculo v4 = new Vehiculo("JKL3456", c4, "Mercedes-Benz Sprinter", "Blanco", p1);
        Vehiculo v5= new Vehiculo("ASE1243", c1, "Nissan Sentra", "Gris", p2);
        Vehiculo v6 = new Vehiculo ("QER4576",c2,"Yamaha YZF-R3","Verde",p2);
        Vehiculo v7 = new Vehiculo ("TUI7890",c3,"Volvo FH16","Azul Oscuro",p2);

        // Datos prueba Puesto
        Puesto puesto1 = new Puesto("Santa Lucia", "Ruta 11 km 81");
        Puesto puesto2 = new Puesto("Pando", "Ruta 8 km 45");
        Puesto puesto3 = new Puesto("Canelones", "Ruta 5 km 23");
        Puesto puesto4 = new Puesto("Colonia", "Ruta 1 km 120");

        // Fechas para probar

        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JUNE, 15, 10, 30, 0);
        Date fechaHora = cal.getTime();

        // Agregar datos de prueba a la fachada

        fachada.agregarBonificacion(b1);
        fachada.agregarBonificacion(b2);
        fachada.agregarBonificacion(b3);

        fachada.agregarUsuario(a1);
        fachada.agregarUsuario(a2);
        fachada.agregarUsuario(p1);
        fachada.agregarUsuario(a3);
        fachada.agregarUsuario(p2);

        fachada.agregarCategoria(c1);
        fachada.agregarCategoria(c2);
        fachada.agregarCategoria(c3);
        fachada.agregarCategoria(c4);

        fachada.agregarVehiculo(v1);
        fachada.agregarVehiculo(v2);
        fachada.agregarVehiculo(v3);
        fachada.agregarVehiculo(v4);
        fachada.agregarVehiculo(v5);
        fachada.agregarVehiculo(v6);
        fachada.agregarVehiculo(v7);

        fachada.agregarPuesto(puesto1);
        fachada.agregarPuesto(puesto2);
        fachada.agregarPuesto(puesto3);
        fachada.agregarPuesto(puesto4);

        // Datos prueba Tarifa para puesto Santa Lucia
        fachada.agregarTarifa("Santa Lucia", "Automovil", 100.00);
        fachada.agregarTarifa("Santa Lucia", "Moto", 50.00);
        fachada.agregarTarifa("Santa Lucia", "Camion", 200.00);
        fachada.agregarTarifa("Santa Lucia", "Bus", 150.00);

        // Datos prueba Tarifa para puesto Pando
        fachada.agregarTarifa("Pando", "Automovil", 80.00);
        fachada.agregarTarifa("Pando", "Moto", 40.00);
        fachada.agregarTarifa("Pando", "Camion", 160.00);
        fachada.agregarTarifa("Pando", "Bus", 120.00);

        // Datos prueba Tarifa para puesto Canelones
        fachada.agregarTarifa("Canelones", "Automovil", 90.00);
        fachada.agregarTarifa("Canelones", "Moto", 45.00);
        fachada.agregarTarifa("Canelones", "Camion", 180.00);
        fachada.agregarTarifa("Canelones", "Bus", 135.00);

        // Datos prueba Tarifa para puesto Colonia
        fachada.agregarTarifa("Colonia", "Automovil", 85.00);
        fachada.agregarTarifa("Colonia", "Moto", 42.50);
        fachada.agregarTarifa("Colonia", "Camion", 170.00);
        fachada.agregarTarifa("Colonia", "Bus", 127.50);

        // Datos prueba Asignacion bonificaciones
        fachada.agregarAsignacion("Santa Lucia", "Frecuente", "1122334");
        fachada.agregarAsignacion("Pando", "Trabajador", "1122334");
        fachada.agregarAsignacion("Colonia", "Exonerado", "1122334");
        fachada.agregarAsignacion("Canelones", "Frecuente", "23456789");

        // Datos prueba transito
        fachada.agregarTransito("ABC1234", new Date(), "Santa Lucia");
        fachada.agregarTransito("DEF5678", new Date(), "Pando");
        fachada.agregarTransito("GHI9012", new Date(), "Canelones");

    }
}
