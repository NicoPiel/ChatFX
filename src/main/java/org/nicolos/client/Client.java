package org.nicolos.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
      static final int port = 45126;
      Socket clientSocket;

      public Client(String IP) {
            try {
                  clientSocket = new Socket(IP, port);
                  System.out.println(TestConnection(IP));
            }
            catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public boolean TestConnection(String IPToTest) {
            Socket server = null;

            try {
                  server = new Socket(IPToTest, port);
                  Scanner in  = new Scanner(clientSocket.getInputStream());

                  System.out.println(in.nextLine());
                  System.out.println("Done, thank you for your patience.");

                  return true;
            }
            catch (UnknownHostException e) {
                  System.out.println("Couldn't determine the host.");
                  e.printStackTrace();
            }
            catch (IOException e) {
                  System.out.println("Couldn't initialize the socket.");
                  e.printStackTrace();
            }
            finally {
                  if (server != null) {
                        try {
                              server.close();
                        }
                        catch (IOException e) {
                              e.printStackTrace();
                        }
                  }
            }

            return false;
      }
}