package data;

import java.util.Iterator;
import java.util.Set;

/**
 * Classe che contiene un insieme di oggetti formanti una singola tupla
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class Tuple implements java.io.Serializable {
    private Item[] tuple;

    Tuple(int size) {
        this.tuple = new Item[size];
    }

    /**
     * Ottiene il numero di oggetti contenuti nella tupla corrente
     * @return numero di oggetti
     */
    public int getLenght() {
        return this.tuple.length;
    }

    /**
     * Ottiene uno specifico oggetto dalla tupla
     * @param i indice nella tupla dell'ogggetto da ottenere
     * @return ogetto richiesot
     */
    public Item get(int i) {
        return this.tuple[i];
    }

    /**
     * Aggiunge un oggetto nella tupla corrente
     * @param c oggetto da aggiungere
     * @param i indice dove inserire l'elemento
     */
    void add(Item c, int i) {
        if (i < this.tuple.length)
            this.tuple[i] = c;
    }

    /**
     * Ottiene la distanza complessiva tra due tuple
     * @param obj tupla da confrontare
     * @return distanza complessiva
     */
    public double getDistance(Tuple obj) {
        double x = 0;
        for (int i = 0; (i < this.getLenght()) && (i < obj.getLenght()); i++)
            x += this.get(i).distance((Object) obj.get(i));
        return x;
    }

    public double avgDistance(Data data, Set<Integer> clusteredData) {
        double p = 0.0, sumD = 0.0;
        for (Iterator<Integer> iterator = clusteredData.iterator(); iterator.hasNext(); ) {
            double d = getDistance(data.getItemSet(iterator.next()));
            sumD += d;
        }
        p = sumD / clusteredData.size();
        return p;
    }
}

