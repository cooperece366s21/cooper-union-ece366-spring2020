package edu.cooper.ece366.week3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHTTPServerAsyncExecutor {

  public static void main(String[] args) throws Exception {
    int port = 8000;
    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("Listening on port " + port);

    ExecutorService executorService = Executors.newFixedThreadPool(8);

    while (true) {
      Socket accept = serverSocket.accept();
      executorService.execute(() -> {
        try {
          handleRequest(accept);
        } catch (IOException | InterruptedException e) {
          throw new RuntimeException("something went wrong handling request", e);
        }
      });
    }
  }

  private static void handleRequest(final Socket accept)
      throws IOException, InterruptedException {
    System.out.println("Request received:");

    InputStream inputStream = accept.getInputStream();
    OutputStream outputStream = accept.getOutputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

    String line;
    String uri = null;
    while (!(line = reader.readLine()).isEmpty()) {
      System.out.println("\t" + line);
      if (line.contains("GET")) {
        uri = line.replaceAll("GET /", "").replaceAll(" HTTP/1.1", "");
      }
    }

    String responseBody = Optional.ofNullable(uri).orElse("");

    String response = "HTTP/1.1 200 OK\r\n"
                      + "content-length: " + responseBody.length()
                      + "\r\n\r\n"
                      + responseBody;
    writer.write(response);

    Thread.sleep(1000);

    System.out.println("Response sent:");
    System.out.println("\t" + response.replaceAll("\n", "\n\t"));

    // close writer before reader
    writer.close();
    reader.close();
    accept.close();
  }
}
