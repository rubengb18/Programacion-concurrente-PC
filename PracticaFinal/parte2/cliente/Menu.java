package p5.parte2.cliente;

import java.util.Scanner;

public class Menu {
	Scanner sn;

	public Menu(Scanner sn) {
		this.sn = sn;
	}
	//Mostrar el menu de opciones del cliente
    public int menu() {
        System.out.println("\n-----------------------------------------------------------");
        System.out.println("¿Que accion deseas realizar?");
        System.out.println("	1 - Consultar el nombre de los usuarios conectados");
        System.out.println("	2 - Descargar un archivo");
        System.out.println("	0 - Salir");
        System.out.println("-----------------------------------------------------------");
        System.out.print(" Escribe tu opcion: ");

        int opcion = -1;
        boolean entero = false;
        while (!entero || opcion < 0 || opcion > 2) {
            entero = true;
            try {
                opcion = Integer.parseInt(sn.next());	//Leo el entero que proporciona el usuario
            } catch (NumberFormatException e) {
                entero = false;
            }

            // Si no es un entero o no esta entre los valores disponibles
            if (!entero || opcion < 0 || opcion > 2) {
                System.out.println("Tienes que introducir un entero entre 0 y 2");
                System.out.print(" Vuelve a escribir tu opcion: ");
            }
        }
        System.out.println();
        return opcion;
    }
}
