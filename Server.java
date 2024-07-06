package projects.ChatApplication;
import java.io.*;
import  java.net.*;

/**
 * Server class is server side source code in this  chatServer project.
 * When we run this program server will keep waiting for client to get connected.
 * Where we can also say this as socket programming.
 *
 */
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader incoming;
    PrintWriter out;


    //Constructor
    public Server() {
        try {
            server=new ServerSocket(7779);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting...");
             socket=server.accept();

            incoming =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch (Exception e){
            System.out.println("Connection Failed....Please try again!");
        }

    }

    /**
     *
     * This function is used to read the data send by client.
     */
    private void startReading() {
        //Thread-1 created using lambda expression
        Runnable r1=()->{
            System.out.println("Reader started");
            try {
                while (true) {
                    String msg = null;
                    msg = incoming.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("prathik went Offline");
                        socket.close();
                        break;
                    }
                    System.out.println("prathik : " + msg);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }


        };
        new Thread(r1).start();
    }


    /**
     * This function helps to write and send the data to client.
     */
    private void startWriting() {
        //Thread-2 created using lambda expression
        Runnable r2 = () -> {
            System.out.println("Writer started");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if (content.equals("exit")) {
                        socket.close();
                        break;
                    }

                }
            } catch (IOException e) {
                System.out.println("Chat Application closed");
            }
        };
        new Thread(r2).start();
    }

    /**
     * Whenever main program runs it calls Server class constructor automatically.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Welcome to one-time-chat(OTC) room");
        new Server();
    }
}
