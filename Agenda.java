package agenda1daw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Agenda implements Serializable{
    private ArrayList<Contacto> agenda;

    public Agenda() {
        this.agenda = new ArrayList<>();
    }
    
    public void registrarContacto(){
        boolean yaExiste = false;
        Contacto c1 = new Contacto();
        for (Contacto actual : this.agenda) {
            if(actual.getNombre().equalsIgnoreCase(c1.getNombre()) 
                    && actual.getApellidos().equalsIgnoreCase(c1.getApellidos())){
                yaExiste = true;
            }
        }
        if(yaExiste == false){
            this.agenda.add(c1);
        }else{
            System.out.println("Un contacto ya existe con ese nombre");
        }
    }
    
    public void verContactos(){
        for (Contacto actual : this.agenda) {
            System.out.println("Nombre: " + actual.getNombre());
            System.out.println("Apellidos: " + actual.getApellidos());
            System.out.println("Telefono: " + actual.getTelefono());
            System.out.println("Email: " + actual.getEmail());
            System.out.println("Edad: " + actual.getEdad());
            System.out.println("");
        }
    }
    
    public void mostrarContacto(Scanner datos){
        for (Contacto actual : this.agenda) {
            if(buscarContacto(datos)){
                System.out.println("Nombre: " + actual.getNombre());
                System.out.println("Apellidos: " + actual.getApellidos());
                System.out.println("Telefono: " + actual.getTelefono());
                System.out.println("Email: " + actual.getEmail());
                System.out.println("Edad: " + actual.getEdad());
            }
        }
    }
    
    public boolean buscarContacto(Scanner datos){
        boolean encontrado = false;
        try {
            System.out.println("Introduzca el nombre del contacto en cuestion: ");
            datos = new Scanner(System.in);
            String nombreContacto = datos.nextLine();
            System.out.println("Introduzca el apellido del contacto: ");
            String apellidoContacto = datos.nextLine();
            if(nombreContacto.equalsIgnoreCase("") || apellidoContacto.equalsIgnoreCase("")
                    || nombreContacto.equalsIgnoreCase(" ") || apellidoContacto.equalsIgnoreCase(" ")){
                throw new Exception();
            }
            for (Contacto actual : this.agenda) {
                if(nombreContacto.equalsIgnoreCase(actual.getNombre()) && apellidoContacto.equalsIgnoreCase(actual.getApellidos())){
                    encontrado = true;
                }
            }
            if(encontrado == false){
                System.out.println("Contacto no encontrado");
            }
        } catch (Exception e) {
            System.out.println("El nombre o el apellido son erroneos");
        }
        return encontrado;
    }
    
    public boolean eliminarContacto(Scanner datos){
        for (Contacto actual : this.agenda) {
            if(buscarContacto(datos)){
                this.agenda.remove(actual);
                return true;
            }
        }
        return false;
    }
    
    
    
}
