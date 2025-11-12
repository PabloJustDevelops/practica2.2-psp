// Programa principal: lanza 30 empleados contra una sala con capacidad 5
// Espera a que todos los hilos terminen

import java.util.ArrayList;
import java.util.List;

public class MainConferencia {
    public static void main(String[] args) throws Exception {
        // Crear sala con capacidad máxima de 5 personas
        SalaDeConferencias sala = new SalaDeConferencias(5);

        // Crear y lanzar 30 empleados como hilos
        List<Thread> hilos = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            String nombre = "Empleado-" + i;
            Thread t = new Thread(new Empleado(nombre, sala), nombre);
            hilos.add(t);
            t.start();
            // Comentario: opcionalmente podríamos escalonar las llegadas, pero no es obligatorio
            // Thread.sleep(100);
        }

        // Esperar a que todos los hilos terminen
        for (Thread t : hilos) {
            t.join();
        }

        System.out.println("Todos los empleados han terminado.");
    }
}
