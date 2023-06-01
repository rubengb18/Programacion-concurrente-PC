package p5.parte2.comunication;


public class Mensaje_lista_usuarios extends Mensaje {

    public Mensaje_lista_usuarios(String origen, String destino) {
        super(TipoMensaje.LISTA_USUARIOS, origen, destino);
    }


}
