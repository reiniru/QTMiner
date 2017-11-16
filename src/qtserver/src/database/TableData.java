package database;

import database.TableSchema.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Astrazione per ottenere le informazioni dal database
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class TableData {

    private DbAccess db;

    /**
     * Inizializzazione
     * @param db specifica un databsae
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Ottiene il nome degli attributi
     * @param table nome della tabella la quale bisogna estrarre le informazioni
     * @return lista di campioni estratti
     * @throws SQLException se la connessione col database fallisce
     * @throws EmptySetException se il result set è vuoto
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
        LinkedList<Example> transSet = new LinkedList<Example>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);


        String query = "select distinct ";

        for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
            Column c = tSchema.getColumn(i);
            if (i > 0)
                query += ",";
            query += c.getColumnName();
        }
        if (tSchema.getNumberOfAttributes() == 0)
            throw new SQLException();
        query += (" FROM " + table);

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean empty = true;
        while (rs.next()) {
            empty = false;
            Example currentTuple = new Example();
            for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
                if (tSchema.getColumn(i).isNumber())
                    currentTuple.add(rs.getDouble(i + 1));
                else
                    currentTuple.add(rs.getString(i + 1));
            transSet.add(currentTuple);
        }
        rs.close();
        statement.close();
        if (empty) throw new EmptySetException("DB Error: empty set!");


        return transSet;

    }

    /**
     * Ottiene un insieme di oggetti rappresentanti le colonne
     * @param table tabella la quale estrarre le informazioni
     * @param column colonna da specificare
     * @return insieme di oggetti
     * @throws SQLException se la connessione col database fallisce
     */
    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
        Set<Object> valueSet = new TreeSet<Object>();
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);


        String query = "select distinct ";

        query += column.getColumnName();

        query += (" FROM " + table);

        query += (" ORDER BY " + column.getColumnName());


        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            if (column.isNumber())
                valueSet.add(rs.getDouble(1));
            else
                valueSet.add(rs.getString(1));

        }
        rs.close();
        statement.close();

        return valueSet;

    }

    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
        Statement statement;
        TableSchema tSchema = new TableSchema(db, table);
        Object value = null;
        String aggregateOp = "";

        String query = "select ";
        if (aggregate == QUERY_TYPE.MAX)
            aggregateOp += "max";
        else
            aggregateOp += "min";
        query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;


        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            if (column.isNumber())
                value = rs.getFloat(1);
            else
                value = rs.getString(1);

        }
        rs.close();
        statement.close();
        if (value == null)
            throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());

        return value;

    }
}
