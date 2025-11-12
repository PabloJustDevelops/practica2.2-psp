// Empleado que intenta usar la sala compartida
// Implementación SIN TimeUnit: se usan Thread.sleep(ms) y wait(ms)

public class Empleado implements Runnable {
    private final String nombre;
    private final SalaDeConferencias sala;

    public Empleado(String nombre, SalaDeConferencias sala) {
        this.nombre = nombre;
        this.sala = sala;
    }

    @Override
    public void run() {
        for (int intento = 1; intento <= 3; intento++) {
            try {
                // Bloqueamos el monitor de la sala para hacer intento atómico y poder esperar notificación
                synchronized (sala) {
                    boolean entro = sala.intentarEntrar(nombre);
                    if (!entro) {
                        // No hay hueco: el empleado espera fuera hasta que alguien salga o se agote el tiempo (12s)
                        System.out.println(nombre + " espera fuera. Reintento " + intento + " de 3 (hasta 12s) ...");
                        sala.wait(12_000); // Se reanuda por notifyAll() o por timeout
                        continue; // Siguiente intento tras esperar
                    }
                }

                // Ya dentro: permanece 10 segundos
                System.out.println(nombre + " está dentro de la sala durante 10s.");
                Thread.sleep(10_000);
                // Salida (tarda 1.5s y notifica liberando sitio)
                sala.salir(nombre);
                return; // Finaliza tras usar la sala
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido y abandona.");
                return;
            }
        }
        // Si tras los 3 intentos no se pudo entrar, el empleado se va
        System.out.println(nombre + " se va tras 3 intentos fallidos.");
    }
}
