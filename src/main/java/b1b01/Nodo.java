/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package b1b01;

/**
 *
 * @author fgalf
 */
//un nodo esta constituido por un id, una latitud y una longitud
public class Nodo {
    private long id;
    private double latitud;
    private double longitud;
   
    public Nodo(long id, double latitud, double longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;      
    }

    public Nodo(long id) {
        this.id = id;
    }    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
      
    @Override
    //Uso de hasCode() para buscar mas rapido en el contenido de la lista
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Nodo other = (Nodo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    
    
     public double distanciaMetros(Nodo nodo) {          
        double radioTierra = 6371000;//km (en el ejemplo de python es que lo da en radianes y tiene que hacer el cambio) 
        double dLat = Math.toRadians(nodo.getLatitud() - getLatitud());  
        double dLng = Math.toRadians(nodo.getLongitud() - getLongitud());  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(getLatitud())) * Math.cos(Math.toRadians(nodo.getLatitud()));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;     
        return Math.abs(distancia);  
    }  
    
    @Override
    public String toString() {       
        return "'" + id + "'";
    }

    /*@Override
    public String toString() {
        return "Nodo{" + "id=" + id + ", latitud=" + latitud + ", longitud=" + longitud + '}';
    }*/

}
