package p5.parte2.comunication;

public class Mensaje_pedir_info extends Mensaje {
    String info;	//Nombre del fichero solicitado

    public Mensaje_pedir_info(String origen, String destino, String info) {
        super(TipoMensaje.PEDIR_INFO, origen, destino);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
