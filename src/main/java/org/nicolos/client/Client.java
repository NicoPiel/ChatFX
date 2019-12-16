package org.nicolos.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
      static final int port = 45126;
      final int checkInterval = 500;
      String ipToConnectTo;
      String requestString = "/#req#/";
      boolean connected;

      int currentChatLength;

      ArrayList<String> fullChat;

      ExecutorService executor;
      Socket socket;

      public Client(String ip) {
            executor = Executors.newCachedThreadPool();

            this.ipToConnectTo = ip;
            fullChat = new ArrayList<>();

            this.socket = EstablishConnection();

            executor.execute(() -> {
                  while (connected) {
                        connected = PokeServer();

                        try {
                              Thread.sleep(checkInterval);
                        } catch (InterruptedException e) {
                              e.printStackTrace();
                        }
                  }
            });

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

                              out.println(requestString); // 1

                              if (in.hasNext()) {
                                    int serverChatLength = in.nextInt(); // 2
                                    System.out.println("Client: Server chat length: " + serverChatLength);

                                    if (currentChatLength < serverChatLength) {
                                          out.println(true); // 3
                                          System.out.println("Client: needs new chat history.");

                                          out.println(currentChatLength); // 4

                                          BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                                          String newChat = input.readLine(); // 5
                                          System.out.println("Client: Writing to chat history.");
                                          System.out.println("New chat: " + newChat);
                                          fullChat.add(newChat);

                                          System.out.println("Client: chat size = " + currentChatLength);
                                          System.out.println("Client full chat: " + GetFullChatString());
                                    } else if (currentChatLength == serverChatLength) {
                                          out.print(false);
                                    }

                                    this.currentChatLength = fullChat.size();

                                    in.close();
                                    out.close();

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

      public Socket EstablishConnection() {
            this.socket = null;

            System.out.println(Thread.currentThread().getName() + ": Establishing connection..");

            try {
                  this.socket = new Socket(this.ipToConnectTo, port);

                  Scanner in = new Scanner(socket.getInputStream());
                  PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                  out.println("#1" + requestString);

                  if (in.hasNext()) {
                        this.currentChatLength = in.nextInt();
                        System.out.println("Client: Server chat length established at " + currentChatLength);
                  }

                  connected = true;
                  System.out.println(Thread.currentThread().getName() + ": Connection established.");
            } catch (UnknownHostException e) {
                  System.err.println("Couldn't determine the host.");
                  e.printStackTrace();
            } catch (IOException e) {
                  System.err.println("Couldn't initialize the socket.");
                  e.printStackTrace();
            } finally {
                  try {
                        TerminateConnection();
                  } catch (IOException e) {
                        e.getMessage();
                  }
            }

            return this.socket;
      }

      public Socket RequestConnection(String message) {
            this.socket = null;

            System.out.println(Thread.currentThread().getName() + ": Requesting connection..");

            try {
                  this.socket = new Socket(this.ipToConnectTo, port);
                  Scanner in = new Scanner(this.socket.getInputStream());
                  PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);

                  out.println(message);

                  System.out.println(message);

                  connected = true;
            } catch (UnknownHostException e) {
                  System.err.println("Couldn't determine the host.");
                  e.printStackTrace();
            } catch (IOException e) {
                  System.err.println("Couldn't initialize the socket.");
                  e.printStackTrace();
            } finally {
                  try {
                        TerminateConnection();
                  } catch (IOException e) {
                        e.getMessage();
                  }
            }

            return this.socket;
      }

      public void TerminateConnection() throws IOException {
            try {
                  if (socket != null) {
                        this.socket.close();
                        System.out.println("Closed the client's connection..");
                  } else {
                        throw new IOException("Socket already closed.");
                  }
            } catch (IOException e) {
                  e.printStackTrace();
            }
      }

      public void TerminateConnectionManually() throws IOException {
            try {
                  if (socket != null) {
                        this.socket.close();
                        System.out.println("Closed the client's connection..");
                  } else {
                        throw new IOException("Socket already closed.");
                  }
            } catch (IOException e) {
                  e.printStackTrace();
            }

            connected = false;
      }

      public String GetFullChatString() {
            StringBuilder sb = new StringBuilder();

            for (String s : GetFullChat()) {
                  sb.append(s);
                  sb.append(", ");
            }

            return sb.toString();
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

      public int GetInterval() {
            return this.checkInterval;
      }
}
