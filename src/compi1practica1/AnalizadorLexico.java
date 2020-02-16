
package compi1practica1;

import java.util.ArrayList;

public class AnalizadorLexico {
    int i,j,estado;
    String palabra, linea;
    char auxpalabra;
    ArrayList<Token> tokens=new ArrayList<>();
    ArrayList<Error> errores=new ArrayList<>();
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
                            tokens.add(new Token(Token.Tipo.SIGNO,"{",i,j));
                        else if(auxpalabra==':')
                            tokens.add(new Token(Token.Tipo.SIGNO,":",i,j));
                        else if(auxpalabra==';')
                            tokens.add(new Token(Token.Tipo.SIGNO,";",i,j));
                        else if(auxpalabra=='~')
                            tokens.add(new Token(Token.Tipo.SIGNO,"~",i,j));
                        else if(auxpalabra=='.')
                            tokens.add(new Token(Token.Tipo.SIGNO,".",i,j));
                        else if(auxpalabra=='}')
                            tokens.add(new Token(Token.Tipo.SIGNO,"}",i,j));
                        else if(auxpalabra=='*')
                            tokens.add(new Token(Token.Tipo.SIGNO,"*",i,j));
                        else if(auxpalabra=='|')
                            tokens.add(new Token(Token.Tipo.SIGNO,"|",i,j));
                        else if(auxpalabra=='_')
                            tokens.add(new Token(Token.Tipo.SIGNO,"_",i,j));
                        else if(auxpalabra=='"')
                            tokens.add(new Token(Token.Tipo.SIGNO,"\"",i,j));
                        else if(auxpalabra=='+')
                            tokens.add(new Token(Token.Tipo.SIGNO,"+",i,j));
                        else if(auxpalabra==',')
                            tokens.add(new Token(Token.Tipo.SIGNO,",",i,j));
                        else if(Character.isLetter(auxpalabra)){
                            palabra+=auxpalabra;
                            estado=1;
                        }else if(Character.isDigit(auxpalabra)){
                            estado=2;
                            j--;
                        }
                        break;
                    case 1:
                        if(Character.isLetterOrDigit(auxpalabra)){
                            palabra+=auxpalabra;
                        }else if(auxpalabra=='_'){
                            palabra+=auxpalabra;
                        }else{
                            if(palabra.toLowerCase().equals("conj")){
                                tokens.add(new Token(Token.Tipo.RESERVADA, "Conj", i, j));
                                estado=0;
                                palabra="";
                                j--;
                            }else{
                                tokens.add(new Token(Token.Tipo.RESERVADA, palabra, i, j));
                                estado=0;
                                palabra="";
                                j--;
                            }
                        }
                        break;
                    case 2:
                        if(Character.isDigit(auxpalabra)){
                            palabra+=auxpalabra;
                        }else if(auxpalabra=='.'){
                            palabra+=auxpalabra;
                            estado=3;
                            j--;
                        }else{
                            tokens.add(new Token(Token.Tipo.NUMERO, palabra, i, j));
                            estado=0;
                            j--;
                            palabra="";
                        }
                        break;
                    case 3:
                        if(Character.isDigit(auxpalabra)){
                            estado=2;
                            j--;
                        }else{
                            errores.add(new Error(Character.toString(auxpalabra), i, j));
                            estado=0;
                        }
                }
            }
        }
        for (int k = 0; k < tokens.size(); k++) {
            Token auxtoken = tokens.get(k);
            System.out.println(auxtoken.tipo+" "+auxtoken.lexema);
            
        }
        return null;
    }
    
}
