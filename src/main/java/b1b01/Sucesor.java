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

    //El sucesor de un estado esta formado por la accion, el nuevo estado y 
//el costo de la accion
public class Sucesor {
    private String accion;
    private Estado estado;
    private double coste;

    public Sucesor(String accion, Estado estado, double coste) {
        this.accion = accion;
        this.estado = estado;
        this.coste = coste;
    }

    public String getAccion() {
        return accion;
    }

    public Estado getEstado() {
        return estado;
    }

    public double getCoste() {
        return coste;
    }

    @Override
    public String toString() {
        return "Sucesor{" + "accion=" + accion + ", estado=" + estado + ", coste=" + coste + '}';
    }    
    
}


    

