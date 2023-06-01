package p5.parte2.comunication;

import java.util.ArrayList;

public class Mensaje_conexion extends Mensaje {
    private final ArrayList<String> info;    //Lista de info del usuario
    private final String ip;    //Ip del usurio
    //El origen sera un cliente y el destino el servidor

    public Mensaje_conexion(String origen, String destino, String ip, ArrayList<String> info) {
        super(TipoMensaje.CONEXION, origen, destino);
        this.ip = ip;
        this.info = info;
    }

    public String getIp() {
        return this.ip;
    }

    public ArrayList<String> getInfo() {
        return this.info;
    }
}
