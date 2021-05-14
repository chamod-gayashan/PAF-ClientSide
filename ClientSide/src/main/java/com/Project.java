package com;
import java.sql.*;

public class Project {
	
	private Connection connect()
	{
		Connection con = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf13.2", "root", "");
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	return con;
	
	}
	
	public String readItems()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			 output = "<table class='table table-bordered table-dark'><thead><tr><th scope='col'>Project Name</th>"
						+ "<th scope='col'>Project Price</th>"
						+ "<th scope='col'>Project Desc</th>"
						+ "<th scope='col'>Update</th><th>Remove</th></tr></thead>";
				String query = "select * from items";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query); 
			
			// iterate through the rows in the result set
			
			while (rs.next())
			{
				String projectID = Integer.toString(rs.getInt("projectID"));
				String projectName = rs.getString("projectName");
				String projectPrice = Double.toString(rs.getDouble("projectPrice"));
				String projectDesc = rs.getString("projectDesc");
	
				 output += "<tbody><tr><td><input id='hidItemIDSave' name='hidItemIDUpdate' type='hidden' value='" + projectID +"'>" + projectName + "</td>";
				 output += "<td>" + projectPrice + "</td>";
				 output += "<td>" + projectDesc + "</td>";
					
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-itemid='" + projectID + "'></td>"
							+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + projectID + "'></td></tr><tbody>";
			 } 
			con.close();
			// Complete the html table
			output += "</table>";
		}
		catch (Exception e)
		{
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
  	
	public String insertItem(String name, String price, String desc)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query = " insert into items(`projectID`,`projectName`,`projectPrice`,`projectDesc`)" + " values (?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
			}
		
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	
	}
	
	
	public String updateItem(String ID, String name, String price, String desc)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
			return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			 String query = "UPDATE items SET projectName=?,projectPrice=?,projectDesc=? WHERE projectID=?"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			 // binding values
			 preparedStmt.setString(1, name); 
			 preparedStmt.setDouble(2, Double.parseDouble(price)); 
			 preparedStmt.setString(3, desc); 
			 preparedStmt.setInt(4, Integer.parseInt(ID)); 
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" +
			newItems + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteItem(String projectID)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from items where projectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(projectID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readItems();
			output = "{\"status\":\"success\", \"data\": \"" + 
			newItems + "\"}";
			
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}
