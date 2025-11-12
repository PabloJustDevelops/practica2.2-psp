// Cuenta bancaria compartida por varios clientes
// Limita el acceso concurrente a 2 clientes usando synchronized + wait/notifyAll

public class CuentaBancaria {
    // Saldo de la cuenta y control de acceso concurrente
    private int saldo;
    private int concurrentes = 0;
    private final int maxConcurrentes = 2;

    public CuentaBancaria(int saldoInicial) {
        this.saldo = saldoInicial;
    }

    // Entrada a la zona de operaciones: como máximo 2 clientes simultáneos
    public synchronized void entrar(String cliente) throws InterruptedException {
        while (concurrentes >= maxConcurrentes) {
            System.out.println(cliente + " espera acceso a la cuenta... (dentro: " + concurrentes + ")");
            wait();
        }
        concurrentes++;
        System.out.println(cliente + " accede a la cuenta. Concurrentes: " + concurrentes);
    }

    // Salida y notificación a los que esperan
    public synchronized void salir(String cliente) {
        concurrentes--;
        System.out.println(cliente + " sale de la cuenta. Concurrentes: " + concurrentes);
        notifyAll();
    }

    // Ingreso de dinero: tarda entre 1 y 3 segundos. Actualización del saldo atómica.
    public void ingresar(int cantidad, String cliente) throws InterruptedException {
        int ms = 1000 + (int)(Math.random() * 2000); // 1..3 segundos
        Thread.sleep(ms);
        synchronized (this) {
            saldo += cantidad;
            System.out.println(cliente + " INGRESA " + cantidad + "€ (tarda " + ms + " ms). Saldo: " + saldo + "€");
        }
    }

    // Retirada de dinero: tarda entre 1 y 3 segundos. Comprobación y actualización atómica.
    public boolean retirar(int cantidad, String cliente) throws InterruptedException {
        int ms = 1000 + (int)(Math.random() * 2000); // 1..3 segundos
        Thread.sleep(ms);
        synchronized (this) {
            if (saldo >= cantidad) {
                saldo -= cantidad;
                System.out.println(cliente + " RETIRA " + cantidad + "€ (tarda " + ms + " ms). Saldo: " + saldo + "€");
                return true;
            } else {
                System.out.println(cliente + " intenta RETIRAR " + cantidad + "€ pero saldo insuficiente (" + saldo + "€). Tiempo " + ms + " ms");
                return false;
            }
        }
    }

    // Consulta de saldo: sincronizada para lectura consistente
    public synchronized int getSaldo() {
        return saldo;
    }
}
