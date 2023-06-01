package p5.parte2.cliente;

import p5.parte2.comunication.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class OyenteServidor extends Thread implements Runnable {
    private final AlgTicket alg;    //Semaforo para evitar imprimir el menu antes de la salida de algunos mensajes
    private final ObjectInputStream fin;        // Flujo de entrada
    private final ObjectOutputStream fout;    //Flujo de salida
    private final Cliente cliente;    //Cliente asociado

    protected OyenteServidor(AlgTicket alg, Cliente cliente) throws IOException {
        this.cliente = cliente;
        this.alg = alg;
        //alg.entry(1);
        this.fout = cliente.getFout();
        this.fin = cliente.getFin();
    }

    public void run() {
        try {
            boolean cerrar = false;    //True si se quiere cerrar la conexion

            while (!cerrar) {
                Mensaje m = (Mensaje) fin.readObject();    //Leemos el mensaje recibido

                switch (m.getTipo()) {
                    case CONFIRMACION_CONEXION:
                        System.out.println("CLIENTE: Conexion establecida con el servidor.");
                        alg.exit();	//Libera el semaforo para que se pueda mostrar el menu
                        break;

                    case CONFIRMACION_LISTA_USUARIOS:
                        System.out.println("CLIENTE: Se ha recibido la lista de usuarios desde el servidor.\n" + ((Mensaje_conf_lista_usuarios) m).getListaUsuarios());
                        alg.exit();	//Libera el semaforo para que se pueda mostrar el menu
                        break;

                    case PREPARADO_SC:
                        // Creamos un hilo receptor para recibir el fichero
                        Thread t = new Receptor(((Mensaje_preparado_sc) m).getIP(), ((Mensaje_preparado_sc) m).getPuerto(), cliente, ((Mensaje_preparado_sc) m).getFileName());
                        t.start();
                        t.join();
                        alg.exit();	//Libera el semaforo para que se pueda mostrar el menu
                        break;

                    case EMITIR_INFO:
                        // Creamos un hilo emisor para enviar el fichero
                        Emisor thread = new Emisor(cliente, ((Mensaje_emitir_info) m).getFileName());
                        thread.start();
                        //Mandamos un mensaje Mensaje_preparado_cs para avisar al servidor de que CLiente-Servidor esta listo
                        fout.writeObject(new Mensaje_preparado_cs(m.getOrigen(), m.getDestino(), cliente.getIp(), thread.getPort(), ((Mensaje_emitir_info) m).getDestinatarioInfo(), ((Mensaje_emitir_info) m).getFileName()));
                        fout.flush();

                        thread.join();
                        break;

                    case CONFIRMACION_CERRAR_CONEXION:
                        System.out.println("CLIENTE: Se ha cerrado la conexion con el servidor.");
                        fin.close();
                        fout.close();
                        alg.exit();
                        cerrar = true;
                        break;

                    case CANCELAR_OPCION:
                        System.out.println("CLIENTE: Ha habido un error en la opcion seleccionada anteriormente.");
                        alg.exit();	//Libera el semaforo para que se pueda mostrar el menu
                    default:
                        break;
                }
            }
        } catch (ClassNotFoundException | IOException | InterruptedException e) {
            System.err.println("\nCLIENTE: Ha ocurrido un error durante la ejecucion en el hilo OyenteServidor.");
            e.printStackTrace();
        }
    }
}
