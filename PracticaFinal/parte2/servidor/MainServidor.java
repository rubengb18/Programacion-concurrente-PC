//RUBEN GOMEZ BLANCO Y MIGUEL JESUS PEÑALVER CARVAJAL


package p5.parte2.servidor;

import java.io.IOException;

public class MainServidor {

    public static void main(String[] args) throws IOException {

        try {
            Servidor s = new Servidor();
            s.main();
        } catch (IOException e) {
            System.err.println("Error durante la ejecucion del Servidor... ");
            e.printStackTrace();
        }
    }

}
