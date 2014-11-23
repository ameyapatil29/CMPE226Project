package com.AirBox.rest;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.AirBox.Dao.DbConnection;
import com.AirBox.Dao.ProductDao;
import com.AirBox.Domain.History;
import com.AirBox.Domain.Product;
import com.AirBox.Domain.UploadObject;
import com.AirBox.Domain.User;
import com.mongodb.DBObject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;



@Path("/file")
public class UploadFileService {
	
	
	
	/*
	 * REST API for accessing of user data 
	 */
	
	@POST
	@Path("/signup")
	public Response userprofile(@FormParam("fname") String fname,
			@FormParam("lname") String lname,
			@FormParam("email") String email, 
			@FormParam("password") String password,
			@Context HttpServletRequest req)  {
			System.out.println("name is: "+fname);
			User user = new User();
			user.setFirstName(fname);
			user.setLastName(lname);
			user.setUserName(email);
			user.setPassword(password);
			
			AWSFacade fact= new AWSFacade();
			System.out.println("surname of the user is"+user.getLastName());
			String output = "Thankyou for registring with us you will recieve email shortly "+ user.getFirstName();
			DbConnection dbcon = new DbConnection();
			
			HttpSession session= req.getSession(true);
			session.setAttribute("username", email);
			session.setAttribute("sessionId", session.getId());
			session.setAttribute("usersfirstname", fname);
			session.setAttribute("userslastname", lname);
			
			System.out.println("User added");
			
			
			dbcon.insertUser(user);
			
		
			

		return Response.status(200).entity(output).build();

	}

	@POST
	@Path("/login")
	public Response userLogin(@FormParam("username") String username, 
			@FormParam("password") String password, 
			@Context HttpServletRequest req) throws IOException {
			String output = "";
			String invalidUser = "Invalid User";
			
			System.out.println("Username is: "+username);
			DbConnection dbcon = new DbConnection();
			ProductDao mongodbcon = new ProductDao();
			
			ArrayList<Product> productDetails = new ArrayList<Product>();
			List<History> historydetails = new ArrayList<History>();
			
			User userDetailObject = new User();
			//List<UploadObject> fileDetails = new ArrayList<UploadObject>();
			List<UploadObject> shareperc = new ArrayList<UploadObject>();
			List<UploadObject> sharefiledetails = new ArrayList<UploadObject>();
			
			
			
			if(dbcon.loginCheck(username, password))
			{
			output = "Login Successful for "+ username;
			System.out.println("User Validated");
			HttpSession session= req.getSession(true);
			session.setAttribute("username", username);
			session.setAttribute("sessionId", session.getId());
			Date date = new Date();
			
			User user =	dbcon.getUserDetails(username);
			user.getFirstName();
			System.out.println("users firsname"+user.getFirstName());
			

			productDetails = mongodbcon.getProductDetails();
			historydetails=dbcon.getUserHistory(username);
			
			System.out.println("inside controller ");
			System.out.println("login "+ user.getLastloggedin());
			
			session.setAttribute("usersfirstname", user.getFirstName());
			session.setAttribute("userslastname", user.getLastName());
			session.setAttribute("lastloggedin", user.getLastloggedin());	
			session.setAttribute("productDetails", productDetails);
			session.setAttribute("history", historydetails);
			
			//System.out.println(historydetails.get(0).getProductname());
			//System.out.println(historydetails.get(0).getQuantity());
			//System.out.println(historydetails.get(0).getProductname());
			//System.out.println(historydetails.get(0).getProductname());
			
			//System.out.println("end of controller ");
			return Response.status(200).entity(output).build();
			}
			
			else
				System.out.println("User Invalid");
				return Response.status(400).entity(invalidUser).build();	

	}
	
	@POST
	@Path("/shoppingCart")
	public Response addCart(@FormParam("result") String result) throws IOException {
		System.out.println("Inside shoppingcart controller: " +result);
		String output = "success";
		return Response.status(200).entity(output).build();

	}
	
	
	
	 //Add to cart----
	@POST
    @Path("/cart/{productname}")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addtocart(@PathParam("productname") String productname,@Context HttpServletRequest req) throws IOException{
	//public Response addtocart(@FormParam("productname") String productname) throws IOException{
		
		System.out.println("inside cart controller");
		System.out.println("parameter "+productname);
		//System.out.println("inside cart controller");
		DbConnection dbcon = new DbConnection();
		ProductDao mongodbcon = new ProductDao();
		//String productname = "iphone6";
		Product product = new Product();
		product = mongodbcon.getProduct(productname);
		
		String rdsproductname = product.getProductname();
		String rdscatalogname =	product.getCatalogname();
		
		
		ArrayList<Product> productCartDetails = new ArrayList<Product>();
		
		HttpSession session= req.getSession(true);
		
		String username=(String) session.getAttribute("username");
		productCartDetails = (ArrayList<Product>) dbcon.getCartDetails(username);
		
		
		System.out.println("catalogname is " + product.getCatalogname());
		System.out.println("name is " + product.getProductname());
		
		dbcon.insertProduct(product,username);
		
		String output="added to cart";
		session.setAttribute("cart", productCartDetails);
    	return Response.status(200).entity(output).build();
		//String output = "success";
    	//return Response.status(200).entity(output).build();
    }
	
	//Remove From Cart
	@POST
    @Path("/removefromcart/{productname}")
	@Consumes(MediaType.APPLICATION_JSON)
    public Response removefromcart(@PathParam("productname") String productname,@Context HttpServletRequest req) throws IOException{
	//public Response addtocart(@FormParam("productname") String productname) throws IOException{
		
		System.out.println("inside remove cart controller");
		System.out.println("parameter "+productname);
		//System.out.println("inside cart controller");
		DbConnection dbcon = new DbConnection();
		//ProductDao mongodbcon = new ProductDao();
		//String productname = "iphone6";
		Product product = new Product();
		//List<Product> cartProducts = new ArrayList<Product>();
		//product = mongodbcon.getProduct(productname);
		HttpSession session= req.getSession(true);
		String username=(String) session.getAttribute("username");
		boolean trial = dbcon.removeCartItem(username, productname);
		
		//String rdsproductname = product.getProductname();
		//String rdscatalogname =	product.getCatalogname();
		
		
		

		
		
		//System.out.println("catalogname " + product.getCatalogname());
		//System.out.println("name " + product.getProductname());
		
		//dbcon.insertProduct(product,username);
		String output;
		if(trial)
		output="deleted from cart";
		else
			output = "Delete failed";
    	return Response.status(200).entity(output).build();
		//String output = "success";
    	//return Response.status(200).entity(output).build();
    }
	
	@POST
	@Path("/addproduct")
	public Response addProduct(@FormParam("pid") int pid,
			@FormParam("pname") String pname,@FormParam("cid") int cid,
			@FormParam("cname") String cname,
			@FormParam("description") String description, 
			@FormParam("price") float price,
			@FormParam("quantity") int quantity,
			@Context HttpServletRequest req) throws IOException {
		System.out.println("Inside addproduct controller: ");
		ProductDao mongodbcon = new ProductDao();
		Product prod= new Product();
		prod.setProductId(pid);
		prod.setProductname(pname);
		prod.setCatalogId(cid);
		prod.setcatalogname(cname);
		prod.setDescription(description);
		prod.setPrice(price);
		prod.setQuantity(quantity);
		String output = "error";

		boolean check=mongodbcon.addProduct(prod);
		if(check){
			output = "success";
		}
		return Response.status(200).entity(output).build();

	}
	
	@GET
	@Path("/refresh")
	public Response pageRefresh(@Context HttpServletRequest req) throws IOException {
		
		System.out.println("inside refresh controller");
		ProductDao dbcon = new ProductDao();
		
		HttpSession session= req.getSession(true);
		System.out.println("inside refresh");
		
		List<Product> productDetails = new ArrayList<Product>();
		List<History> historydetails = new ArrayList<History>();


		productDetails = dbcon.getProductDetails();
		System.out.println(productDetails);
		
		DbConnection db = new DbConnection();
		String username=(String)session.getAttribute("username");
		
		
		System.out.println("refresh...");
		historydetails=db.getUserHistory(username);
		System.out.println("history...");
		
		session.setAttribute("productDetails", productDetails);
		session.setAttribute("history", historydetails);
		
		//System.out.println(historydetails.get(0).getProductname());
		
		return Response.status(200).entity("success").build();

	}

	
	// --------------------
	
	@GET
	@Path("/logout")
	public Response logout(@Context HttpServletRequest req) {
		HttpSession session= req.getSession(false);
		session.invalidate();
		System.out.println("User has logged out !");
		String output = "User has succesfully logged out";
		
		 return Response.status(200).entity(output).build();
		
	}
	
}


