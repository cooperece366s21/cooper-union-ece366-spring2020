# Week 1

Make sure you have a JVM for Java 11.0 installed. You can compile the source files by running

```bash
$ cd JavaPlayground/src/main/java
$ javac edu/cooper/ece366/week1/*.java
```

Then, you'll notice there are `*.class` files in the same directory as the Java source files. To run the main method in Hello.java, you can run

```bash
$ java edu.cooper.ece366.week1.Hello
Hello
```

### Common Error

```bash
$ cd JavaPlayground/src/main/java/edu/cooper/ece366/week1
$ java Hello
Error: Could not find or load main class Hello
Caused by: java.lang.NoClassDefFoundError: edu/cooper/ece366/week1/Hello (wrong name: Hello)
```

This occurs because `Hello.java` has a defined `package edu.cooper.ece366.week1;` which dictates the classpath. You can explicitly set the classpath from within the `ece366` directory as follows:

```bash
$ java -cp ../../../ edu.cooper.ece366.week1.Hello
Hello
```
