package agenda1daw;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    protected Scanner datos = new Scanner(System.in);
    private Usuarios u1 = new Usuarios();
    
    public Agenda iniciar(Agenda a1){
        boolean falloEnUsuarioYContra = false;
        boolean valido = false;
        do {            
            try {
                System.out.println("Agenda 1DAW");
                System.out.println("Introduzca una de las siguientes opciones:");
                System.out.println("1. Crear un nuevo usuario");
                System.out.println("2. Iniciar sesion como usuario");
                int eleccion = datos.nextInt();
                switch (eleccion) {
                    case 1:
                        u1.crearUsuario();
                        valido = true;
                        break;
                    case 2:
                        if(u1.iniciarSesion() == false && u1.getContadorFallosGeneral() < 3){
                            valido = true;
                        }else{
                            falloEnUsuarioYContra = true;
                            if(falloEnUsuarioYContra){
                                valido = true;
                                u1.setInicioSesionIncorrecto(true);
                            }
                        }
                        if(falloEnUsuarioYContra == false){
                            a1 = u1.cargarAgenda();
                        }
                        break;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Opcion no valida");
            }
        } while (valido == false);
        return a1;
    }
    
    public void opciones(Agenda a1){
        boolean valido = false;
        do {            
            try {
                System.out.println("Que desea hacer?");
                System.out.println("1-Registrar Contacto");
                System.out.println("2-Ver Contactos");
                System.out.println("3-Buscar Contacto");
                System.out.println("4-Eliminar Contacto");
                System.out.println("5-Salir");
                int eleccion = datos.nextInt();
                switch (eleccion) {
                    case 1:
                        System.out.println("1-Registrar Contacto");
                        a1.registrarContacto();
                        valido = false;
                        break;
                    case 2:
                        System.out.println("2-Ver Contactos");
                        a1.verContactos();
                        valido = false;
                        break;
                    case 3:
                        System.out.println("3-Buscar Contacto");
                        a1.mostrarContacto(datos);
                        valido = false;
                        break;
                    case 4:
                        System.out.println("4-Eliminar Contacto");
                        a1.eliminarContacto(datos);
                        valido = false;
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        System.out.println("Gracias por usar Agenda DAW");
                        valido = true;
                        u1.crearAgenda(a1);
                        break;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Opcion no valida");
            }
        } while (valido == false);
    }

    public Usuarios getU1() {
        return u1;
    }

    public void setU1(Usuarios u1) {
        this.u1 = u1;
    }
    
    
}
