package p5.parte2.cliente;

import p5.parte2.comunication.Mensaje_cerrar_conexion;
import p5.parte2.comunication.Mensaje_conexion;
import p5.parte2.comunication.Mensaje_lista_usuarios;
import p5.parte2.comunication.Mensaje_pedir_info;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
    private static final int puerto = 994;    // Puerto que utilizan los sockets
    private final Socket socket;        //Socket
    private final ObjectOutputStream fout;        //Flujo de salida
    private final ObjectInputStream fin;    //Flujo de entrada
    private final AlgTicket alg;    // Semaforo utilizado para retrasar la concurrencia del menu de opciones
    private String nombre;	//Nombre del cliente
    private String ip;	//Direccion IP del cliente
    private final String servidorIP = "127.0.0.1";    //Direccion IP del servidor
    private final ArrayList<String> info;    //Lista con la info que contiene el cliente (nombres de los ficheros)

    protected Cliente() throws IOException {
        this.socket = new Socket(servidorIP, puerto);
        this.fout = new ObjectOutputStream(socket.getOutputStream());
        this.fin = new ObjectInputStream(socket.getInputStream());
        this.alg = new AlgTicket(1);
        this.info = new ArrayList<>();	//Inicialmente vacia asique hay que cargarla
    }


    public void main() throws InterruptedException {
        Scanner sn = new Scanner(System.in);
        try {
            preguntaNombre(sn);

            OyenteServidor os = new OyenteServidor(alg, this);	//Iniciamos hilo OyenteServidor para comunicarnos con el servidor
            os.start();
            
            leeInfo(sn);    // Carga la lista de archivos

            //Manda Mensaje_conexion para avisar al servidor que se quiere establecer conexion con el
            fout.writeObject(new Mensaje_conexion(this.nombre, "Servidor", this.ip, new ArrayList<>(info)));
            fout.flush();

            alg.entry(0);
            Menu menu=new Menu(sn);

            int opcion = -1;
            while (opcion != 0) {
                alg.entry(0);    //Para que se muestre el mensaje que acabamos de mandar antes del menu
                opcion = menu.menu();	//Leemos la oppcion que elige el usuario por pantalla

                if (opcion == 0) {    // Salir de la aplicacion
                    System.out.println("Saliendo de la aplicacion...");
                    //Manda Mensaje_cerrar_conexion para avisar al servidor que se quiere cerrar conexion con el
                    fout.writeObject(new Mensaje_cerrar_conexion(this.nombre, "Servidor"));
                    fout.flush();

                } else if (opcion == 1) {    // Mostrar la lista de usuarios conectados
                    //Manda Mensaje_lista_usuarios para avisar al servidor que se quiere obtener la lista  de usuarios conectados
                    fout.writeObject(new Mensaje_lista_usuarios(this.nombre, "Servidor"));
                    fout.flush();

                } else if (opcion == 2) { // Descargar un archivo
                    System.out.print("Por favor, escribe a continuacion el archivo deseado: ");
                    String s = sn.next();
                    //Manda Mensaje_pedir_info para avisar al servidor que se quiere obtener un fichero otro usuario conectado
                    fout.writeObject(new Mensaje_pedir_info(this.nombre, "Servidor", s));
                    fout.flush();
                }
            }
            os.join();
            sn.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void preguntaNombre(Scanner sn) throws UnknownHostException {
        System.out.print("Introduce tu nombre: ");
        // Almaceno el nombre del usuario y su IP
        this.nombre = sn.nextLine();
        InetAddress address = InetAddress.getLocalHost();
        this.ip = address.getHostAddress();
    }


    //Lee los ficheros del cliente
    public void leeInfo(Scanner sn) {
    	try {
	        File folder = new File(System.getProperty("user.dir") + "\\usuarios\\" + this);	//Carpeta cuyo nombre es el del usuario proporcionado
	        File[] listOfFiles = folder.listFiles();

            assert listOfFiles != null;
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile())
                    info.add(listOfFile.getName());    //Cargo a la lista de informacion del cliente los textos de su carpeta
            }
    	}
    	catch(NullPointerException e){	//Si no hay ninguna carpeta con el nombre del usuario
            System.out.println("El nombre no coincide con ningun usuario posible");
            try {
				this.preguntaNombre(sn);
				this.leeInfo(sn);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
    	}
    }

    public void addFile(String fileName) {
        info.add(fileName);
    }

    // Devuelve la IP del cliente
    public String getIp() {
        return this.ip;
    }

    // Devuelve el nombre del cliente
    public String toString() {
        return this.nombre;
    }

    //Devuelve el flujo de entrada
    public ObjectInputStream getFin() {
        return fin;
    }

    // Devuelve el flujo de salida
    public ObjectOutputStream getFout() {
        return fout;
    }
}
