import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * a class for manage network in game
 * and make a server client connection and share data
 * @author Mohammad Mighani
 */
public class MultiPlayer {
    private boolean server;
    private String host;
    private static Socket socket;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;

    /**
     * a method for start of connection
     * @throws IOException
     */
    public void startConnection() throws IOException {
        if (server) {
            ServerSocket serverSocket = new ServerSocket(13028);
                socket = serverSocket.accept();
        } else {
                socket = new Socket(host, 13028);
        }
    }

    /**
     * a method for share information between client and server
     * @param ShareData
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ShareData sendInformation(ShareData ShareData) throws IOException, ClassNotFoundException {
        ShareData getenInfo = null;
        if (StartFrames.isServer()) {
            sendServerSharedInformation(ShareData);
            getenInfo = getServerSharedInformation();

        } else {
            getenInfo = getServerSharedInformation();
            sendServerSharedInformation(ShareData);
        }
        return getenInfo;
    }

    /**
     * a method for get data from network
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private ShareData
    getServerSharedInformation() throws IOException, ClassNotFoundException {
        ShareData sharedInformation = null;

        // that was sent to the server by the client.
        // close resources
            in = new ObjectInputStream(socket.getInputStream());
            sharedInformation = (ShareData) in.readObject();

        return sharedInformation;
    }

    /**
     *  a method for send information in network
     * @param shareData
     */
    private void sendServerSharedInformation(ShareData shareData) {
        try {
            // that was sent to the server by the client.
            // close resources

            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(shareData);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setServer(boolean server) {
        this.server = server;
    }

    public void setHost(String host) {
        this.host = host;
    }

    /**
     *  a method for reset connection and close socket
     */
    public static void resetConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "There is a problem in connection!", "Error", JOptionPane.ERROR_MESSAGE);
            Informations.resetInformation();
            ThreadPool.init();
            StartFrames.firstFrames();
            MapMaker.getMapMaker().resetMap();
//            e.printStackTrace();
        }
    }
}
