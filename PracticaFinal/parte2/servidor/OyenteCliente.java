package p5.parte2.servidor;

import p5.parte2.comunication.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OyenteCliente extends Thread {
    private final Socket socket;        //Socket utilizado en la comunicacion
    private final Servidor servidor;    //Servidor
    private final ObjectOutputStream fout;    // Flujos de la comunicacion
    private final ObjectInputStream fin;    //Flujo de entrada

    protected OyenteCliente(Socket s, Servidor servidor) throws IOException {
        this.socket = s;
        this.servidor = servidor;
        this.fout = new ObjectOutputStream(this.socket.getOutputStream());
        this.fin = new ObjectInputStream(this.socket.getInputStream());
    }

    public void run() {
        try {
            boolean cerrar = false;    // True si se quiere cerrar la conexion

            while (!cerrar) {
                Mensaje m = (Mensaje) fin.readObject();    // Leemos el mensaje recibido
                switch (m.getTipo()) {
                    case CONEXION:
                        servidor.insertarUsuario(m.getOrigen(), ((Mensaje_conexion) m).getInfo(), fout, fin);

                        // Envío mensaje de confirmación al usuario
                        fout.writeObject(new Mensaje_conf_conexion("Servidor", m.getOrigen()));
                        fout.flush();
                        System.out.println("SERVIDOR: Conexion establecida con el usuario " + m.getOrigen() + ".");
                        break;

                    case LISTA_USUARIOS:
                        // Envío un nuevo mensaje con la lista de usuarios
                        fout.writeObject(new Mensaje_conf_lista_usuarios("Servidor", m.getOrigen(), servidor.listaUsuarios()));
                        fout.flush();
                        System.out.println("SERVIDOR: Se ha mandado la lista de usuarios al usuario " + m.getOrigen() + ".");
                        break;

                    case CERRAR_CONEXION:
                        servidor.cerrarConexion(m.getOrigen());
                        //Envio mensaje de confirmacion (del servidor) para cerrar la conexion entre el servidor y el cliente
                        fout.writeObject(new Mensaje_conf_cerrar_conexion("Servidor", m.getOrigen()));
                        fout.flush();
                        System.out.println("SERVIDOR: Conexion cerrada con el usuario " + m.getOrigen() + ".");
                        cerrar = true;
                        break;

                    case PEDIR_INFO:
                        //Obtengo el usuario al cual le estamos pidiendo el fichero solicitado
                        String u = servidor.getUsuarioPropietario(((Mensaje_pedir_info) m).getInfo());
                        if (u != null) {
                            //Cogemos el flujo de salida del usuario que tiene que emitir la informacion
                            ObjectOutputStream fout2 = servidor.getFoutUsuario(u);
                            fout2.writeObject(new Mensaje_emitir_info("Servidor", u, ((Mensaje_pedir_info) m).getInfo(), m.getOrigen()));
                            fout2.flush();

                            System.out.println("SERVIDOR: El usuario " + u + " va a mandar el archivo " + ((Mensaje_pedir_info) m).getInfo() + " al usuario " + m.getOrigen() + ".");
                        } else {
                            System.out.println("SERVIDOR: El fichero solicitado no se encuentra ligado a ningún usuario conectado.");

                            fout.writeObject(new Mensaje_cancelar_opcion("Servidor", m.getOrigen()));
                            fout.flush();
                        }
                        break;

                    case PREPARADO_CS:
                        // Obtengo flujo de salida del cliente que solicitó el fichero
                        ObjectOutputStream fout2 = servidor.getFoutUsuario(((Mensaje_preparado_cs) m).getDestinatarioInfo());
                        //Mandamos un mensaje Mensaje_preparado_sc para avisar al cliente de que Servidor-Cliente esta listo
                        fout2.writeObject(new Mensaje_preparado_sc("Servidor", ((Mensaje_preparado_cs) m).getDestinatarioInfo(), ((Mensaje_preparado_cs) m).getIP(), ((Mensaje_preparado_cs) m).getPuerto(), ((Mensaje_preparado_cs) m).getFileName()));
                        fout2.flush();
                        //Añade el fichero a la lista de ficheros del usuario
                        servidor.addFileToUser(((Mensaje_preparado_cs) m).getDestinatarioInfo(), ((Mensaje_preparado_cs) m).getFileName());
                        break;

                    default:
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.err.println("SERVIDOR: Ha ocurrido un error durante la ejecucion en el hilo oyenteCliente");
            e.printStackTrace();
        }
    }
}
