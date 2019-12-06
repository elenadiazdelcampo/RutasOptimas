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
/*una arista esta definida por el nodo inicial, el nodo final, el nombre de la calle 
y la longitud de la arista
*/
public class Arista {
   long ninicial;
   long nfinal;
   String ncalle;
   double longitud;

    public Arista(long ninicial, long nfinal, String ncalle, double longitud) {
        this.ninicial = ninicial;
        this.nfinal = nfinal;
        this.ncalle = ncalle;
        this.longitud = longitud;
    }

    public long getNinicial() {
        return ninicial;
    }

    public void setNinicial(long ninicial) {
        this.ninicial = ninicial;
    }

    public long getNfinal() {
        return nfinal;
    }

    public void setNfinal(long nfinal) {
        this.nfinal = nfinal;
    }

    public String getNcalle() {
        return ncalle;
    }

    public void setNcalle(String ncalle) {
        this.ncalle = ncalle;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Arista{" + "ninicial=" + ninicial + ", nfinal=" + nfinal + ", ncalle=" + ncalle + ", longitud=" + longitud + '}';
    }

}
