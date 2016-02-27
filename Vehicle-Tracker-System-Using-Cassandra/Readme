#Project to get started with Cassandra.

Installing Cassandra:
  Navigate to http://cassandra.apache.org/download/ and download the apache-cassandra-x.x-bin.tar.gz
  Download and install Oracle JRE or JDK (7 or Higher)
  Create new directory, mkdir cassandra
  cd cassandra
  mv ~/Downloads/apache-cassandra-2.1.6-bin.tar.gz
  tar -xvzf apache-cassandra-2.1.6-bin.tar.gz
  cd apache-cassandra-2.1.6 
  Start Cassandra: bin/cassandra 
  Stop Cassandra: ps aux | grep cass 
                  kill <portnumber> 
  
  Checking Status of Cassandra: bin/nodetool status

*Communicating with Cassandra: (After starting the cassandra, open a new terminal and do the following)
  cd apache-cassandra-2.1.6

  bin/cqlsh
  
  SHOW HOST; // to see the name of the cluster

  HELP; // to see list of all CQL commands


*Creating a database and table:

  cd apache-cassandra-2.1.6
  
  bin/cqlsh
  
  DESCRIBE KEYSPACES; //View schemas existing in the system.
  
  DESCRIBE KEYSPACE // to view the tables that exist in the system keyspace.
  
  CREATE KEYSPACE WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
  
  USE keyspace_name;
  
  CREATE TABLE (col1 type1, col2 type2, .. , PRIMARY KEY(col1, col2,..)) WITH CLUSTERING ORDER BY ( col1 DESC); // Clustering order by is set by default to Ascending order.
  
  INSERT INTO : used to insert data 
  
  COPY: data can also be imported rows from a .csv file using COPY command
  
  SELECT: used to access data in a table (the WHERE clause need to include a partition key)
  
  UPDATE: it is used to update a record in the table, but UPDATE doesn't work the same way as relational database. It writes and not updates the new changes, and when a request is made for the row data, the timestamps are compared for the conflicting cells to return the latest result.
  
  DELETE: DELETE can be used to delete a cell, a row of a table. TRUNCATE is used to delete all the rows of a table. DROP TABLE is used to delete the entire table. DROP KEYSPACE is used to delete the keyspace. (Delete doesn't remove the contents immediately. It marks the tombstone, the date when the data can be deleted, so that if any node id down, it can be made aware of the recent deletion. It sets the minimum time that must pass before the deletion occurs, gc_grace_seconds property. For the data to be actually deleted, compaction should occur, after passing the grace period. It combines SSTables and the releases the disk space.You can use TTL for automatic expiration of data in a row/cell. The clause used is USING TTL while using INSERT. TTL can be updated using UPDATE. Once TTL expires, the SSTables mark the entry as "d" to be deleted after the grace period. Before the TTL expiration, it marks them as "e".)

To see how data is stored in cassandra: 
  bin/cassandra-cli; 
  USE keyspace_name; 
  LIST table_name; 
  quit; // after finish viewing the data.

To see how data is stored in disk: 
cd data/data 
ls home_security 
open a new terminal and type: bin/nodetool flush home_security // to flush the memcaches for home_security keyspace to disk. back to the old terminal: ls home_security/activity // to view the SSTable file You can use bin/sstable2json/data/data/home_security/activity/home_security-activity-ka-1-Data.db to view contents of a particular table.

Note: There are no joins in CQL.

To use a column in WHERE clause, create an INDEX first: CREATE INDEX ON ();

You can also define composite partition key((col1, col2), col3);

Install Java Cassandra Driver:(Use Eclipse IDE and Apache Tomcat Server, install Apache Maven to manage dependencies)
  http://planetcassandra.org/client-drivers-tools
  Open Eclipse-> Create New - Dynamic Web Project -> project name : vehicle_tracker -> Select Target -> Click Next -> Next ->   Check the box saying "Generate web.xml deployment descriptor" -> Finish

Create an Application Page: 
vehicle_tracke -> Java Resources -> src (right click and create a new servlet) 
Enter Java Package as com.vehicletracker.servlet 
For Class name, enter VehicleTrackerServlet 
Click Next 
In URL mappigs, select /VehicleTrackerServlet and Click Edit and change to /track Click OK and then Next 
Deselct the checkbox for doPost Finish Enter your code in doGet method of VehicleTrackerServlet.java Save and Run As - Run on Server (Before this, make sure that the Cassandra is running in background.) 
Leave Tomcat v7.0 selected - click Finish.

Get the needed DataStax JAR files for this project: ( http://www.datastax.com/download#dl-datastax-drivers )
  
  cassandra-driver-core-2.0.1.jar   
  netty-3.9.0-Final.jar 
  guava-16.0.1.jar 
  metrics-core-3.0.2.jar 
  slf4j-api-1.7.5.jar

In terminal, open vim conf/cassandra.yaml and set start_native_transport : true
In Eclipse, right click vehicle_tracker and select Configure - Convert to Maven Project, create new POM file and finish. 
Open POM file, go to Dependencies tab and click Add. 
For group ID: com.datastax.cassandra
For Artifact ID: cassandra-driver-core
For version: 2.1.6

OR

Download JAR files and put them manually in WEB_INF/lib folder of your project.


Sample Input for Form: 
Date: 2014-05-19 
Vehicle ID: WA063JXD 


{Ref. Ruth Stryker's apache cassandra hands-on training level one}
