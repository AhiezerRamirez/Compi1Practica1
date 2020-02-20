
package compi1practica1;

import java.util.ArrayList;


public class Compi1Practica1 {

    public static void main(String[] args) {
        // TODO code application logic here
        String cadenaprueba=".L*|LD";
        char auxchar[]=cadenaprueba.toCharArray();
        ArrayList<Token> array=new ArrayList<>();
        array.add(new Token(Token.Tipo.SIGNO, Character.toString(auxchar[0]), 2, 1, true));
        array.add(new Token(Token.Tipo.IDENTIFICADOR, Character.toString(auxchar[1]), 2, 1, false));
        array.add(new Token(Token.Tipo.SIGNO, Character.toString(auxchar[2]), 2, 1, true));
        array.add(new Token(Token.Tipo.SIGNO, Character.toString(auxchar[3]), 2, 1, true));
        array.add(new Token(Token.Tipo.SIGNO, Character.toString(auxchar[4]), 2, 1, false));
        array.add(new Token(Token.Tipo.SIGNO, Character.toString(auxchar[5]), 2, 1, false));
        
        Arbol arbol=new Arbol();
        Nodo root=arbol.construirArbol(array);
        arbol.marckNullable(root);
        arbol.ponerPrimeros(root);
        arbol.ponerUltimos(root);
        arbol.obtenerHojas(root);
        arbol.sacarTablaSiguientes(root);
        arbol.llenarTablaSiguientes();
        arbol.Graficar(root);
        System.out.println(root.getLeft().getPrimeros().toString()+ root.left.lexema+" <- "+ root.lexema +root.getPrimeros().toString()+" -> "+root.right.lexema);
    }
    
}
