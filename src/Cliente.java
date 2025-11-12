// Cliente que ejecuta tres operaciones sobre la cuenta bancaria
// Extiende Thread según requisitos: cada hilo es un cliente

public class Cliente extends Thread {
    private final String nombre;
    private final CuentaBancaria cuenta;

    public Cliente(String nombre, CuentaBancaria cuenta) {
        super(nombre); // nombramos el hilo con el nombre del cliente
        this.nombre = nombre;
        this.cuenta = cuenta;
    }

    @Override
    public void run() {
        for (int op = 1; op <= 3; op++) {
            try {
                // Acceso controlado: como máximo 2 clientes dentro simultáneamente
                cuenta.entrar(nombre);

                // Elegir operación aleatoria e importe aleatorio 10..1000
                boolean ingreso = Math.random() < 0.5; // 50% probabilidad
                int cantidad = 10 + (int)(Math.random() * 991); // 10..1000

                if (ingreso) {
                    cuenta.ingresar(cantidad, nombre);
                } else {
                    cuenta.retirar(cantidad, nombre);
                }

                // Consultar saldo al finalizar la operación
                int saldo = cuenta.getSaldo();
                System.out.println(nombre + " consulta saldo: " + saldo + "€");

            } catch (InterruptedException e) {
                // En caso de interrupción, marcamos el hilo y salimos
                Thread.currentThread().interrupt();
                System.out.println(nombre + " fue interrumpido y abandona.");
                return;
            } finally {
                // Aseguramos la salida del área de acceso aunque ocurra una excepción
                cuenta.salir(nombre);
            }
        }
        System.out.println(nombre + " completó sus 3 operaciones.");
    }
}
