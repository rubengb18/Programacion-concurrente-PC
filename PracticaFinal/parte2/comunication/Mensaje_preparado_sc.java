package p5.parte2.comunication;

public class Mensaje_preparado_sc extends Mensaje {
    private final String ip, fileName;        // Dirección IP
    private final int puerto;    //Puerto para la transmisión

    public Mensaje_preparado_sc(String origen, String destino, String ip, int puerto, String fileName) {
        super(TipoMensaje.PREPARADO_SC, origen, destino);
        this.ip = ip;
        this.puerto = puerto;
        this.fileName = fileName;
    }

    public String getIP() {
        return this.ip;
    }

    public int getPuerto() {
        return this.puerto;
    }

    public String getFileName() {
        return fileName;
    }
}
