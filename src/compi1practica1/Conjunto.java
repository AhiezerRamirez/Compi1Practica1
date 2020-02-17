
package compi1practica1;

import java.util.ArrayList;

public class Conjunto {
    String nombre;
    ArrayList<String> elementos;
    
    Conjunto(String nombre,ArrayList<String> elementos){
        this.nombre=nombre;
        this.elementos=elementos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<String> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<String> elementos) {
        this.elementos = elementos;
    }
    
}
