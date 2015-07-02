// Description : An implementation of Baggage system problem statements
//Author : Sameer Lowlekar

package com.baggage.details;
import java.util.Scanner;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;			
import com.datastax.driver.core.Metadata;			
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;			
import com.datastax.driver.core.Row;			
import com.datastax.driver.core.Session;	

// Defining the class
public class Baggage_Details  {			
   private Cluster cluster;			
   private Session session;			

// Connecting to Cassandra Cluster
   public void connect(String node) {			
      cluster = Cluster.builder()			
            .addContactPoint(node).build();			
      Metadata metadata = cluster.getMetadata();			
      System.out.printf("Connected to cluster: %s\n", 			
            metadata.getClusterName());			
      for ( Host host : metadata.getAllHosts() ) {			
         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",			
               host.getDatacenter(), host.getAddress(), host.getRack());			
      }			
      session = cluster.connect();			
   }			

// Populating the cassandra tables for demo run
   
   public void loadData() { 			
	   session.execute(		
			      "INSERT INTO baggage.baggage_details (bag_id,entry_point,flight_id) " +
			      "VALUES (" +
			          "0002," +
			          "'A4'," +
			          "'UA17'" +
			          ");");
	   session.execute(		
			      "INSERT INTO baggage.flight_details (flight_id,exit_point,destination,time) " +
			      "VALUES (" +
			          "'UA17'," +
			          "'A3'," +
			          "'MHT'," +
			          "'9:15'" +
			          ");");
			
   }			
 			
// The method to perform required fetch operation to get entry and exit gates corresponding to bag and flight related details
  public void queryData() {
	  
	  System.out.println("Please enter the Bag ID  "); // Accepting user input for Bag ID
	  Scanner baginp = new Scanner(System.in);
      int input = baginp.nextInt(); 

// Querying the database based on bag id provided
	  PreparedStatement ps = session.prepare("SELECT flight_id,entry_point FROM baggage.baggage_details " +
			  "WHERE bag_id in ( "+input+" ) ");
	  BoundStatement bs = ps.bind();
	  ResultSet results = session.execute(bs);
	  
	  String flightid ="";
	  int entry=0;
	  int exit=0;
	  String entrygate="";

	  for (Row row : results) {
	  
			  flightid=row.getString("flight_id");
			 entrygate=row.getString("entry_point");
			 entry=Integer.parseInt(entrygate.substring(1));
					  
		  System.out.println("\nFlight ID and Entry gate for provided Bag ID is : "+flightid +"&" +entrygate);
		  
		  }

// Fetching the Exit Gate corresponding to Flight ID fetched above
	  
	  PreparedStatement ps1 = session.prepare("SELECT exit_point FROM baggage.flight_details " +
			  "WHERE flight_id in ( '"+flightid+"' ) ");
	  BoundStatement bs1 = ps1.bind();
	  ResultSet results1 = session.execute(bs1);	 
	
	  String exitgate="";

	  for (Row row : results1) {
	  
	  exitgate=row.getString("exit_point");
	  exit=Integer.parseInt(exitgate.substring(1));
		  	  
	  System.out.println("\nExit Gate corresponding to above Flight ID is : "+exitgate);
		  	  
	  }

	 	 
// Instantiating the ShortestPath class (i.e. using ShortestPath.java file here) to call the GetShortestPath method and providing
// inputs to the method as the entry and exit gates. Finally, showing the time between the two gates as output
	  
	  ShortestPath sp=new ShortestPath();
	   sp.GetShortestPath(entry, exit);
  }

public void close() {			
	     cluster.close();			
	   }

  // The Main method

   public static void main(String[] args) {			
	   Baggage_Details client = new Baggage_Details();		   
      client.connect("127.0.0.1");
      client.loadData();
      client.queryData();			
      client.close();			
   }			
}			
