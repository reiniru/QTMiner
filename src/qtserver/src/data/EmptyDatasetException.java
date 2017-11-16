package data;

/**
 * Lanciato quando non si trova nessun dato nel set corrente
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class EmptyDatasetException extends Exception {
    @Override
    public String toString() {
        return "DataSet is Empty!.\n";
    }
}