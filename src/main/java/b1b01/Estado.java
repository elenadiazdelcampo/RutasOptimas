/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package b1b01;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author IsabelDM
 * Objetivos de la clase:
 *  -Lista de nodos a recorrer (ordenada por el ID del nodo)
 *  -Variables para los estados: Posición donde me encuentro (ID_OSM) y Lista de nodos que me quedan por visitar. 
 * 
 */

public class Estado {
    private Nodo actual;
    private List<Nodo> listaNodos;
    private String identificador;

    public Estado(Nodo actual, List<Nodo> listaNodos) {
        this.actual = actual;
        this.listaNodos = new ArrayList<Nodo>(listaNodos);
        Comparator<Nodo> nodeComparator = Comparator.comparing(Nodo::getId);
        Collections.sort(this.listaNodos,nodeComparator);
        this.identificador=getMD5(this.toString());
    }

    public Nodo getActual() {
        return actual;
    } 

    public List<Nodo> getListaNodos() {
        return listaNodos;
    }    
    
    public String getIdentificador() {
        return identificador;
    }    

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Estado other = (Estado) obj;
        if (!Objects.equals(this.identificador, other.identificador)) {
            return false;
        }
        return true;
    }    
    
    public String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
 
            while(hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public double getHeuristica () {
        double menor=100000;
        double distancia = 0;
        for (Nodo nodo : listaNodos) {
            distancia=actual.distanciaMetros(nodo);
              if (distancia<menor)
                menor=distancia;
        }
        return menor;
       // return distancia;
    }
    
    
    
    @Override
    public String toString() {
        return "Estoy en " + actual + ", y tengo que visitar " + listaNodos;
    }  
    
}
