package p5.parte2.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    private static final int puerto = 994;    // Puerto del servidor
    private final ServerSocket ss;    //Socket del servidor
    private final Datos data; // Mapa con los usuarios y sus archivos

    protected Servidor() throws IOException {
        ss = new ServerSocket(puerto);
        data = new Datos();
    }

    public void main() throws IOException {
        System.out.println("El servidor se ha iniciado en el puerto " + puerto + ".");
        while (true) {
            // Socket para comunicarse con los clientes
            Socket socket = ss.accept();
            (new OyenteCliente(socket, this)).start();
        }
    }

    public void cerrarConexion(String u) throws IOException, InterruptedException {
        data.remove(u);
    }

    protected void insertarUsuario(String u, ArrayList<String> info, ObjectOutputStream fout, ObjectInputStream fin) throws IOException, InterruptedException {
        data.add(u, info, fout, fin);
    }

    public String listaUsuarios() throws IOException, InterruptedException {
        return data.getListaUsuarios();
    }

    public ObjectOutputStream getFoutUsuario(String u) throws InterruptedException {
        return data.getFoutUsuario(u);
    }

    public String getUsuarioPropietario(String info) throws InterruptedException {
        return data.getUsuarioPropietario(info);
    }

    public void addFileToUser(String userName, String fileName) throws InterruptedException {
        data.addFile(userName, fileName);
    }
}
