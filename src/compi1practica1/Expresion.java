
package compi1practica1;

import java.util.ArrayList;

public class Expresion {
    Token nombre;
    ArrayList<Token> parametros;

    public Expresion(Token nombre, ArrayList<Token> parametos) {
        this.nombre = nombre;
        this.parametros=parametos;
    }

    public Token getNombre() {
        return nombre;
    }

    public void setNombre(Token nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Token> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Token> parametros) {
        this.parametros = parametros;
    }
    
    
}
