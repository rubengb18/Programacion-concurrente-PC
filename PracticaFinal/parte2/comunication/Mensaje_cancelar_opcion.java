package p5.parte2.comunication;

public class Mensaje_cancelar_opcion extends Mensaje {
    public Mensaje_cancelar_opcion(String origen, String destino) {
        super(TipoMensaje.CANCELAR_OPCION, origen, destino);
    }
}
