package p5.parte2.comunication;

import java.io.Serializable;

public class Mensaje_confirmacion_conexion extends Mensaje implements Serializable {

    public Mensaje_confirmacion_conexion(String origen, String destino) {
        super(TipoMensaje.CONFIRMACION_CONEXION, origen, destino);
    }

}
