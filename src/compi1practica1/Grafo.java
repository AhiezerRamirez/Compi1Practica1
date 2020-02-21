/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compi1practica1;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

class Vertice {
    String estado;
    boolean acceptacion;
    Vertice(String label,boolean acceptacion) {
        this.estado = label;
        this.acceptacion=acceptacion;
    }
 
    // equals and hashCode
}
public class Grafo {
    private Map<Vertice, List<Vertice>> adjVertices;

    public Grafo() {
    }
    
    void addVertice(String estado,boolean acceptacion){
        adjVertices.putIfAbsent(new Vertice(estado,acceptacion),new ArrayList<>());
    }
    void addArista(String label1,String label2,boolean stado1,boolean stado2){
        Vertice v1=new Vertice(label1, stado1);
        Vertice v2=new Vertice(label2, stado2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }
    
    void Agregar(String estado1,boolean acceptacion1,String estado2,boolean acceptacion2,String arista){
        this.addVertice(estado1,acceptacion1);
        this.addVertice(estado2, acceptacion2);
        this.addArista(estado1, estado2, acceptacion1, acceptacion2);
    }
    void Agregar(String estado,boolean aceptacion){
        this.addVertice(estado,aceptacion);
    }
    
    List<Vertice> getadjVertices(String label,boolean stado){
        return adjVertices.get(new Vertice(label,stado));
    }
    
    Set<String> recorrido(Grafo grafo,String root){
        Set<String> visited = new LinkedHashSet<>();
        Stack<String> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {            
            String vertex = stack.pop();
            if (!visited.contains(vertex)) {
            visited.add(vertex);
            for (Vertice v : grafo.getadjVertices(vertex,true)) {              
                stack.push(v.estado);
                }
            }
        }
        return visited;
    }
}
