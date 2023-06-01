//RUBEN GOMEZ BLANCO Y MIGUEL JESUS PEÑALVER CARVAJAL

package p5.parte2.cliente;

import java.io.IOException;

public class MainCliente {

    public static void main(String[] args) throws IOException {
        Cliente c;
        try {
            c = new Cliente();
            c.main();
        } catch (InterruptedException e) {
            System.err.println("Ha ocurrido un error durante la ejecucion del cliente.");
            e.printStackTrace();
        }

    }

}
