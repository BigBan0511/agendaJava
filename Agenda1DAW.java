package agenda1daw;

public class Agenda1DAW {
    
    public static void main(String[] args) {
        Menu m1 = new Menu();
        Agenda a1 = new Agenda();
        a1 = m1.iniciar(a1);
        if(m1.getU1().isInicioSesionIncorrecto() == false){
            m1.opciones(a1);
        }
    }
    
}
