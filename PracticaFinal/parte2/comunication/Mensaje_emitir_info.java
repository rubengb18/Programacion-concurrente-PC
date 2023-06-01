package p5.parte2.comunication;

public class Mensaje_emitir_info extends Mensaje {
    private final String fileName, destinatarioInfo;	//Nombre del fichero a emitir y nombre del destinatario

    public Mensaje_emitir_info(String origen, String destino, String info, String destinatarioInfo) {
        super(TipoMensaje.EMITIR_INFO, origen, destino);
        this.fileName = info;
        this.destinatarioInfo = destinatarioInfo;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDestinatarioInfo() {
        return destinatarioInfo;
    }
}
