package Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;  
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Statement;  
import java.util.ArrayList;
import java.util.Scanner;
public class DataBase 
{  
	public Connection connection = null;  
	public ResultSet resultSet = null;  
	public Statement statement = null;
	Connection conn;
		
	
	
	
	
	
	public void createDB()
	{
		try {
			connectToDB();
	        String sql="CREATE TABLE WORD (word TEXT   NOT NULL);"; 
	        try{
	        	statement.executeUpdate(sql);
			
	        }catch(SQLException e)
	        {
	        //	System.out.println("\n......table alread exists......\n\nso deleting existing table and creating another one");
	        	statement.executeUpdate("DROP TABLE WORD;");
	        	statement.executeUpdate(sql);
				System.out.println("\n.....after deleting creating a new table is successfull");
	        	//	e.printStackTrace();
	        }
			System.out.println("database with a table word been created");
			statement.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void connectToDB() throws ClassNotFoundException, SQLException
	{
		
			Class.forName("org.sqlite.JDBC");
			String dbURL = "jdbc:sqlite:product.db";
			conn = DriverManager.getConnection(dbURL);		
			statement=conn.createStatement();
		
	}
	
	public void deleteTable()
	{
		try {
			connectToDB();
			
			statement.executeUpdate("DROP TABLE WORD;");
			
			System.out.println("database table WORD has been deleted");
			
			statement.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void insert(ArrayList<String> s)
	{
		System.out.println("in insert functionwith array list:");
		try {
			connectToDB();
			
			for (int i=0;i<s.size();i++)
			{
				
				String sql="INSERT INTO WORD (word) VALUES ("+"'"+s.get(i)+"'"+");";
				
				statement.addBatch(sql);
			//	statement.executeUpdate(sql);
					
				if (i%50==0)
				{
					statement.executeBatch();
					System.out.print(" "+i);
							
				}
					
			}
			
			System.out.println("all the words has been successfully added to database");
			statement.close();
			conn.close();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void insert(String s)
	{
		
		try {
			connectToDB();			
			String sql="INSERT INTO WORD VALUES ("+"'"+s+"'"+");";
		//	System.out.println("the sql is: "+sql);
			statement.executeUpdate(sql);			
	//		System.out.println("word has been successfully added to database");
			
			statement.close();
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void printDB()
	{
		try {
			
			System.out.println("\n\n....about to print the whole database...");
			
			connectToDB();			
			ResultSet res=statement.executeQuery("SELECT * FROM WORD;");
			
			while (res.next())
			{
				String s=res.getString("word");
				System.out.println(s);
	
			}
			
			System.out.println("\n\n\nDatabase printing over");
			
			
			statement.close();
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public boolean isItAWord(String s)
	{
		boolean r=false;
		try{
			
			connectToDB();			
			String sql="SELECT * FROM WORD WHERE word like "+"'"+s+"'"+";";
			
			ResultSet res2=statement.executeQuery(sql);
	
			
			while (res2.next())
			{
				r=true;
				break;
	
			}
			statement.close();
			conn.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(s+ " is it a word: "+r);
		
		return r;
		
		
	}
	
	public void createNewDBFromTextFile()
	{
		
		createDB();
		ArrayList <String> w=new ArrayList<>();
		
		try {
			Scanner sc=new Scanner(new File("wordlist.txt"));
			while (sc.hasNext())
			{
				String s=sc.next();
				w.add(s);
				
			}
			
			insert(w);
			
		//	System.out.println(w.toString());
			
			System.out.println("new database has been created from text file");
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) 
	{  
		System.out.println("main started");
		
		DataBase db=new DataBase();
	//	db.createNewDBFromTextFile();
		
		db.isItAWord("boo");
		
		
		
		
		
		
		
		System.out.println("main over");
		
		
		
		
		
		
	}
	
//		try 
//		{  
//	
//			Class.forName("org.sqlite.JDBC");
//			String dbURL = "jdbc:sqlite:product.db";
//			Connection conn = DriverManager.getConnection(dbURL);
//		//	conn.setAutoCommit(false);
//	
//			System.out.println("\nconnection successful");
//			statement=conn.createStatement();
//	
//			//         String s="CREATE TABLE WORD (word TEXT   NOT NULL);";         
//			//         statement.executeUpdate(s);
//			////         
//			//       
//			//        
//			String sql="INSERT INTO WORD VALUES ('book');";
//			statement.executeUpdate(sql);
//	
//	
//	
//			sql="INSERT INTO WORD VALUES ('cat');";
//			statement.executeUpdate(sql);
//	
//			sql="INSERT INTO WORD VALUES ('dog');";
//			statement.executeUpdate(sql);
//	
//			sql="INSERT INTO WORD VALUES ('bat');";
//			statement.executeUpdate(sql);
//	
//	
//			//         
//			//         sql="INSERT INTO WORD VALUES ('cat');";
//			//         statement.executeUpdate(sql);
//			//         
//			//         
//	
//			ResultSet res=statement.executeQuery("SELECT * FROM WORD;");
//	
//			while (res.next())
//			{
//				String s=res.getString("word");
//				System.out.println(s);
//			}
//	
//			//         
//			sql="SELECT * FROM WORD WHERE word like 'dog';";
//	
//			ResultSet res2=statement.executeQuery(sql);
//	
//			System.out.println("\n\nsearching......");
//			while (res.next())
//			{
//				String s=res2.getString("word");
//				System.out.println(s);
//	
//			}
//			//         
//	
//			//   statement.executeUpdate("DROP TABLE WORD;");
//			//         DatabaseMetaData md = conn.getMetaData();
//			//         ResultSet rs =md.getTables(null, null, "%", null);
//			//         while (rs.next()) {
//			//           System.out.println(rs.getString(3));
//			//         }
//			//         
//			statement.close();
//			conn.close();
//	
//	
//			System.out.println("over");
//	
//		} 
//	
//		catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
//		}  
}  