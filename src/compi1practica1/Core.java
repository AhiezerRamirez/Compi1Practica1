
package compi1practica1;

import java.util.ArrayList;

public class Core {
    private ArrayList<Token> tokens,auxtokens;
    ArrayList<Conjunto> conjuntos;
    ArrayList<Expresion> expresiones;
    ArrayList<Lexema> lexemas;
    
    Core(ArrayList<Token> tokens){
        this.tokens=tokens;
        this.auxtokens=new ArrayList<>();
        this.conjuntos=new ArrayList<>();
        this.expresiones=new ArrayList<>();
        this.lexemas=new ArrayList<>();
    }
    void serparaComponentes(){
        ArrayList<Token> auxconjutos=new ArrayList<>();                 //Para serparac conjuntos de toda la lista de tokens
        int auxExpresion=0;
        int auxLexemas=0;
        for (int i = 0; i < this.tokens.size(); i++) {
            if(this.tokens.get(i).lexema.toLowerCase().equals("conj")){
                while(!this.tokens.get(i).lexema.equals(";")){
                    auxconjutos.add(this.tokens.get(i));
                    i++;
                }
                auxconjutos.add(this.tokens.get(i));
                //System.out.println(i);
                auxExpresion=i+1;
            }
        }
        while (!this.tokens.get(auxExpresion).lexema.equals("%%")) {      //Para serparar las expresiones de toda la lista de tokens 
            Token auxtoken=this.tokens.get(auxExpresion);
            this.auxtokens.add(new Token(auxtoken.tipo, auxtoken.lexema, auxtoken.fila, auxtoken.columna,auxtoken.operador));
            auxExpresion++;
        }
        auxLexemas=auxExpresion+2;
        
        for (int i = auxLexemas; i < this.tokens.size(); i++) {                 //Deberia de separar los lexemas de entrada por nombre y cadena
            if(this.tokens.get(i).lexema.equals("->")){
                this.lexemas.add(new Lexema(this.tokens.get(i-1).lexema, this.tokens.get(i+1).lexema));
            }
        }
        
        for (int i = 0; i < auxconjutos.size(); i++) {                          //Para serparar el nombre de los conjuntos con sus elementos separados en un array
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
        for (int i = 0; i < auxtokens.size(); i++) {                            //Para serparar las expresiones en tokens con sus elementos tokens en array    
            if(this.auxtokens.get(i).lexema.equals("->")){
                Token auxtoken=this.auxtokens.get(i-1);i++;
                ArrayList<Token> auxlistaElementos=new ArrayList();
                while (!this.auxtokens.get(i).lexema.equals(";")) {                    
                    auxlistaElementos.add(this.auxtokens.get(i));
                    i++;
                }
                this.expresiones.add(new Expresion(auxtoken,auxlistaElementos));
            }
        }
        
        for (int i = 0; i < this.expresiones.size(); i++) {
            System.out.println(this.expresiones.get(i).nombre.lexema);
            for (int j = 0; j < this.expresiones.get(i).parametros.size(); j++) {
                System.out.print(this.expresiones.get(i).parametros.get(j).lexema);
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
