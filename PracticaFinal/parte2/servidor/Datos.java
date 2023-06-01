package p5.parte2.servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Datos {
    private final HashMap<String, ArrayList<String>> users;    //Lista de usuarios conectados
    private final HashMap<String, ObjectInputStream> flujos_entrada;    //Lista del flujo de entrada por usuario
    private final HashMap<String, ObjectOutputStream> flujos_salida;    //Lista del flujo de salida por usuario
    private final Monitor m;

    public Datos() {
        this.users = new HashMap<>();
        this.flujos_entrada = new HashMap<>();
        this.flujos_salida = new HashMap<>();
        this.m = new Monitor();
    }

    //Elimina un usuario de la lista
    public void remove(String u) throws InterruptedException {
        m.solicita_escritura();
        users.remove(u);
        flujos_entrada.remove(u);
        flujos_salida.remove(u);
        m.libera_escritura();
    }

    //Añade un usuario a la lista
    public void add(String u, ArrayList<String> info, ObjectOutputStream out, ObjectInputStream in) throws InterruptedException {
        m.solicita_escritura();
        users.put(u, info);
        flujos_salida.put(u, out);
        flujos_entrada.put(u, in);
        m.libera_escritura();
    }

    //Añadimos un fichero a la lista de ficheros correspondiente a un usuario
    public void addFile(String userName, String fileName) throws InterruptedException {
        m.solicita_escritura();
        users.get(userName).add(fileName);
        m.libera_escritura();
    }

    //Devuelve la lista de nombres de usuarios
    public String getListaUsuarios() throws InterruptedException {
        StringBuilder ret = new StringBuilder("Lista de usuarios del servidor:\n");

        m.solicita_lectura();
        for (String u : users.keySet()) ret.append("  ").append(u).append(": ").append(users.get(u)).append("\n");
        m.libera_lectura();

        return ret.toString();
    }

    // Devuelve el flujo de salida de un usuario
    public ObjectOutputStream getFoutUsuario(String userName) throws InterruptedException {
        m.solicita_lectura();
        ObjectOutputStream fout = flujos_salida.get(userName);
        m.libera_lectura();

        return fout;
    }

    //Devuelve el usuario que tiene el fichero con nombre info
    public String getUsuarioPropietario(String info) throws InterruptedException {
        String ret = null;

        m.solicita_lectura();
        for (String u : users.keySet()) {
            for (String s : users.get(u))
                if (info.equals(s)) {
                    ret = u;
                    break;
                }
            if (ret != null) break;
        }
        m.libera_lectura();

        return ret;
    }
}
