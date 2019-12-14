package org.nicolos.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
      static final int port = 45126;
      Socket clientSocket;

      public Client() {
            try {
                  clientSocket = new Socket("localhost", port);
                  System.out.println(TestConnection());
            }
            catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public boolean TestConnection() {
            Socket server = null;

            try {
                  server = new Socket("localhost", port);
                  Scanner in  = new Scanner(server.getInputStream());

                  System.out.println(in.nextLine());
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
