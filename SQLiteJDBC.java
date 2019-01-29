/* This class creates a database using SQlite. The database interacts with Person objects.
 * 
 * Name: Ahmed Mansour
 * Date: 11/21/18
 */
import java.sql.*;
import java.util.ArrayList;

public class SQLiteJDBC {
	
   public static void createTable() {
      Connection conn = null;
      Statement stmt = null;
      
      try {
         Class.forName("org.sqlite.JDBC");
         conn = DriverManager.getConnection("jdbc:sqlite:personalinfo.db"); //Connect to database
         System.out.println("Opened database successfully!!");

         stmt = conn.createStatement();
         String sql = "CREATE TABLE IF NOT EXISTS PEOPLE  (\n"  //table is created with 6 columns
               +  " id              integer    PRIMARY KEY,\n" 
        	   +  " firstName       text       NOT NULL,   \n"
        	   +  " lastName        text       NOT NULL,   \n"
        	   +  " age             integer    NOT NULL,   \n"
        	   +  " ssn             integer,               \n"
        	   +  " creditCard      integer                \n"
        	   +  ");";  
         stmt.executeUpdate(sql);
         stmt.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully!!");
   }
   
   public static void insertPerson(Person person) { //Insert a row into the table that holds a Person objects parameters
	   Connection conn = null;
	   
           try {
        	 conn = DriverManager.getConnection("jdbc:sqlite:personalinfo.db");
        	 String sqlInsert = "INSERT INTO PEOPLE(firstName, lastName, age, ssn, creditCard) VALUES(?,?,?,?,?)";
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
			 pstmt.setString(1, person.getFirstName()); //sets the data in the table to the parameters of the Person object
			 pstmt.setString(2, person.getLastName());
			 pstmt.setInt(3, person.getAge());
			 pstmt.setLong(4, person.getSSN());
			 pstmt.setLong(5, person.getCreditCard());
			 pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}


   }
   
   public static Person selectPerson(int ID) { //This method finds a row in a table using an ID and returns a Person object with that row's values
	   Person person = new Person("","",0,0,0);
	   String sqlGetInfo = "SELECT id, firstName, lastName, age, ssn, creditCard FROM PEOPLE";
	   Connection conn = null;
	   
	   try {
		conn = DriverManager.getConnection("jdbc:sqlite:personalinfo.db");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlGetInfo);
		
		 while ( rs.next() ) {
			 if(rs.getInt("id") == ID) {
				 person = new Person(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"), rs.getLong("ssn"), rs.getLong("creditCard"));
			 }
			 
		 }
		 rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	   
	   return person;
   }
   public static ArrayList<Person> findAllPeople() { //This method creates an ArrayList of Person objects and adds all table rows to it, then returns the Arraylist
	   String sqlGetInfo = "SELECT id, firstName, lastName, age, ssn, creditCard FROM PEOPLE";
	   ArrayList<Person> personArray = new ArrayList<Person>();
	   Connection conn = null;
	   
	   try {
		conn = DriverManager.getConnection("jdbc:sqlite:personalinfo.db");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlGetInfo);
		 while ( rs.next() ) {
			 personArray.add(new Person(rs.getString("firstName"), rs.getString("lastName"), rs.getInt("age"), rs.getLong("ssn"), rs.getLong("creditCard")));
		 }
		 rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	   return personArray;
  }
   
   public static void selectAll() { //This method displays the contents of the database table to the console
	   String sqlGetInfo = "SELECT id, firstName, lastName, age, ssn, creditCard FROM PEOPLE";
	   Connection conn = null;
	   
	   try {
		conn = DriverManager.getConnection("jdbc:sqlite:personalinfo.db");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlGetInfo);
		 while ( rs.next() ) {

			 System.out.println(rs.getInt("id") + "\t" + rs.getString("firstName") + "\t" + rs.getString("lastName") + "\t" + rs.getInt("age") + "\t" + rs.getInt("ssn") + "\t" + rs.getInt("creditCard"));

		 }
		 rs.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
   
   public static String deletePerson(String firstName, String lastName) { //this method removes a row from the table
	   String sqlGetInfo = "SELECT id, firstName, lastName, age, ssn, creditCard FROM PEOPLE";
	   boolean wasDeleted = false; //This boolean sets a flag to let the us know if something was deleted
	   Person person;
	   String result = "";
	   Connection conn = null;
	   
	   try {
		conn = DriverManager.getConnection("jdbc:sqlite:personalinfo.db");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlGetInfo);
		 while ( rs.next() ) {
			 if(rs.getString("firstName").equals(firstName) && rs.getString("lastName").equals(lastName)) {
				 System.out.println("Entry that is being deleted:");
				 person = selectPerson(rs.getInt("id"));
				 System.out.println(person.toString());
				 stmt.executeUpdate("DELETE from PEOPLE where ID=" + rs.getInt("id"));
				 wasDeleted = true; //if name specified was found and deleted, this boolean is turned to true
			 }

		 }
		 if(!wasDeleted) { //this message will appear if boolean wasn't turned to true, in other words if nothing was deleted
			result = "Sorry, the name " + firstName + " " + lastName + " was not found in the database";
		 }
		 System.out.println("\nDatabase after deletion:");
		 selectAll();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	   return result;
   }
   public static void main(String[] args) {
		
		Person person1 = new Person("John", "Doe", 29, 123567839L, 1789456354L); //creates a few Person objects
		Person person2 = new Person("Ahmed", "Mansour", 19, 87658486L, 452684534538L);
		Person person3 = new Person("Max", "Johnson", 46, 554817621L, 9456354324L);
		Person person4 = new Person("Suzy", "Jackson", 23, 974635423L, 654874697436L);
		ArrayList<Person> personArray = new ArrayList<Person>();
		
		Person retrievedPerson; //A Person object to hold what values were retrieved from the database
		
		createTable(); //creates the table itself
		
		insertPerson(person1); //inserts four Person objects
		insertPerson(person2);
		insertPerson(person3);
		insertPerson(person4);
		
		System.out.println("ID\tFirst\tLast\tAge\t  SSN\t       Credit Card Num");
		selectAll(); //Displays all data
		
		System.out.println();
		System.out.println("Retrieving Person with ID 2...");
		retrievedPerson = selectPerson(2); //displays the data of the Person with ID 2
		System.out.println(retrievedPerson.toString());
		System.out.println();
		System.out.println("Retrieving Person with ID 1...");
		retrievedPerson = selectPerson(1);
		System.out.println(retrievedPerson.toString());
		System.out.println();
		System.out.println("Retrieving Person with ID 3...");
		retrievedPerson = selectPerson(3);
		System.out.println(retrievedPerson.toString());
		System.out.println();
		
		System.out.println("ArrayList of Person objects filled from the database...");
		personArray = findAllPeople(); //Fill an ArrayList with Person objects
		
		for(Person p : personArray) { //A for each loop to iterate through the ArrayList
			System.out.println(p.toString());
		}
		
		System.out.println();
		System.out.println("Deleting Max Johnson...");
		
		System.out.println(deletePerson("Max", "Johnson")); //deletes a row of data in table
		
		
		
	}

}