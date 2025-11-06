package Actividad1;

import java.util.Random;

public class Cliente implements Runnable{

    private String nombre;
    private Restaurante restaurante;

    public Cliente(String nombre, Restaurante restaurante) {
        Thread.currentThread().setName(nombre);
        this.nombre = nombre;
        this.restaurante = restaurante;
    }

    @Override
    public void run() {
        Random random = new Random();
        int tiempo = random.nextInt(400, 800) + 1;
        System.out.println("El cliente " +  nombre +  " entrara en " + tiempo / 1000 + " segundos");
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        restaurante.pedirMesa(nombre);
    }
}
