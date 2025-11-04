package Actividad1;

import java.util.Map;
import java.util.Optional;

public class Restaurante {
    final int AFORO_MAX = 30;
    final int TOTAL_MESAS = 30;

    Map<Integer, String> mesasReservadas;

    public Restaurante(Map<Integer, String> mesasReservadas) {
        this.mesasReservadas = mesasReservadas;

        for (int i = 1; i <= 30;i++)
            mesasReservadas.put(i, null);

    }

    public synchronized void pedirMesa(String nomCliente){
        Optional<Integer> mesalibre = mesasReservadas.keySet().stream().filter(k -> mesasReservadas.get(k) == null).findFirst();

        if (mesalibre.isPresent()){
            mesasReservadas.put(mesalibre.get(), nomCliente);
            System.out.println("AL cliente " + nomCliente +  " se le ha asignado la mesa " + mesalibre.get());
        }
        else {
            System.out.println("No hay mesas libres para el cliente " + nomCliente);
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void vaciarMesa(String nomCliente){

    }

    public static void main(String[] args) {
        int codigoCliente = 1;

    }
}
