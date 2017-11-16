package mining;

import data.Data;
import utility.ClusterPlot;

import java.net.Socket;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Insieme di cluster
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
public class ClusterSet implements Iterable<Cluster>, java.io.Serializable {
    private Set<Cluster> C;
    private ClusterPlot cplot;

    /**
     * Crea un insieme vuoto di cluster
     */
    ClusterSet() {

        C = new TreeSet<Cluster>();
        cplot = new ClusterPlot("Cluster Plot","distance","#cluster",500,220);
    }

    /**
     * Aggiunge un cluster all'insieme
     * @param c
     */
    void add(Cluster c) {
        C.add(c);
    }

    /**
     * Ottiene un iteratore adatto ad enumerare i cluster
     * presenti nell'insieme
     */
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }

    /**
     * Ottiene una stringa contenenti i singoli cluster concatenati
     * sotto forma di testo.
     * @return stringa di cluster
     */
    public String toString() {
        String x = "";
        Iterator<Cluster> it = C.iterator();
        while (it.hasNext()) {
            x += it.next().toString();
            x += '\n';
        }
        return x;
    }

    /**
     * Ottiene una stringa contenenti i singoli cluster concatenati
     * sotto forma di testo. I diversi cluster verranno elaborati
     * in base al Data specificaot
     * @param data specificato per i cluster
     * @return stringa di cluster
     */
    public String toString(Data data) {
        String str = "";
        Iterator<Cluster> it = C.iterator();
        for (int i = 0; it.hasNext(); i++) {
            Cluster c = it.next();
            if (c != null) {
                str += i + ":" + c.toString(data) + "\n";
            }
        }
        return str;
    }

   public void populatePlot(Data data)
    {
        Iterator<Cluster> it = this.iterator();


        while(it.hasNext())
        {
            Cluster c = it.next();
            Iterator<Integer> i = c.iterator();
                while(i.hasNext())
                {
                    Integer v = i.next();
                    cplot.AddData(c.getCentroid().getDistance(data.getItemSet(v)), 1);
                }
        }
    }

    public void writePlot(Socket s)
    {
        this.cplot.WritePlot(s);
    }
}