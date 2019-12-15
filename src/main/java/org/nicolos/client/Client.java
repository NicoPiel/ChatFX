package org.nicolos.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class Client {
      static final int port = 45126;
      String ipToConnectTo;
      boolean connected;

      Socket socket;

      public Client(String ip) {
            this.ipToConnectTo = ip;

            this.socket = RequestConnection("Client requesting connection..");

            connected = true;
      }

      boolean PokeServer (Socket server) {
            /*if (connected) {
                  try {
                        Scanner in = new Scanner(server.getInputStream());
                        PrintWriter out = new PrintWriter(server.getOutputStream(), true);

                        out.println("/#req#/");

                        if (in.hasNext()) {

                        }
                  }
                  catch (IOException e) {
                        System.err.println("Couldn't write to server's output stream.");
                        e.printStackTrace();
                  }
            }
            else {
                  System.err.println("Couldn't poke the server.");
                  return false;
            }
            return false;*/

            return false;
      }

      public Socket RequestConnection(String message) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss z 'on' dd.MM.yyyy");
            this.socket = null;

            System.out.println(Thread.currentThread().getName() + ": Requesting connection..");

            try {
                  this.socket = new Socket(this.ipToConnectTo, port);
                  Scanner in = new Scanner(this.socket.getInputStream());
                  PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

                  String output = String.format("<%s> %s", dateFormat.format(Date.from(Instant.now())), message);

                  out.println(output);

                  System.out.println(output);
            } catch (UnknownHostException e) {
                  System.err.println("Couldn't determine the host.");
                  e.printStackTrace();
                  return null;
            } catch (IOException e) {
                  System.err.println("Couldn't initialize the socket.");
                  e.printStackTrace();
                  return null;
            } finally {
                  TerminateConnection(this.socket);
            }

            return socket;
      }
      public boolean TerminateConnection (Socket socket) {
            try {
                  if (socket != null) {
                        this.socket.close();
                        System.out.println("Closed the client's connection..");
                        return true;
                  }
                  else {
                        System.err.println("There was no socket to close.");
                        return false;
                  }
            }
            catch (IOException e) {
                  e.printStackTrace();
            }

            return false;
      }

      public boolean TerminateConnectionManually (Socket socket) {
            try {
                  if (socket != null) {
                        this.socket.close();
                        System.out.println("Closed the client's connection..");
                        connected = true;
                        return true;
                  }
                  else {
                        System.err.println("There was no socket to close.");
                        connected = false;
                        return false;
                  }
            }
            catch (IOException e) {
                  e.printStackTrace();
            }

            return false;
      }

      public boolean IsConnected() {
            return connected;
      }

      public Socket GetSocket () {
            return this.socket;
      }
      public boolean HasSocket () {
            return this.socket != null;
      }
}