// Programa principal: Simulación de cuenta bancaria con 5 clientes
// Sólo 2 clientes pueden acceder de forma simultánea

public class MainBanco {
    public static void main(String[] args) throws Exception {
        // Saldo inicial de la cuenta (puedes ajustarlo)
        CuentaBancaria cuenta = new CuentaBancaria(5_000);

        // Crear 5 clientes (hilos) con nombres descriptivos
        Cliente c1 = new Cliente("Cliente-1", cuenta);
        Cliente c2 = new Cliente("Cliente-2", cuenta);
        Cliente c3 = new Cliente("Cliente-3", cuenta);
        Cliente c4 = new Cliente("Cliente-4", cuenta);
        Cliente c5 = new Cliente("Cliente-5", cuenta);

        // Lanzar hilos
        c1.start();
        c2.start();
        c3.start();
        c4.start();
        c5.start();

        // Esperar a que terminen (cada uno realiza 3 operaciones)
        c1.join();
        c2.join();
        c3.join();
        c4.join();
        c5.join();

        System.out.println("Simulación terminada. Saldo final: " + cuenta.getSaldo() + "€");
    }
}
