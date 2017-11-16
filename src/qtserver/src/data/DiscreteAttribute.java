package data;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Rappresentazione di un attributo contenente valori discreti
 *
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
class DiscreteAttribute extends Attribute implements Iterable {
    // per ogni stringa si ha un valore del dominio discreto.
    // I valori del dominio sono memorizzati in values seguendo
    // un ordine lessicografico
    //private String values[];
    private TreeSet<String> values;

    /**
     * Inizializza
     *
     * @param name   nome dell'attributo
     * @param index  identificativo dell'attributo
     * @param values dominio dell'attributo
     */
    DiscreteAttribute(String name, int index, String values[]) {
        super(name, index);
        //this.values = values;
        this.values = new TreeSet<>();
        for (int i = 0; i < values.length; i++)
            this.values.add(values[i]);
    }

    /**
     * Restituisce la dimensione di values
     *
     * @return dimensione di values
     */
    public int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Ottiene un iteratore adatto ad enumerare le stringhe contenute
     * presenti nell'insieme
     */
    public Iterator<String> iterator() {
        return values.iterator();
    }
}
