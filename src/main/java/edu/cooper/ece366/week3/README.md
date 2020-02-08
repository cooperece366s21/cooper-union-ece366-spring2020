# Week 3

## Compiling and running the code

To run in IntelliJ (recommended):

```bash
mvn clean compile
```

Then right-click on a file or in a main method to `Run` or `Debug`.

To run manually via maven:

```bash
mvn clean compile
java -classpath target/classes edu.cooper.ece366.week3.SimpleHTTPServerSync 
```

Note that you have to manually set the classpath to the `target` directory where maven writes class files.

You can also compile files manually easily since these classes have no third-party dependencies:

```bash
javac src/main/java/edu/cooper/ece366/week3/SimpleHTTPServerSync.java
java -classpath src/main/java edu.cooper.ece366.week3.SimpleHTTPServerSync
```

## Talking to your simple socket server

```bash
$ curl localhost:8000/search
search%
```

For verbose output:

```bash
$ curl -v localhost:8000/search
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8000 (#0)
> GET /search HTTP/1.1
> Host: localhost:8000
> User-Agent: curl/7.64.1
> Accept: */*
>
< HTTP/1.1 200 OK
< content-length: 6
<
* Connection #0 to host localhost left intact
search* Closing connection 0
```
