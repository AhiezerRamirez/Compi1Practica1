
package compi1practica1;

import java.util.ArrayList;

public class AnalizadorLexico {
    int i,j,estado;
    String palabra, linea;
    char auxpalabra;
    ArrayList<Token> tokens=new ArrayList<>();
    ArrayList<Error> errores=new ArrayList<>();
    Core auxcore;
    public AnalizadorLexico(){}
    
    public ArrayList<Token> analizar(String[] cadena){
        estado=0;
        palabra="";
        for (i = 0; i < cadena.length; i++) {
            linea = cadena[i];
            for (j = 0; j < linea.length(); j++) {
                auxpalabra=linea.charAt(j);
                switch (estado){
                    case 0:
                        if(auxpalabra=='{')
                            tokens.add(new Token(Token.Tipo.SIGNO,"{",i,j,false));
                        else if(auxpalabra==':')
                            tokens.add(new Token(Token.Tipo.SIGNO,":",i,j,false));
                        else if(auxpalabra==';')
                            tokens.add(new Token(Token.Tipo.SIGNO,";",i,j,false));
                        else if(auxpalabra=='~')
                            tokens.add(new Token(Token.Tipo.SIGNO,"~",i,j,false));
                        else if(auxpalabra=='.')
                            tokens.add(new Token(Token.Tipo.SIGNO,".",i,j,true));
                        else if(auxpalabra=='}')
                            tokens.add(new Token(Token.Tipo.SIGNO,"}",i,j,false));
                        else if(auxpalabra=='*')
                            tokens.add(new Token(Token.Tipo.SIGNO,"*",i,j,true));
                        else if(auxpalabra=='|')
                            tokens.add(new Token(Token.Tipo.SIGNO,"|",i,j,true));
                        else if(auxpalabra=='+')
                            tokens.add(new Token(Token.Tipo.SIGNO,"+",i,j,true));
                        else if(auxpalabra==',')
                            tokens.add(new Token(Token.Tipo.SIGNO,",",i,j,false));
                        else if(auxpalabra=='?')
                            tokens.add(new Token(Token.Tipo.SIGNO,"?",i,j,true));
                        else if(auxpalabra=='/')
                            estado=1;
                        else if(auxpalabra=='-')
                            estado=3;
                        else if(auxpalabra=='<')
                            estado=4;
                        else if(auxpalabra=='"')
                            estado = 7;
                        else if(auxpalabra== '%')
                            estado = 9;
                        else if(Character.isLetter(auxpalabra)){
                            palabra+=auxpalabra;
                            estado=2;
                        }else if(Character.isDigit(auxpalabra)){
                            estado=11;
                            j--;
                        }
                        break;
                    case 1:                                                                 //Estado 1 para ir a comentarios una línea
                        if(auxpalabra=='/')
                            estado=10;
                        else
                            errores.add(new Error(Character.toString(auxpalabra), i, j));
                        break;
                    case 2:                                                                 //Estado 2  Para palabras o identificadores aceptacion
                        if(Character.isLetterOrDigit(auxpalabra)){
                            palabra+=auxpalabra;
                        }else if(auxpalabra=='_'){
                            palabra+=auxpalabra;
                        }else{
                            if(palabra.toLowerCase().equals("conj")){
                                tokens.add(new Token(Token.Tipo.RESERVADA, "CONJ", i, j,false));
                                estado=0;
                                palabra="";
                                j--;
                            }else{
                                tokens.add(new Token(Token.Tipo.IDENTIFICADOR, palabra, i, j,false));
                                estado=0;
                                palabra="";
                                j--;
                            }
                        }
                        break;
                    case 3:                                                                 //Para la flechita de asignar algo, aceptacion
                        if(auxpalabra == '>'){
                            tokens.add(new Token(Token.Tipo.SIGNO, "->", i, j,false));
                            estado=0;
                        }else{
                            errores.add(new Error(Character.toString(auxpalabra), i, j));
                            estado=0;
                        }
                        break;
                    case 4:                                                                 //Para los comentarios multininea
                        if(auxpalabra=='!'){
                            estado=5;
                        }else{
                            errores.add(new Error(Character.toString(auxpalabra),i,j));
                        }
                        break;
                    case 5:
                        if(auxpalabra =='!')
                            estado=6;
                        else
                            ;
                        break;
                    case 6:
                        if(auxpalabra == '>')
                            estado =0;
                        else
                            errores.add(new Error(Character.toString(auxpalabra), i, j));
                        estado =0;
                        break;
                    case 7:                                                                 //Para aceptar las cadenas
                        if(auxpalabra=='"')
                            estado=8;
                        else
                            palabra += auxpalabra;
                        break;
                    case 8:
                        tokens.add(new Token(Token.Tipo.CADENA, palabra, i, j,false));
                        palabra="";
                        estado=0;
                        j--;
                        break;
                    case 9:
                        if(auxpalabra== '%'){
                            tokens.add(new Token(Token.Tipo.SIGNO, "%%", i, j,false));
                            estado=0;
                        }else{
                            errores.add(new Error(Character.toString(auxpalabra), i, j));
                            estado =0;
                        }
                        break;
                    case 10:
                        if(j==linea.length()-1){
                            estado=0;
                        }
                        break;
                    case 11:
                        if(Character.isDigit(auxpalabra))
                            palabra+=auxpalabra;
                        else{
                            tokens.add(new Token(Token.Tipo.NUMERO, palabra, i, j,false));
                            estado=0;
                            j--;
                            palabra="";
                        }
                        break;
                }
            }
        }
        auxcore=new Core(tokens);
        auxcore.serparaComponentes();
        auxcore.hacerMetodoArbol();
        
        /*for (int k = 0; k < tokens.size(); k++) {
            Token auxtoken = tokens.get(k);
            System.out.println(auxtoken.tipo+" "+auxtoken.lexema);
            
        }*/
        return tokens;
    }
    
    public ArrayList<String>getRutas(){
        ArrayList<String> auxrutas=auxcore.getRutas();
        return auxrutas;
    }
    
    public ArrayList<Expresion>getExpresiones(){
        ArrayList<Expresion> auxrutas=auxcore.getExpresiones();
        return auxrutas;
    }
    
  
}
