package p5.parte2.cliente;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Receptor extends Thread implements Runnable {
    private final String ip_emisor, fileName;    //Direccion IP del emisor, nombre del fichero recibido
    private final int puerto;    //Puerto por el que se recibe la informacion
    private final Cliente cliente;	//Cliente que recibe el fichero

    public Receptor(String ip_emisor, int puerto, Cliente cliente, String fileName) {
        this.ip_emisor = ip_emisor;
        this.puerto = puerto;
        this.cliente = cliente;
        this.fileName = fileName;
    }

    public void run() {
        try {
            Socket socket = new Socket(this.ip_emisor, this.puerto);

            InputStream in = socket.getInputStream();
            OutputStream out = new FileOutputStream(System.getProperty("user.dir") + "\\usuarios\\" + cliente + "\\" + fileName);
            byte[] bytes = new byte[16 * 1024];
            int count;

            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);	//Escribe en el fichero el contenido del fichero emitido
            }
            cliente.addFile(fileName);	//Añade el fichero recibido a la lista de ficheros del cliente receptor

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
