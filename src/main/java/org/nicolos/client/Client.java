package org.nicolos.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
      static final int port = 45126;
      String ipToConnectTo;
      Socket clientSocket;

      public Client(String ip) {
            try {
                  clientSocket = new Socket(ip, port);
            }
            catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public boolean WriteToServer(Socket server) {
            try {
                  Scanner in = new Scanner(server.getInputStream());
                  Scanner in_local = new Scanner(System.in);
                  PrintWriter out = new PrintWriter(server.getOutputStream());

                  System.out.println(in.nextLine());

                  String input = in_local.nextLine();

                  switch (input) {
                        case "quit":
                              return false;
                        default:
                              out.println(input);
                              break;
                  }

                  return true;
            }
            catch (IOException e) {
                  e.printStackTrace();
                  return false;
            }
      }

      public Socket RequestConnection() {
            Socket server = null;

            try {
                  server = new Socket(this.ipToConnectTo, port);
                  Scanner in  = new Scanner(clientSocket.getInputStream());

                  System.out.println(in.nextLine());

                  boolean readyToWrite = WriteToServer(server);

                  while(readyToWrite) {
                        readyToWrite = WriteToServer(server);
                  }
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

            return server;

      }

      boolean TerminateConnection (Socket server) {
            try {
                  if (server != null) {
                        server.close();
                        System.out.println("Connection has been closed.");
                  }

                  return true;
            }
            catch (IOException e) {
                  System.out.println("Socket couldn't be closed.");
                  e.printStackTrace();
            }

            return false;
      }
      public boolean HasSocket () {
            return clientSocket != null;
      }
}
