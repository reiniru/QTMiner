package database;

import java.util.ArrayList;
import java.util.List;


/**
 * Campione, contenitore di oggetti
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class Example implements Comparable<Example> {
    private List<Object> example = new ArrayList<Object>();

    /**
     * Aggiunge un oggetto al campione corrente
     * @param o oggetto da aggiungere
     */
    void add(Object o) {
        example.add(o);
    }

    /**
     * Ottiene un oggetto specifico dal campione corrente
     * @param i indice dell'oggetto da ritornare
     * @return oggetto specificato dal campione
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * Compara due campioni
     * @return 0 se i due campioni sono uguali
     */
    public int compareTo(Example ex) {

        int i = 0;
        for (Object o : ex.example) {
            if (!o.equals(this.example.get(i)))
                return ((Comparable) o).compareTo(example.get(i));
            i++;
        }
        return 0;
    }

    /**
     * Lista degli oggetti contenuti nel campione
     */
    public String toString() {
        String str = "";
        for (Object o : example)
            str += o.toString() + " ";
        return str;
    }

}