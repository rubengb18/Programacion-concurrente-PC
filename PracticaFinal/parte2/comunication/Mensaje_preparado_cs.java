package p5.parte2.comunication;

public class Mensaje_preparado_cs extends Mensaje {
    private final String ip, destinatarioInfo, fileName;
    private final int puerto;    //Puerto para la transmisión

    public Mensaje_preparado_cs(String origen, String destino, String ip, int puerto, String destinatarioInfo, String fileName) {
        super(TipoMensaje.PREPARADO_CS, origen, destino);
        this.ip = ip;
        this.puerto = puerto;
        this.destinatarioInfo = destinatarioInfo;
        this.fileName = fileName;
    }

    public String getIP() {
        return this.ip;
    }

    public int getPuerto() {
        return this.puerto;
    }

    public String getDestinatarioInfo() {
        return destinatarioInfo;
    }

    public String getFileName() {
        return fileName;
    }
}
