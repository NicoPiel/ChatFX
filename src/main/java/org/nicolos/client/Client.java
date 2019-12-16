package org.nicolos.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
      static final int port = 45126;
      String ipToConnectTo;
      String requestString = "/#req#/";
      boolean connected;

      int currentChatLength;

      ArrayList<String> fullChat;

      Socket socket;

      public Client(String ip) {
            this.ipToConnectTo = ip;
            fullChat = new ArrayList<>();

            this.socket = RequestConnection(requestString);

            new Thread(() -> {
                  while (connected) {
                        connected = PokeServer();

                        try {
                              Thread.sleep(3000);
                        } catch (InterruptedException e) {
                              e.printStackTrace();
                        }
                  }
            }).start();
      }

      boolean PokeServer() {
            this.socket = null;

            try {
                  this.socket = new Socket(this.ipToConnectTo, port);

                  if (connected) {
                        System.out.println(Thread.currentThread().getName() + ": Poking..");

                        try {
                              Scanner in = new Scanner(socket.getInputStream());
                              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                              out.println(requestString);

                              if (in.hasNext()) {
                                    int serverChatLength = in.nextInt();
                                    System.out.println("Server chat length: " + serverChatLength);

                                    if (currentChatLength < serverChatLength) {
                                          out.print(true);

                                          while (in.hasNext()) {
                                                String newChatLine = in.nextLine();
                                                fullChat.add(newChatLine);
                                          }

                                          this.currentChatLength = fullChat.size();
                                    } else if (currentChatLength == serverChatLength) {
                                          out.print(false);
                                    } else {
                                          System.out.println("Something went wrong, please try reconnecting.");
                                          return false;
                                    }

                                    return true;
                              }
                        } catch (IOException e) {
                              System.err.println("Couldn't write to server's output stream.");
                              e.printStackTrace();
                        }
                  } else {
                        System.err.println("Couldn't poke the server.");
                        return false;
                  }
            } catch (ConnectException e) {
                  System.err.println("Server is down..");
                  return false;
            } catch (IOException e) {
                  e.printStackTrace();
            } finally {
                  try {
                        TerminateConnection();
                  } catch (IOException e) {
                        e.getMessage();
                  }
            }

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

                  connected = true;
            } catch (UnknownHostException e) {
                  System.err.println("Couldn't determine the host.");
                  e.printStackTrace();
                  return null;
            } catch (IOException e) {
                  System.err.println("Couldn't initialize the socket.");
                  e.printStackTrace();
                  return null;
            } finally {
                  try {
                        TerminateConnection();
                  } catch (IOException e) {
                        e.getMessage();
                  }
            }

            return socket;
      }

      public boolean TerminateConnection() throws IOException {
            try {
                  if (socket != null) {
                        this.socket.close();
                        System.out.println("Closed the client's connection..");
                        return true;
                  } else {
                        throw new IOException("Socket already closed.");
                  }
            } catch (IOException e) {
                  e.printStackTrace();
            }

            return false;
      }

      public ArrayList<String> GetFullChat() {
            return this.fullChat;
      }

      public boolean IsConnected() {
            return this.connected;
      }

      public boolean HasSocket() {
            return (this.socket != null);
      }

      public Socket GetSocket() {
            return this.socket;
      }
}