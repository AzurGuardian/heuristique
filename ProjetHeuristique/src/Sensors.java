/**
 * Created by abrussia on 12/03/18.
 */
public class Sensors {
    int numero;
    double duree_vie;
    String areas[];

    public Sensors(int n,double dv,String a[] ){
        numero = n;
        duree_vie=dv;
        areas=a;
    }
    public Sensors(int n ,double dv ){
        numero=n;
        duree_vie=dv;
    }

    public double getDuree_vie() {
        return duree_vie;
    }

    public void setDuree_vie(double duree_vie) {
        this.duree_vie = duree_vie;
    }

    public String[] getAreas() {
        return areas;
    }

    public void setAreas(String[] areas) {
        this.areas = areas;
    }

    public int getNumero() {
        return numero;
    }

}
