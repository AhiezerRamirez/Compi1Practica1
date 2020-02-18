/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi1practica1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

class Nodo{
    Nodo left,right;
    String lexema;
    int i;
    String nombre;
    Boolean anulable;
    ArrayList<Integer> primeros, ultimos;
    Nodo(String lexema){
        this.lexema=lexema;
        this.left=null;
        this.right=null;
        this.primeros=new ArrayList<>();
        this.ultimos=new ArrayList<>();
    }

    public Nodo getLeft() {
        return left;
    }

    public void setLeft(Nodo left) {
        this.left = left;
    }

    public Nodo getRight() {
        return right;
    }

    public void setRight(Nodo right) {
        this.right = right;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Boolean getAnulable() {
        return anulable;
    }

    public void setAnulable(Boolean anulable) {
        this.anulable = anulable;
    }

    public ArrayList<Integer> getPrimeros() {
        return primeros;
    }

    public void setPrimeros(ArrayList<Integer> primeros) {
        this.primeros = primeros;
    }

    public ArrayList<Integer> getUltimos() {
        return ultimos;
    }

    public void setUltimos(ArrayList<Integer> ultimos) {
        this.ultimos = ultimos;
    }
    
}
public class Arbol {
    Nodo construirArbol(ArrayList<Token> prefix){
        Stack <Nodo> st=new Stack();
        Nodo t,t1,t2;
        int contador=0;
        for (int i = prefix.size()-1; i >= 0; i--) {
            if(prefix.get(i).operador==false){
                contador++;
                t=new Nodo(prefix.get(i).lexema);
                t.setAnulable(false);
                t.setI(contador);
                t.primeros.add(contador);
                t.ultimos.add(contador);
                t.nombre="a"+contador;
                st.push(t);
            }else if(prefix.get(i).lexema.equals(".")||prefix.get(i).lexema.equals("|")){
                contador++;
                t=new Nodo(prefix.get(i).lexema);
                t1=st.pop();
                t2=st.pop();
                t.right=t2;
                t.left=t1;
                t.nombre="a"+contador;
                st.push(t);
            }else if(prefix.get(i).lexema.equals("*")||prefix.get(i).lexema.equals("+")||prefix.get(i).lexema.equals("?")){
                contador++;
                t=new Nodo(prefix.get(i).lexema);
                t1=st.pop();
                t.right=t1;
                t.nombre="a"+contador;
                st.push(t);
            }
        }
        Nodo t22=new Nodo("#");
        t22.setAnulable(false);
        t22.primeros.add(contador+1);
        t22.ultimos.add(contador+1);
        t=st.peek();
        Nodo t32=new Nodo(".");
        t32.setRight(t22);
        t32.setLeft(t);
        st.pop();
        return t32;
    }
    
    void marckNullable(Nodo t){
        if(t!=null){
            if(t.lexema.equals(".")){
                if(t.right.anulable==true&&t.left.anulable==true){
                 t.setAnulable(true);
                }else
                    t.setAnulable(false);
            }else if(t.lexema.equals("|")){
                if(t.right.getAnulable()==false&&t.left.getAnulable()==false)
                    t.setAnulable(false);
                else
                    t.setAnulable(true);
            }else if(t.right.getLexema().equals("?")){
                t.setAnulable(true);
            }else if(t.getLexema().equals("*"))
                t.setAnulable(true);
            else if(t.getLexema().equals("+"))
                t.setAnulable(false);
            marckNullable(t.left);
            marckNullable(t.right);
        }
    }
    String codigo;
    
    private void dibujar3(Nodo root){
        
        if(root!=null){
            codigo+=root.nombre+"[label=\""+root.getLexema()+"\"] \n";
            codigo+=root.nombre+" -> ";
            dibujar3(root.getLeft());
        }
        if(root!=null){
            codigo+=root.nombre+"[label=\""+root.getLexema()+"\"] \n";
            codigo+=root.nombre+" -> ";
            dibujar3(root.getRight());
        }
    }
    
    void Graficar(Nodo root){
        PrintWriter writer;
        try {
            writer = new PrintWriter("Arbol.txt", "UTF-8");
            writer.println("digraph g{");
            if(root==null)
                writer.print("\n");
            if(root.left==null && root.right==null)
                writer.print("\""+root.getLexema()+"\"");
            else
                dibujar3(root);
            
            writer.print(codigo);
            writer.print("\n}");
            codigo="";
            writer.close();
            
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
