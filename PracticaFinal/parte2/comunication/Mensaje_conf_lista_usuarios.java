package p5.parte2.comunication;

import java.util.ArrayList;
import java.util.HashMap;

public class Mensaje_conf_lista_usuarios extends Mensaje {
    private final String lista_usuarios;    // Lista de usuarios conectados (clave nombre de usuario y valor su lista de ficheros)

    public Mensaje_conf_lista_usuarios(String origen, String destino, String lista_usuarios) {
        super(TipoMensaje.CONFIRMACION_LISTA_USUARIOS, origen, destino);
        this.lista_usuarios = lista_usuarios;
    }

    public String getListaUsuarios() {
        return this.lista_usuarios;
    }
}
