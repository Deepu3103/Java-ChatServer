package projects.ChatApplication;

import java.io.*;
import java.net.*;

/**
 * Client class it is client side source code
 * By passing ipv4 address and port number client will connect with sever
 *
 *
 */
public class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;


    /**
     * no-parameterised constructor
     *
     */
    public Client(){
        try {
            System.out.println("Sending request to server");
            socket=new Socket();
            socket.connect(new InetSocketAddress("192.168.0.114",12345));
            System.out.println("Connection done");
            System.out.println("Hello"); System.out.println("Hello");
            System.out.println("Hello");
            System.out.println("Hello");

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
            

        }
        catch (Exception e ){
            System.out.println("Connection Failed....Please try again!");
        }

    }

    /**
     *
     * This function is used to read the data send by Server.
     */
    private void startReading() {
        //thread1 only read the data
        Runnable r1=()->{
            System.out.println("Reader started");
            try {
                while (true) {
                    String msg = null;

                    msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Deepu went Offline");
                        socket.close();
                        break;
                    }
                    System.out.println("Deepu : " + msg);
                }
            }catch (Exception e) {
                System.out.println("Unexpected Error occurred sorry for inconvenience");
            }
        };
        new Thread(r1).start();
    }

    /**
     * This function helps to write and send the data to Server.
     */
    private void startWriting() {
        //thread2 take data from user and send it client
        Runnable r2=()->{
            System.out.println("Writer Started");
            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }

                }
            }
                catch(Exception e){
                    System.out.println("chat Application closed");
                }

        };
        new Thread(r2).start();
    }

    /**
     * Whenever main program runs it calls Server class constructor automatically.
     * no parameterised constructor.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("this is client");
        new Client();
    }
}
