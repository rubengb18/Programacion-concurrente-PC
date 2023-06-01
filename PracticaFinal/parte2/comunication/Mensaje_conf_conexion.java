package p5.parte2.comunication;

public class Mensaje_conf_conexion extends Mensaje {

    public Mensaje_conf_conexion(String origen, String destino) {
        super(TipoMensaje.CONFIRMACION_CONEXION, origen, destino);
    }
}
