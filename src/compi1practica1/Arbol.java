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
import java.util.LinkedList;
import java.util.Queue;
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
    TablaSiguientes tablasiguientes;
    Queue<Estado> estadosPendientes=new LinkedList<>();
    Queue<Estado> estadosListos=new LinkedList<>();
    
    public Arbol(){
        this.tablasiguientes=new TablaSiguientes();
    }
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
    
    void obtenerHojas(Nodo root){
        
            if(root.getLeft()==null && root.getRight()==null){
                tablasiguientes.i.add(new tablaFinal(root.getI(),root.getLexema()));
                return;
            }
        obtenerHojas(root.getRight());
        if(root.getLeft()!=null)
        obtenerHojas(root.getLeft());
        
    }
    void sacarTablaSiguientes(Nodo root){
        if(root==null)
            return;
        sacarTablaSiguientes(root.getLeft());
        sacarTablaSiguientes(root.getRight());
        switch (root.getLexema()) {
            case ".":
                this.tablasiguientes.sigDi.add(new ColunmaSiguiente(root.getLeft().getUltimos(),root.getRight().getPrimeros()));
                //this.tablasiguientes.setSigDi(root.getRight().getPrimeros());
                break;
            case "*":
                this.tablasiguientes.getSigDi().add(new ColunmaSiguiente(root.getUltimos(), root.getPrimeros()));
                break;
            case "+":
                this.tablasiguientes.getSigDi().add(new ColunmaSiguiente(root.getUltimos(), root.getPrimeros()));
                break;
            default:
                break;
        }
    }
    
    void llenarTablaSiguientes(){
        try {
            PrintWriter archivo=new PrintWriter("TablaSiguientes.txt", "UTF-8");
            archivo.print("digraph G  {\n node [shape=record, fontname=\"Arial\"];\n");
            String codigo="set1 [label = \"{i ";
            this.tablasiguientes.sacarTabla();
            for (int i = 0; i < tablasiguientes.i.size(); i++) {
                codigo+="|"+tablasiguientes.i.get(i).lexema+" "+tablasiguientes.i.get(i).i;   
            }
            codigo+=" }| { Sig(i) ";
            for (int i = 0; i < tablasiguientes.i.size(); i++) {
                codigo+="| ";
                for (int j = 0; j < tablasiguientes.i.get(i).sig.size(); j++) {
                    codigo+=tablasiguientes.i.get(i).getSig().get(j);
                }
                
            }
            codigo+=" }\" ];\n}";
            archivo.print(codigo);
            archivo.close();
            codigo="";
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Arbol.class.getName()).log(Level.SEVERE, null, ex);
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
    //----------------------------------------TABLA DE ESTADOS INICIA------------------------------------------
    void sacarEstados(Nodo root){
        ArrayList primerosderoot=root.getPrimeros();
        estadosPendientes.add(new Estado("S0",root.getPrimeros()));
        System.out.println(root.getPrimeros().toString()+" Los primeros de root");
        while (!estadosPendientes.isEmpty()) {            
            Estado estadoAux=estadosPendientes.poll();
            for (int i = 0; i < estadoAux.getTransisiones().size(); i++) {
                for (int j = 0; j < this.tablasiguientes.getI().size(); j++) {
                    if(this.tablasiguientes.getI().get(j).getI()==estadoAux.getTransisiones().get(i)){
                        if(this.tablasiguientes.getI().get(j).getSig().toString().equals(estadoAux.getTransisiones().toString())){
                            
                        }else{
                            estadosPendientes.add(new Estado("S"+Integer.toString(i+1), this.tablasiguientes.getI().get(j).getSig()));
                        }
                    }
                    
                }
            }
            estadosListos.add(estadoAux);
        }
        
        for (Estado item: estadosListos) {
		System.out.println(item.estado+" "+item.getTransisiones().toString());
            }
    }
    
    //-----------------------------------------TABLA DE ESTADOS TERMINA---------------------------------------
}

class TablaSiguientes{
    ArrayList<tablaFinal>i;
    ArrayList<ColunmaSiguiente> sigDi;

    public TablaSiguientes() {
        this.i=new ArrayList<>();
        this.sigDi=new ArrayList<>();
    }
    
    void sacarTabla(){
        for (int j = 0; j < this.i.size(); j++) {
            TreeSet<Integer> auxsiguientes=new TreeSet<>();
            tablaFinal auxtabla=this.i.get(j);
            for (int k = 0; k < this.sigDi.size() ; k++) {
                ColunmaSiguiente auxcolumna=this.sigDi.get(k);
                for (int l = 0; l < auxcolumna.LC1.size(); l++) {
                    if(auxtabla.i==auxcolumna.LC1.get(l)){
                        auxsiguientes.addAll(auxcolumna.FC2);
                    }
                }
            }
            ArrayList<Integer>auxsigya=new ArrayList<>(auxsiguientes);
            this.i.get(j).setSig(auxsigya);
        }
    }

    public ArrayList<tablaFinal> getI() {
        return i;
    }

    public void setI(ArrayList<tablaFinal> i) {
        this.i = i;
    }

    


    public ArrayList<ColunmaSiguiente> getSigDi() {
        return sigDi;
    }

    public void setSigDi(ArrayList<ColunmaSiguiente> sigDi) {
        this.sigDi = sigDi;
    }
    
    
}

class ColunmaSiguiente{
    
    ArrayList<Integer> LC1,FC2;

    public ColunmaSiguiente(ArrayList<Integer> lci,ArrayList<Integer> fc2) {
        this.LC1=lci;
        this.FC2=fc2;
    }

    public ArrayList<Integer> getLC1() {
        return LC1;
    }

    public void setLC1(ArrayList<Integer> LC1) {
        this.LC1 = LC1;
    }

    public ArrayList<Integer> getFC2() {
        return FC2;
    }

    public void setFC2(ArrayList<Integer> FC2) {
        this.FC2 = FC2;
    }
    
}

class tablaFinal{
    int i;
    String lexema;
    ArrayList<Integer> sig;

    public tablaFinal(int nodo,String lexeman) {
        this.i=nodo;
        this.lexema=lexeman;
        this.sig=new ArrayList<>();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public ArrayList<Integer> getSig() {
        return sig;
    }

    public void setSig(ArrayList<Integer> sig) {
        this.sig = sig;
    }
}

class Estado{
    String estado;
    ArrayList<Integer> transisiones;

    public Estado(String estado) {
        this.estado=estado;
        
    }
    public Estado(String estado, ArrayList<Integer> trans){
        this.estado=estado;
        this.transisiones=trans;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Integer> getTransisiones() {
        return transisiones;
    }

    public void setTransisiones(ArrayList<Integer> transisiones) {
        this.transisiones = transisiones;
    }
    
}