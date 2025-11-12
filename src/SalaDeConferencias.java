// Control de acceso a una sala de conferencias compartida
// Implementación SIN Semaphore ni TimeUnit: synchronized + wait/notifyAll

public class SalaDeConferencias {
    // Capacidad máxima y ocupación actual
    private final int maxCapacidad;
    private int ocupacion = 0;

    public SalaDeConferencias(int maxCapacidad) {
        this.maxCapacidad = maxCapacidad;
    }

    // Intenta entrar si hay hueco en este momento. Operación atómica bajo el monitor de la instancia.
    public synchronized boolean intentarEntrar(String empleadoNombre) {
        // Si hay espacio, entra inmediatamente
        if (ocupacion < maxCapacidad) {
            ocupacion++;
            System.out.println(empleadoNombre + " ha entrado. Ocupación ahora: " + ocupacion);
            return true;
        }
        // No hay espacio; no entra en este intento
        System.out.println(empleadoNombre + " no pudo entrar (sala llena). Ocupación actual: " + ocupacion);
        return false;
    }

    // Salir tarda 1.5s y, al liberar, notifica a los que esperan en el monitor
    public void salir(String empleadoNombre) throws InterruptedException {
        // Simulamos el tiempo de salida de 1.5 segundos
        Thread.sleep(1500);
        synchronized (this) {
            ocupacion--;
            System.out.println(empleadoNombre + " ha salido. Ocupación ahora: " + ocupacion);
            // Notificamos a todos los hilos que podrían estar esperando fuera para reintentar
            notifyAll();
        }
    }

    // Método auxiliar para logs y comprobaciones
    public synchronized int ocupacionActual() {
        return ocupacion;
    }
}
