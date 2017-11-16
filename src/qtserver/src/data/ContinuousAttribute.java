package data;

/**
 * Rappresentazione di un attributo contenente valori continui
 *
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
class ContinuousAttribute extends Attribute {

    // definizioni estremi dell'intervallo di valori
    private double max;
    private double min;

    /**
     * Invoca il costruttore della classe madre ed inizializza
     * i membri aggiunti per estensione
     *
     * @param name  nome dell'attributo
     * @param index identificativo dell'attributo
     * @param min   minimo valore che si può assumere
     * @param max   massimo valore che si può assumere
     */
    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * Calcola e restituisce il valore normalizzato del
     * parametro passato in input. La normalizzazione ha
     * come codominio l'intervallo [0, 1].
     *
     * @param value valore dell'attributo da normalizzare
     * @return valore normalizzato
     */
    double getScaledValue(double value) {
        return (value - min) / (max - min);
    }
}
