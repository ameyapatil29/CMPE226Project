package Dao;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;


public class DbConnection {
	
	private static String connectionString = "jdbc:mysql://localhost:3306/226_db";
	private static String dbUsername = "root";
	private static String dbPassword = "password";
	
	

	
public boolean loginCheck(String studentID, String password){
    String query1,query2,query3;
    boolean login = false;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date date = new Date();
	String login_date =date.toString() ;

    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt1 = (Statement) con.createStatement();
        //Statement stmt2 = (Statement) con.createStatement();
        //Statement stmt3 = (Statement) con.createStatement();
        query1 = "SELECT studentID, password FROM Student WHERE studentID='" + studentID + "' AND password='" + password + "';";
        //query2 = "update user_details set last_logged_in = login_date where username='" + username + "';";
        //query3 = "update user_details set login_date = '" + login_date + "' where username='" + username + "';";
        //System.out.println("logindate "+login_date );
        stmt1.executeQuery(query1);
        //stmt2.executeUpdate(query2);
        //stmt3.executeUpdate(query3);
        ResultSet rs = stmt1.getResultSet();
        //System.out.println("result "+query2);
        login = rs.first(); //rs.first();
        con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return login;
}



public User getUserDetails (String uname){
	
	String query;
	String first_name, last_name,username, password;
	String last_logged_in;
	User userD = new User();
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
       
        query = "SELECT * FROM user_details WHERE username ='"+uname+"';";
        ResultSet rs = stmt.executeQuery(query);
       
        
        if ( rs.next() ) {
            
        	 first_name = rs.getString("first_name");
        	 last_name = rs.getString("last_name");
        	 username = rs.getString("username");
        	 password = rs.getString("password");
        	 last_logged_in = rs.getString("last_logged_in");
             userD.setFirstName(first_name);
             userD.setLastName(last_name);
             userD.setUserName(username);
             userD.setPassword(password);
             userD.setLastloggedin(last_logged_in);
        } else{
        	return null;
        }
        con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    //return bname;
    return userD;
	
}

public List<History> getUserHistory (String username){
	
	
	String query;
	String productname;
	String catalogname;
	Float price;
	int quantity;
	String purchase_date;
	List<History> userHistoryList = new ArrayList<History>();	

    try {
    	
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "SELECT * FROM user_history WHERE username ='"+username+"';";
        ResultSet rs = stmt.executeQuery(query);
       
        
       while(rs.next()){
        		
    	   		productname = rs.getString("productname");
        		catalogname = rs.getString("catalogname");
        		price = rs.getFloat("price");
        		quantity =rs.getInt("quantity");
        		purchase_date =rs.getString("purchase_date");
        		History userHistoryObject = new History();
        		userHistoryObject.setProductname(productname);
        		userHistoryObject.setcatalogname(catalogname);
        		userHistoryObject.setPrice(price);
        		userHistoryObject.setPurchase_date(purchase_date);
        		userHistoryList.add(userHistoryObject);
        	}
       //System.out.println("Productname " + userHistoryList.get(0).getProductname());
       con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
    return userHistoryList;
	
}


public List<Product> getCartDetails (String username){
	
	
	String query;
	String productname;
	String catalogname;
	Float price;
	int quantity;
	String purchase_date;
	String description = "Product Description";
	List<Product> userCartList = new ArrayList<Product>();	

    try {
    	
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "SELECT * FROM cart_details WHERE username ='"+username+"';";
        ResultSet rs = stmt.executeQuery(query);
       
        
       while(rs.next()){
        		
    	   		productname = rs.getString("productname");
        		catalogname = rs.getString("catalogname");
        		price = rs.getFloat("price");
        		
        		quantity =rs.getInt("quantity");
        		purchase_date =rs.getString("purchase_date");
        		description=rs.getString("description");
        		Product userCartProducts = new Product();
        		userCartProducts.setProductname(productname);
        		userCartProducts.setcatalogname(catalogname);
        		userCartProducts.setPrice(price);
        		userCartProducts.setDescription(description);
        		userCartProducts.setDate(purchase_date);
        		userCartList.add(userCartProducts);
        	}
   
       con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    
    return userCartList;
	
}



public boolean removeCartItem(String username, String productname) {
	// TODO Auto-generated method stub
	//ArrayList<Product> userCartDetails = (ArrayList<Product>) getCartDetails(username);
	
	String query;
	try {
    	
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "DELETE FROM cart_details WHERE username ='"+username+"' AND productname ='"+productname+"';";
        //ResultSet rs = stmt.executeQuery(query);
       stmt.executeUpdate(query);
       
        
       /*while(rs.next()){
        		
    	   		//productname = rs.getString("productname");
        		catalogname = rs.getString("catalogname");
        		price = rs.getFloat("price");
        		
        		quantity =rs.getInt("quantity");
        		purchase_date =rs.getDate("purchase_date");
        		description=rs.getString(description);
        		Product userCartProducts = new Product();
        		userCartProducts.setProductname(productname);
        		userCartProducts.setcatalogname(catalogname);
        		userCartProducts.setPrice(price);
        		userCartProducts.setDescription(description);
        		userCartProducts.setDate(purchase_date);
        		userCartList.add(userCartProducts);
        	}*/
       System.out.println("Productname " +productname+" Deleted successfully for user "+(String) username);
       //con.close();
       con.close();
       
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
	
	
	
	return true;
}

public void insertUser(User user){
    String query;
    Date date = new Date();
    try {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        Statement stmt = (Statement) con.createStatement();
        query = "INSERT into user_details (first_name, last_name, username, password,login_date,last_logged_in) values ('"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getUserName()+"','"+user.getPassword()+"','"+date.toString()+"','"+date.toString()+"')";
        stmt.executeUpdate(query);
        System.out.println(date.toString());
        con.close();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void insertProduct(Product product, String username){
        String query;
        Date date = new Date();
    	
    	
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
            Statement stmt = (Statement) con.createStatement();
            query = "INSERT into cart_details (username, productname, catalogname,description, price,quantity,purchase_date) values ('"+username+"','"+product.getProductname()+"','"+product.getCatalogname()+"','"+product.getDescription()+"','"+product.getPrice()+"','"+product.getQuantity()+"','"+date.toString()+"')";
            stmt.executeUpdate(query);
            System.out.println(" addto cart in db successful");
            con.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }  
}


       

   




}

