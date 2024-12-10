package agenda1daw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Usuarios {
    private Scanner datos = new Scanner(System.in);
    private String nombre;
    private String contraseña;
    private String ruta;
    private FileWriter f1;
    private File usuarios;
    private BufferedWriter b1;
    private int contadorFallosGeneral;
    private boolean inicioSesionIncorrecto = false;

    public Usuarios() {
        this.nombre = "";
        this.contraseña = "";
        this.ruta = "Usuarios\\usuarios.txt";
        this.usuarios = new File(this.ruta);
        this.contadorFallosGeneral = 0;
    }
    
    public void crearUsuario(){
        boolean valido = true;
        do{
            System.out.println("Introduzca el nombre de usuario: ");
            this.nombre = this.datos.nextLine();
            System.out.println("Introduzca la clave de usuario: ");
            this.contraseña = this.datos.nextLine();
            if(this.nombre.contains(" ") || this.contraseña.contains(" ")){
                valido = false;
                System.out.println("Ni el nombre de usuario ni la contraseña pueden contener espacios");
            }else{
                if(yaExisteUsuario(this.nombre)){
                    System.out.println("Este usuario ya existe, introduzca otro");
                    valido = false;
                }else{
                    valido = true;  
                }
            }
        }while(valido == false);
        try {
            this.f1 = new FileWriter(this.usuarios, true);
            this.b1 = new BufferedWriter(this.f1);
            this.b1.write(this.nombre + " " + this.contraseña + " ");
            this.b1.close();
            this.f1.close();
        } catch (IOException e) {
            System.out.println("Archivo no creado");
        }
    }
    
    public boolean yaExisteUsuario(String nombreUsuario){
        boolean yaExiste = false;
        FileReader fr1;
        try {
            fr1 = new FileReader(this.usuarios);
            BufferedReader br1 = new BufferedReader(fr1);
            String linea = br1.readLine();
            if(linea != null){
                String [] separado = linea.split(" ");
                String[] usu = new String[separado.length];
                for (int x = 0; x < separado.length; x++) {
                    if(x%2 == 0){
                        usu[x] = separado[x];
                    }
                }
                for (int x = 0; x < separado.length; x++) {
                    if(nombreUsuario.equals(usu[x])){
                        yaExiste = true;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no existe");
        } catch (IOException e){
            System.out.println("Error");
        }
        return yaExiste;
    }
    
    public boolean iniciarSesion(){
        int contadorFallosConUsuarioBien = 0;
        boolean usuariosNulos = false;
        boolean sesionCorrecta = false;
        boolean valido = true;
        do {            
            System.out.println("Introduzca su nombre de usuario: ");
            String usuarioPuesto = this.datos.nextLine();
            System.out.println("Introduzca la clave para este usuario: ");
            String contra = this.datos.nextLine();
            if(usuarioPuesto.contains(" ") || contra.contains(" ")){
                valido = false;
                System.out.println("Ni el nombre de usuario ni la contraseña pueden contener espacios");
            }else{
                valido = true;
                try {
                    FileReader fr1 = new FileReader(this.usuarios);
                    BufferedReader br1 = new BufferedReader(fr1);
                    String linea = br1.readLine();
                    if(linea == null){
                        throw new Exception();
                    }
                    String [] separado = linea.split(" ");
                    String[] usu = new String[separado.length];
                    String[] contraseñas = new String[separado.length];
                    for (int x = 0; x < separado.length; x++) {
                        if(x%2 == 0){
                            usu[x] = separado[x];
                        }else{
                            contraseñas[x-1] = separado[x];
                        }
                    }
                    for (int x = 0; x < separado.length; x++) {
                        if(usuarioPuesto.equals(usu[x]) && contra.equals(contraseñas[x])){
                            System.out.println("Sesion iniciada");
                            sesionCorrecta = true;
                            this.nombre = usuarioPuesto;
                            cargarAgenda();
                        }else if(usuarioPuesto.equals(usu[x]) && !contra.equals(contraseñas[x])){
                            contadorFallosConUsuarioBien++;
                        }
                    }
                    if(sesionCorrecta == false){
                        System.out.println("Usuario o contraseña incorrectos");
                        this.contadorFallosGeneral++;
                        if(contadorFallosGeneral == 3){
                            valido = true;
                            sesionCorrecta = true;
                        }
                        if(contadorFallosConUsuarioBien == 3){
                            eliminarUsuario(usuarioPuesto);
                        }
                    }
                    br1.close();
                    fr1.close();
                } catch (IOException e) {
                    System.out.println("Archivo no encontrado");
                } catch (Exception e){
                    System.out.println("No puedes iniciar sesion si no hay usuarios creados");
                    usuariosNulos = true;
                    sesionCorrecta = true;
                    valido = true;
                }
            }
        } while (valido == false || sesionCorrecta == false);
        return usuariosNulos;
    }
    
    public void eliminarUsuario(String nombreUsuario) {
        File archivoDat = new File("Usuarios\\Agendas\\agenda" + nombreUsuario + ".dat");
        if (archivoDat.exists()) {
            archivoDat.delete();
            System.out.println("Archivo .dat eliminado correctamente.");
        } else {
            System.out.println("El archivo .dat no existe.");
        }
    }
    
    public void crearAgenda(Agenda a1){
        try {
            FileOutputStream flujoSalida = new FileOutputStream("Usuarios\\Agendas\\agenda" + this.nombre + ".dat");
            ObjectOutputStream salvaObjeto = new ObjectOutputStream(flujoSalida);
            salvaObjeto.writeObject(a1);
            salvaObjeto.close();
            flujoSalida.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error de archivo");
        } catch (IOException e){
            System.out.println("No ha sido posible crear la agenda: " + e.getMessage());
        }
    }
    
    public Agenda cargarAgenda(){
        boolean noHayNa = false;
        String rutilla = "Usuarios\\Agendas\\agenda" + this.nombre + ".dat";
        Agenda agendita = null;
        try {
            FileInputStream flujoEntrada = new FileInputStream(rutilla);
            ObjectInputStream objetoEntrada = new ObjectInputStream(flujoEntrada);
            agendita = (Agenda)objetoEntrada.readObject();
            objetoEntrada.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se ha encontrado el archivo buscado " + e.getMessage());
        } catch(IOException e){
        } catch(ClassNotFoundException e){
            System.out.println("No se ha encontrado la agenda");
        }
        return agendita;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public int getContadorFallosGeneral() {
        return contadorFallosGeneral;
    }
    
    public boolean isInicioSesionIncorrecto() {
        return inicioSesionIncorrecto;
    }

    public void setInicioSesionIncorrecto(boolean inicioSesionIncorrecto) {
        this.inicioSesionIncorrecto = inicioSesionIncorrecto;
    }
    
}
