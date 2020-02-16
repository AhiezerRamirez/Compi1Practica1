
package compi1practica1;

public class Token {
    public String lexema;
    public int fila, columna;
    public Tipo tipo;
    
    public enum Tipo{
        SIGNO,NUMERO,IDENTIFICADOR,RESERVADA,CADENA
    }
    public Token(Tipo tipo,String lexema,int fila, int colunma){
        this.tipo=tipo;
        this.lexema=lexema;
        this.fila=fila;
        this.columna=colunma;
    }
}
