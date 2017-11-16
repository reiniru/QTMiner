package data;

/**
 * Oggetto contenente dei valori discreti
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
class DiscreteItem extends Item {

    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Ottiene la distanzatra i due oggetti discreti
     * @return 0 se sono uguali, 1 se sono distanti tra loro
     */
    public double distance(Object a) {
        return this.equals(a) == true ? 0 : 1;
    }

    /**
     * Ottiene un hash potenzialmente univoco dall'oggetto corrente
     * @return hash a 32 bit
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getValue() == null) ? 0 : this.getValue().hashCode());
        return result;
    }

    /**
     * Controlla se l'oggetto corrente è uguale all'oggetto da confrontare
     * @return true se l'oggetto è uguale, altrimenti false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DiscreteItem other = (DiscreteItem) obj;
        if (this.getAttribute().getName().compareTo(other.getAttribute().getName()) != 0)
            return false;
        if (this.getValue() == null) {
            if (other.getValue() != null)
                return false;
        } else if (!this.getValue().equals(other.getValue()))
            return false;
        return true;
    }
}
