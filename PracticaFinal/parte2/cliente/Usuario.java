package p5.parte2.cliente;

import java.util.ArrayList;

public class Usuario {
    String iden, ip;	//Identificador (nombre) y direccion IP del usuario
    ArrayList<String> info;	//Lista con los nombres de los ficheros del usuario

    public Usuario(String origen, String ip, ArrayList<String> info) {
        this.iden = origen;
        this.ip = ip;
        this.info = info;
    }

    public String toString() {
        return iden;
    }

    public ArrayList<String> getInfo() {
        return info;
    }
}
