package mining;

import data.Data;
import data.Tuple;



import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Singolo cluster, gestisce l'insieme di valori e tuple
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
 class Cluster implements Iterable<Integer>, Comparable<Cluster>, java.io.Serializable {
    private Tuple centroid;


    //private ArraySet clusteredData;
    private Set<Integer> clusteredData;

	/*Cluster(){

	}*/

    Cluster(Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet<>();

    }

    Tuple getCentroid() {
        return centroid;
    }

    //return true if the tuple is changing cluster
    boolean addData(int id) {
        return clusteredData.add(id);

    }

    /**
     * verifica se una transazione è clusterizzata nell'array corrente
     * @param id da controllare
     * @return true se è contenuta
     */
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    /**
     * rimuove la tupla specificata
     * @param id della tupla da rimuovere
     */
    void removeTuple(int id) {
        clusteredData.remove(id);

    }

    /**
     * Ottiene la dimensione del cluster
     * @return dimensione del cluster come numero intero
     */
    int getSize() {
        return clusteredData.size();
    }

    /**
     * Compara due cluster
     * @param cluster da comparare
     * @return ritorna 1 se i due cluster sono differenti, altrimenti ritorna -1
     */
    public int compareTo(Cluster cluster) {
        return (clusteredData.size() != cluster.getSize()) == true ? 1 : -1;
    }

    /**
     * Ottiene un iteratore adatto ad enumerare i valori nel cluster
     */
    public Iterator<Integer> iterator() {
        return clusteredData.iterator();
    }

    public String toString() {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLenght(); i++)
            str += centroid.get(i);
        str += ")";
        return str;

    }

    String toString(Data data) {
        String str = "Centroid=(";
        for (int i = 0; i < centroid.getLenght(); i++)
            str += centroid.get(i) + " ";
        str += ")\nExamples:\n";

        Iterator<Integer> array = iterator();
        HashSet<Integer> set = new HashSet<Integer>();
        for (Iterator<Integer> i = iterator(); i.hasNext(); ) {
            Integer v = i.next();
            set.add(v);
            str += "[";
            for (int j = 0; j < data.getNumberOfExplanatoryAttributes(); j++)
                str += data.getAttributeValue(v, j) + " ";
            str += "] dist=" + getCentroid().getDistance(data.getItemSet(v)) + "\n";
        }

        str += "AvgDistance=" + getCentroid().avgDistance(data, set) + "\n";
        return str;

    }
}