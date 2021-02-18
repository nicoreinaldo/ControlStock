package mysquare.core;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Db {
	
	private static Connection conn = null;
	
	private static Connection connect() {
	    try {    
	    	if(conn == null){
	    	    //Class.forName("org.sqlite.JDBC");
				Utility u =new Utility();
	    	    HashMap<String, String> properties = u.getProperties();
	            conn = DriverManager.getConnection(properties.get("dbDriver")+properties.get("dbSource"));
	            System.out.println("Connection to Database has been established.");
	        } 
	    } catch (SQLException | IOException e) {
	            System.out.println(e.getMessage());  
	    }
	    return conn;
	}
	
	public static ResultSet fetchData(String tableName) throws SQLException {
		ResultSet rs = null;
		Connection conn = connect();
		Statement stat = conn.createStatement();
		if("products".equalsIgnoreCase(tableName))
			rs = stat.executeQuery("SELECT * FROM "+tableName+" ORDER BY pname;");
		else
			rs = stat.executeQuery("SELECT * FROM "+tableName+" ORDER BY timestamp DESC;");
		return rs;	
	}
	
	public static void addItem(String table, String value) throws Exception {
		Connection conn = connect();
		Statement stat = conn.createStatement();
		String sql = "INSERT INTO "+table+" VALUES ('"+value+"');";
		stat.executeUpdate(sql);
	}

	public static void removeItem(String table, String columnName, String value) throws Exception {
		Connection conn = connect();
		Statement stat = conn.createStatement();
		String sql = " DELETE FROM "+table+" WHERE "+columnName+"='"+value+"';";
		stat.executeUpdate(sql);
	}
	
	public static ResultSet addProduct(String product,  int qty) throws Exception{
		Connection conn = connect();
		ResultSet rs = null;
		PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM products WHERE pname=? ;");
		ps1.setString(1, product);
		rs = ps1.executeQuery();
	          
		if (rs.next() == false) {
			PreparedStatement ps2 = conn.prepareStatement("INSERT INTO products VALUES (?,?);");
			ps2.setString(1, product);
			ps2.setInt(2, qty);
			ps2.executeUpdate();	
		} else {
			int updtdQty = Integer.parseInt(rs.getString("pqt")) + qty;
			PreparedStatement ps3 = conn.prepareStatement("UPDATE products SET pqt=? WHERE pname=? ;");
			ps3.setInt(1, updtdQty);
			ps3.setString(2, product);
			ps3.executeUpdate();
		}

		PreparedStatement ps4 = conn.prepareStatement("INSERT INTO prod_records VALUES (strftime('%d/%m/%Y %H:%M:%S','now','localtime'),?,?);");
		ps4.setString(1, product);
		ps4.setInt(2, qty);
		ps4.executeUpdate();
		Statement s1 = conn.createStatement();
		rs = s1.executeQuery("SELECT * FROM prod_records ORDER BY timestamp DESC;");
		
		return rs;
	}
	
	public static ResultSet sellProduct(String product,  int qty) throws Exception{
		Connection conn = connect();
		ResultSet rs = null;
		
		PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM products WHERE pname=? ;");
        ps1.setString(1, product);
		rs = ps1.executeQuery();
			
		if (rs == null) {
			System.out.println("Product Not Found!");
		} else {
			int updtdQty = Integer.parseInt(rs.getString("pqt")) - qty;
			PreparedStatement ps2 = conn.prepareStatement("UPDATE products SET pqt=? WHERE pname=?;");
			ps2.setInt(1, updtdQty);
			ps2.setString(2, product);
			ps2.executeUpdate();
		}

		PreparedStatement ps3 = conn.prepareStatement("INSERT INTO sold_records VALUES (strftime('%d/%m/%Y %H:%M:%S','now','localtime'),?,?);");
		ps3.setString(1, product);
		ps3.setInt(2, qty);
		ps3.executeUpdate();
		Statement s1 = conn.createStatement();
		rs = s1.executeQuery("SELECT * FROM sold_records ORDER BY timestamp DESC;");
	
		return rs;
	}
	
	public static ArrayList<String> fetchPList() {
		ResultSet rs = null;
		Connection conn = connect();
		ArrayList<String> pal = new ArrayList<String>();
		
		try {
			Statement s1 = conn.createStatement();
			rs = s1.executeQuery("SELECT * FROM product_list ORDER BY pname;");
			while(rs.next() != false) {
				pal.add(rs.getString("pname"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return pal;
	}
}