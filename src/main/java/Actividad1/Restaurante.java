package Actividad1;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Restaurante {
    final int TOTAL_MESAS = 30;

    Map<Integer, String> mesasReservadas;

    public Restaurante() {
        this.mesasReservadas = new HashMap<>();
        for (int i = 1; i <= TOTAL_MESAS; i++)
            mesasReservadas.put(i, null);

    }

    public void pedirMesa(String nomCliente) {
        synchronized (this) {
            Optional<Integer> mesalibre = mesasReservadas
                    .keySet()
                    .stream()
                    .filter(k -> mesasReservadas.get(k) == null).findFirst();

            while (!mesalibre.isPresent()) {
                System.out.println("El cliente " + nomCliente + " debe esperar");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            mesasReservadas.put(mesalibre.get(), nomCliente);
            System.out.println("El cliente " + nomCliente + " se ha sentado en la mesa " + mesalibre.get());
        }
        Random random = new Random();
        int espera = random.nextInt(2000, 3000) + 1;
        try {
            Thread.sleep(espera);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        vaciarMesa(nomCliente);


    }

    public synchronized void vaciarMesa(String nomCliente) {
        mesasReservadas.keySet()
                .stream()
                .filter(k -> nomCliente.equals(mesasReservadas.get(k)))
                .findFirst()
                .ifPresent(k -> {
                    mesasReservadas.put(k, null);
                    System.out.println("El cliente " + nomCliente + " ha salido del restaurante");
                    this.notifyAll();
                });
    }

    public static void main(String[] args) {
        int codigoCliente = 1;
        Restaurante restaurante = new Restaurante();

        while (true) {
            Cliente cliente = new Cliente("Cliente-" + codigoCliente++, restaurante);
            Thread hilo = new Thread(cliente);
            hilo.start();

            try {
                Thread.sleep(1000); // cada 0.4 s llega un nuevo cliente
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
