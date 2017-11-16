package database;

/**
 * Restituisce un'eccezione nel caso il result set sia vuoto.
 *
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class EmptySetException extends Exception {

    private String message;

    EmptySetException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
