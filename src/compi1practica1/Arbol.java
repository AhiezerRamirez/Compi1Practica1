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
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

class Nodo{
    Nodo left,right,parent;
    String lexema;
    int i;
    String nombre;
    Boolean anulable;
    ArrayList<Integer> primeros, ultimos;
    Nodo(String lexema){
        this.lexema=lexema;
        this.left=null;
        this.right=null;
        this.parent=null;
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
                t1.parent=t;
                t2.parent=t;
                t.nombre="a"+contador;
                st.push(t);
            }else if(prefix.get(i).lexema.equals("*")||prefix.get(i).lexema.equals("+")||prefix.get(i).lexema.equals("?")){
                contador++;
                t=new Nodo(prefix.get(i).lexema);
                if(prefix.get(i).lexema.equals("*")||prefix.get(i).lexema.equals("?"))
                    t.anulable=true;
                else
                    t.anulable=false;
                t1=st.pop();
                t.right=t1;
                t1.parent=t;
                t.nombre="a"+contador;
                st.push(t);
            }
        }
        Nodo t22=new Nodo("#");
        t22.setAnulable(false);
        t22.setI(contador+1);
        t22.primeros.add(contador+1);
        t22.ultimos.add(contador+1);
        contador++;
        t22.nombre="a"+contador;
        t=st.peek();
        Nodo t32=new Nodo(".");
        t32.setRight(t22);
        t32.setLeft(t);
        t22.parent=t32;
        t.parent=t32;
        contador++;
        t32.nombre="a"+contador;
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
            }
            marckNullable(t.left);
            marckNullable(t.right);
        }
    }
    
    void ponerPrimeros(Nodo t){
        
        if(t==null){
           return; 
        }
        ponerPrimeros(t.left);
        ponerPrimeros(t.right);
            if(t.lexema.equals(".")){
                if(t.left.anulable==true){
                TreeSet<Integer> auxarraylist=new TreeSet();
                auxarraylist.addAll(t.left.getPrimeros());
                auxarraylist.addAll(t.right.getPrimeros());
                ArrayList<Integer> aux=new ArrayList<>(auxarraylist);
                t.setPrimeros(aux);
                }else
                    t.setPrimeros(t.getLeft().getPrimeros());
            }else if(t.lexema.equals("|")){
                TreeSet<Integer> auxarraylist=new TreeSet();
                auxarraylist.addAll(t.left.getPrimeros());
                auxarraylist.addAll(t.right.getPrimeros());
                ArrayList<Integer> aux=new ArrayList<>(auxarraylist);
                t.setPrimeros(aux);
            }else if(t.getLexema().equals("*")){
                t.setPrimeros(t.getRight().getPrimeros());
            }else if(t.getLexema().equals("+")){
                t.setPrimeros(t.getRight().getPrimeros());
            }else if(t.getLexema().equals("?")){
                t.setPrimeros(t.getRight().getPrimeros());
            }
    }
    
    void ponerUltimos(Nodo t){
        
        if(t==null){
           return; 
        }
        ponerUltimos(t.left);
        ponerUltimos(t.right);
            if(t.lexema.equals(".")){
                if(t.right.anulable==true){
                TreeSet<Integer> auxarraylist=new TreeSet();
                auxarraylist.addAll(t.left.getUltimos());
                auxarraylist.addAll(t.right.getUltimos());
                ArrayList<Integer> aux=new ArrayList<>(auxarraylist);
                t.setUltimos(aux);
                }else
                    t.setUltimos(t.getRight().getUltimos());
            }else if(t.lexema.equals("|")){
                TreeSet<Integer> auxarraylist=new TreeSet();
                auxarraylist.addAll(t.left.getUltimos());
                auxarraylist.addAll(t.right.getUltimos());
                ArrayList<Integer> aux=new ArrayList<>(auxarraylist);
                t.setUltimos(aux);
            }else if(t.getLexema().equals("*")){
                t.setUltimos(t.getRight().getUltimos());
            }else if(t.getLexema().equals("+")){
                t.setUltimos(t.getRight().getUltimos());
            }else if(t.getLexema().equals("?")){
                t.setUltimos(t.getRight().getUltimos());
            }
    }
    
    String s="";
    void toDot(Nodo root){
        if(root.left!=null){
            s+=root.nombre+"[ label= \""+root.lexema+" \\n "+root.getAnulable()+" \\n Pri."+root.getPrimeros().toString()+" \\n Ult."+root.getUltimos().toString()+"\"];\n";
            s+=root.nombre+" -> " + root.left.nombre+ ";\n";
            toDot(root.getLeft());
        }else{
            s+=root.nombre+"[ label= \""+root.lexema+" \\n "+root.getAnulable()+" \\n Pri."+root.getPrimeros().toString()+" \\n Ult."+root.getUltimos().toString()+"\"];\n";
            s+=root.parent.nombre +" -> "+root.nombre+";\n";
        }
        
        if(root.right!=null){
            s+=root.nombre+"[ label= \""+root.lexema+" \\n "+root.getAnulable()+" \\n Pri."+root.getPrimeros().toString()+" \\n Ult."+root.getUltimos().toString()+"\"];\n";
            System.out.println(root.getPrimeros().toString());
            s+=root.nombre+" -> " + root.right.nombre+ ";\n";
            toDot(root.getRight());
        }else{
            s+=root.nombre+"[ label= \""+root.lexema+" \\n "+root.getAnulable()+" \\n Pri."+root.getPrimeros().toString()+" \\n Ult."+root.getUltimos().toString()+"\"];\n";
            s+=root.parent.nombre +" -> "+root.nombre+";\n";
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
            else{
                toDot(root);
            }
            writer.print(s);
            writer.print("\n}");
            //codigo="";
            writer.close();
            
            
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}
