package edu.cooper.ece366.week3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHTTPServerSync {

  public static void main(String[] args) throws Exception {
    ServerSocket serverSocket = new ServerSocket(8000);
    System.out.println("listening on port 8000");

    while (true) {
      Socket accept = serverSocket.accept();
      BufferedReader inputReader =
          new BufferedReader(new InputStreamReader(accept.getInputStream()));
      BufferedWriter outputWriter =
          new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));

      String line = inputReader.readLine();
      while (!line.isEmpty()) {
        // do something with response
        line = inputReader.readLine();
        System.out.println(line);
      }
      String response = "{\"response\": \"hey\"}";

      outputWriter.write("HTTP/1.1 200 OK\r\n");
      outputWriter.write("content-length: " + response.length());
      outputWriter.write("\r\n\r\n");
      outputWriter.write(response);

      outputWriter.close();
      inputReader.close();
      accept.close();
    }
  }
}
