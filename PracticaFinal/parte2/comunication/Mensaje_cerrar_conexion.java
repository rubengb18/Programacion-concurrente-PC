package p5.parte2.comunication;

public class Mensaje_cerrar_conexion extends Mensaje {

    public Mensaje_cerrar_conexion(String origen, String destino) {
        super(TipoMensaje.CERRAR_CONEXION, origen, destino);
    }

}
