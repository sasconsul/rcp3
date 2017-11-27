A simple command line program to run the core of the word count problem.

# BUILD

To build use maven:
```$shell
    mvn clean build
```

# RUN

Run the program via ``` java -jar <target-jar> <URL-to-count>``` for example:

```
java -jar target/rcp3-0.0.1-SNAPSHOT-jar-with-dependencies.jar  http://bayareatango.org
```

# DATABASE

The database is HSQL stored in the directory ```data/```.  To interact with the DB after runs use the following:
```$shell
java -cp target/rcp3-0.0.1-SNAPSHOT-jar-with-dependencies.jar  org.hsqldb.util.DatabaseManager
```
Use the URL ```jdbc:hsqldb:data/wordcount``` to access the database.

# TODO

Depending on customer requirements of the code some changes would be needed:
* duplicate page detection
* query interface
* change the schema to use mongodb since the schema in SQL can get unwieldy as the application grows.

