package p5.parte2.comunication;

public class Mensaje_conf_cerrar_conexion extends Mensaje {

    public Mensaje_conf_cerrar_conexion(String origen, String destino) {
        super(TipoMensaje.CONFIRMACION_CERRAR_CONEXION, origen, destino);
    }
}
