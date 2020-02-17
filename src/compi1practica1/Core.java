
package compi1practica1;

import java.util.ArrayList;

public class Core {
    ArrayList<Token> tokens;
    ArrayList<Conjunto> conjuntos;
    ArrayList<Expresion> expresiones;
    ArrayList<Lexema> lexemas;
    
    Core(ArrayList<Token> tokens){
        this.tokens=tokens;
        this.conjuntos=new ArrayList<>();
        this.expresiones=new ArrayList<>();
        this.lexemas=new ArrayList<>();
    }
    void serparaComponentes(){
        ArrayList<Token> auxconjutos=new ArrayList<>();                 //Para serparac conjuntos
        for (int i = 0; i < this.tokens.size(); i++) {
            if(this.tokens.get(i).lexema.toLowerCase().equals("conj")){
                while(!this.tokens.get(i).lexema.equals(";")){
                    auxconjutos.add(this.tokens.get(i));
                    i++;
                }
                auxconjutos.add(this.tokens.get(i));
            }
            if(this.tokens.get(i).tipo.IDENTIFICADOR.equals("IDENTIFICADOR") && this.tokens.get(i+1).lexema.equals("->")){
                Expresion auxexpresion=new Expresion(this.tokens.get(i));
                i++;
                
            }
        }
        for (int i = 0; i < auxconjutos.size(); i++) {
            if(auxconjutos.get(i).lexema.toLowerCase().equals("conj")){
                i++;i++;
                String auxnombre=auxconjutos.get(i).lexema; i++;i++;
                ArrayList<String> auxelementos=new ArrayList<>();
                while(!auxconjutos.get(i).lexema.equals(";")){
                    auxelementos.add(auxconjutos.get(i).lexema);
                    i++;
                }
                conjuntos.add(new Conjunto(auxnombre, auxelementos));
            }
        }
        
        
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public ArrayList<Conjunto> getConjuntos() {
        return conjuntos;
    }

    public void setConjuntos(ArrayList<Conjunto> conjuntos) {
        this.conjuntos = conjuntos;
    }

    public ArrayList<Expresion> getExpresiones() {
        return expresiones;
    }

    public void setExpresiones(ArrayList<Expresion> expresiones) {
        this.expresiones = expresiones;
    }

    public ArrayList<Lexema> getLexemas() {
        return lexemas;
    }

    public void setLexemas(ArrayList<Lexema> lexemas) {
        this.lexemas = lexemas;
    }
    
    
}
