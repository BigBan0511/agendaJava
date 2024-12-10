package agenda1daw;

import java.io.Serializable;
import java.util.Scanner;

public class Contacto implements Serializable{
    private String nombre;
    private String apellidos;
    private int telefono;
    private String email;
    private int edad;

    public Contacto() {
        Scanner datos = new Scanner(System.in);
        System.out.println("Vamos a introducir los datos del nuevo contacto");
        System.out.println("Introduzca el nombre del contacto");
        this.nombre = datos.nextLine();
        System.out.println("Introduzca los apellidos del contacto");
        this.apellidos = datos.nextLine();
        System.out.println("Introduzca el telefono del contacto");
        this.telefono = datos.nextInt();
        System.out.println("Introduzca el email del contacto");
        datos = new Scanner(System.in);
        this.email = datos.nextLine();
        System.out.println("Introduzca la edad del contacto");
        datos = new Scanner(System.in);
        this.edad = datos.nextInt();
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    
    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public int getEdad() {
        return edad;
    }
    
}
