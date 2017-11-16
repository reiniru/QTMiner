package data;

/**
 * Rappresenta un oggetto contenente la coppia attributo-valore.
 *
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
class ContinuousItem extends Item {
    ContinuousItem(ContinuousAttribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Ottiene la distanza tra l'oggetto corrente e l'oggetto specificato
     *
     * @param a oggetto contenente il valore dal quale calcolare la distanza
     * @return distanza tra i due oggetti
     */
    public double distance(Object a) {
        ContinuousItem ci = (ContinuousItem) a;
        ContinuousAttribute ca = (ContinuousAttribute) this.attribute;
        return Math.abs(ca.getScaledValue((double) this.value) - ca.getScaledValue((double) ci.getValue()));
    }
}
