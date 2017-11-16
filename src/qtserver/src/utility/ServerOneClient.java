package utility;

import data.Data;
import data.EmptyDatasetException;
import database.EmptySetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;

import java.net.*;
import java.io.*;


/**
 * Classe che gestisce una singola connessione da parte di un client
 * @author Ciccariello Luciano, Palumbo Vito, Rosini Luigi
 */
class ServerOneClient extends Thread {
    private Socket socket;
    private mining.QTMiner kmeans;


    /**
     * Inizializza gli attributi socket, in ed out. Avvia il thread.
     * @param s
     * @throws IOException
     */
    ServerOneClient(Socket s) throws IOException {
        socket = s;

    }

    /**
     * Si occupa della ricezione sicura di un oggetto via socket
     * @param socket la quale ricevere l'oggetto
     * @return oggetto ricevuto dal socket specificato
     * @throws ClassNotFoundException nel caso la classe ricevuta non vi è riconosciuta
	 * @throws IOException nel caso si verifichi un errore in fase di lettura
     */
	private static Object readObject(Socket socket) throws ClassNotFoundException, IOException
	{
		Object o;
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		o = in.readObject();
		return o;
	}
	/**
     * Si occupa dell'invio sicuro di un oggetto via socket
     * @param socket la quale riceverà l'oggetto
	 * @param o oggetto da inviare
	 * @throws IOException nel caso si verifichi un errore in fase di scrittura
	 */
	private static void writeObject(Socket socket, Object o) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(o);
		out.flush();
	}

    /**
     * Riscrive il metodo run della superclasse Thread al fine di gestire le
     * richieste del client
     */
    public void run()
    {
    	while (socket.isConnected())
    	{
            try {
            	// il primo oggetto in ricezione sarà l'operazione da effettuare
                Object o = readObject(socket);
                // ci si aspetta che l'operazione sia un intero
                if (o instanceof Integer)
                {
                    switch ((Integer)o)
                    {
                        case 0: // STORE TABLE FROM DB
                            break;
                        case 1: // LEARNING FROM DB
                            learningFromDb(socket);
                            break;
                        case 2: // STORE CLUSTER IN FILE
                            learningFromDb(socket);
                            break;
                        case 3: // LEARNING FROM FILE
                            learningFromFile(socket);
                            break;
                        default:
                        	// Nel caso venga selezionata un'operazione non supportata, si esce
                            System.out.println("Operation " + o + " from " + socket + " not supported.\nThe connection will be closed.");
                            socket.close();
                            break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    /**
     * Si occupa di leggere il set da file e di inviarlo al client
     * @param socket del client
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void learningFromFile(Socket socket) throws IOException, ClassNotFoundException
    {
        String tableName = (String)readObject(socket);
		double radius = (Double)readObject(socket);
		// Il radius deve essere maggiore di 0
		if (radius <= 0.0)
		{
			writeObject(socket, "Radius must be greater than 0.");
			writeObject(socket, "IMG");
		}
		try
        {
			QTMiner qt = new QTMiner(tableName + "_" + radius + ".dmp");
            String result = qt.toString();
            writeObject(socket, "OK");
            writeObject(socket, result);
            writeObject(socket, "IMG");
			qt.getC().writePlot(socket);

        }
        catch (Exception e)
        {
            writeObject(socket, "File not found");
            writeObject(socket, "NO_IMG");
        }
    }
    
    /**
     * Si occupa di leggere il set dal database e di inviarlo al client
     * @param socket del client
     */
    private boolean learningFromDb(Socket socket)
    {
    	Object o;
    	try {
			o = readObject(socket);
			if (o instanceof String)
			{
				String tableName = (String)o;
				try {
					Data data = new Data(tableName);
					writeObject(socket, "OK");
					o = readObject(socket);
					if (o instanceof Double)
					{
						double radius = (Double)o;
						// Il radius deve essere maggiore di 0
						if (radius <= 0)
						{
							writeObject(socket, "Radius must be greater than 0.");
							writeObject(socket,"NO_IMG");
						}
						else
						{
							// Specifica il radius nel miner
	                        QTMiner qt = new QTMiner(radius);
                            try {
                            	// Tutto è pronto per computare le informazioni
								int numC = qt.compute(data);
								writeObject(socket, "OK");
								// Il client è pronto a ricevere il risultato
								writeObject(socket, new Integer(numC));
								writeObject(socket, qt.getC().toString(data));
								//Invio del grafico al client
								writeObject(socket,"IMG");
								qt.getC().populatePlot(data);
								qt.getC().writePlot(socket);
								// Ora che le informazioni sono state processate, salva una copia
								// del risultato in un file da richiamare poi successivamente
								qt.salva(tableName + "_" + radius + ".dmp");
								// Finito. Esce dalla funzione con successo.
							} catch (ClusteringRadiusException e) {
								writeObject(socket, "An invalid radius value was specified.");
								writeObject(socket,"NO_IMG");
							} catch (EmptyDatasetException e) {
								writeObject(socket, "Dataset is empty.");
								writeObject(socket,"NO_IMG");
							}
						}
					}
					else
					{
						writeObject(socket, "Expected a decimal value greater than 0.");
						writeObject(socket,"NO_IMG");
					}
				} catch (EmptySetException e) {
					writeObject(socket, "Table " + tableName + " empty or not found.");
					writeObject(socket,"NO_IMG");
				}
			}
			else
			{
				writeObject(socket, "Expected the name of table to process.");
				writeObject(socket,"NO_IMG");
			}
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
			return false;
		}
    	return true;
    }

}
