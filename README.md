# rdf4j_project
A project for talking with an RDF database using Java, that can be called from C++. 

## Dependencies
Java JDK and Maven is needed to compile this project.
```
sudo apt-get install default-jdk 
sudo apt-get install maven
```

## Compilation and Usage
Run "make" in the root folder to automatically compile the Java code using Maven and create a JAR file with all code and dependencies. The JAR file is saved at maven/rdf4j_connector/target/rdf4j_connector-1.0-SNAPSHOT.jar. This will also compile a simple C++ script that calls the Java code and adds a model to the RDF database. If "Model successfully added, connection is working!" is printed in the terminal, then the program is working correctly. 
