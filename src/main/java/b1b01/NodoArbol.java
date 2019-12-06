/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;

/**
 *
 * @author User
 */
/**
 *
 * @author IsabelDM
 * Estructura con lo necesario para trabajar con el árbol de búsqueda (no se si nos vale lo que tenemos ya del grafo) 
 */
public class NodoArbol implements Comparable<NodoArbol>{
    private NodoArbol padre; //acceso al padre
    private Estado estado;
    private double coste;
    private String accion;
    private int p;//profundidad
    private double f;//valor por el que se ordena la frontera

    
     public NodoArbol(NodoArbol padre, Estado estado, double coste, String accion, int p, double f) {
        this.padre = padre;
        this.estado = estado;
        this.coste = coste;
        this.accion = accion;
        this.p = p;
        this.f = f;
    }

    public NodoArbol getPadre() {
        return padre;
    }

    public void setPadre(NodoArbol padre) {
        this.padre = padre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "NodoArbol{" + "padre=" + padre + ", estado=" + estado + ", coste=" + coste + ", accion=" + accion + ", p=" + p + ", f=" + f + '}';
    }

       
    // método para ordenar por F en una cola con prioridad
    public int compareTo(NodoArbol nodo) {
        int r = 0;
        if (nodo.getF()< getF())
            r = +1;
        else
        if (nodo.getF() > getF())
            r = -1;
        return r;
    }
}

