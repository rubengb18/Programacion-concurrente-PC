package p5.parte2.cliente;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread implements Runnable {
    private final Cliente cliente;    //Cliente emisor
    private final String info;    //Fichero que queremos emitir
    private final ServerSocket ss_nuevo;    //Server socket nuevo

    public Emisor(Cliente cliente, String info) throws IOException {
        this.cliente = cliente;
        this.info = info;
        this.ss_nuevo = new ServerSocket(0);	//Con 0 coge uno aleatorio disponible
    }

    public int getPort() {
        return ss_nuevo.getLocalPort();
    }

    public void run() {
        try {
            Socket socket = ss_nuevo.accept();
            //Cogemos el fichero a emitir
            File file = new File(System.getProperty("user.dir") + "\\usuarios\\" + cliente + "\\" + info);
            byte[] bytes = new byte[16 * 1024];
            InputStream in = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();
            int count;

            while ((count = in.read(bytes)) > 0) {	//Leemos los bytes del fichero a emitir
                out.write(bytes, 0, count);	//Los transmitimos al receptor
            }

            out.close();
            in.close();
            ss_nuevo.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
