package data;

/**
 * Generico oggetto, descritto da l'attributo e valore associati
 *
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
abstract class Item implements java.io.Serializable {
    Attribute attribute;
    Object value;

    /**
     * Istanza un nuovo oggetto
     *
     * @param attribute attributo dell'oggetto
     * @param value     valore dell'oggetto
     */
    Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Ottiene l'attributo associato all'oggetto
     *
     * @return attributo
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Ottiene il valore associato all'oggetto
     *
     * @return valore
     */
    public Object getValue() {
        return value;
    }

    /**
     * Ottiene il valore associato come stringa
     *
     * @return getValue() come stringa
     */
    public String toString() {
        return value.toString();
    }

    public abstract double distance(Object a);
}
