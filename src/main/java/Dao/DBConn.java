package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Domain.Student;
import Domain.Subject;

public class DBConn {
	private static String connectionString = "jdbc:mysql://localhost:3306/226_db";
	private static String dbUsername = "root";
	private static String dbPassword = "password";
	
	
	public boolean loginCheck (Integer studentID, String password){
		String query1;
		boolean login = false;
		
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
			Statement stmt1 = (Statement) con.createStatement();
			query1 = "SELECT studentID, password FROM Student WHERE studentID='" + studentID + "' AND password='" + password + "';";
			stmt1.executeQuery(query1);
			ResultSet rs = stmt1.getResultSet();
			login = rs.first();
			con.close();
		}
		catch (InstantiationException e) {
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
	
	
	public Student getStudentDetails (Integer studentID){
		String query;
		String gender;
		String stream;
		String name;
		String password;
		Student newStudent = new Student();
		try {
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
	        Statement stmt = (Statement) con.createStatement();
	       
	        query = "SELECT * FROM Student WHERE studentID ='"+studentID+"';";
	        ResultSet rs = stmt.executeQuery(query);
	       
	        
	        if ( rs.next() ) {
	        	gender = rs.getString("gender");
	    		stream = rs.getString("stream");
	    		name = rs.getString("name");
	    		password = rs.getString("password");
	    		
	    		newStudent.setGender(gender);
	    		newStudent.setStream(stream);
	    		newStudent.setName(name);
	    		newStudent.setPassword(password);
	    		newStudent.setStudentID(studentID);
	    
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
	    
	    return newStudent;
	}
	
	public ResultSet preReqFinder(Integer courseID){
        String myQuery1 , myQuery2 = null;
        ResultSet rs1,rs2 = null;
        //Subject newSubject = null;
        //Integer subjectID;
        //Integer capacity;
        //String subjectName;

        try {
            // check if the course is available for student to choose
        	Connection con = DriverManager.getConnection(connectionString, dbUsername, dbPassword);
	        Statement stmt = (Statement) con.createStatement();
            myQuery1 = "select * from Subject where subjectID = '"+courseID+"';";
            rs1 = stmt.executeQuery(myQuery1);
           
            //result = conn.createStatement().executeQuery(myQuery);

            if(rs1.next()==false){// course not available in the subject table
                return null;
            }else{// course available in the subject table, return prerequisite result;
                myQuery2 = "SELECT prereqID FROM Prerequisite WHERE subjectID = '"+courseID+"';";
                rs2 = stmt.executeQuery(myQuery2);
                //result = conn.createStatement().executeQuery(myQuery);
                /*if(rs2.next()){
                	String myQuery3 = "SELECT * FROM Subject WHERE subjectID = '"+courseID+"';";
                	ResultSet rs3 = stmt.executeQuery(myQuery3);
                	subjectName = rs3.getString("subjectName");
                	capacity = rs3.getInt("capacity");
                	newSubject = new Subject(courseID, subjectName, capacity);
                
                }else
                	System.out.println("No pre-requisite for this Subject");*/
            }
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs2;

    }

    private boolean preReqChecker(int studentID, ResultSet queryResult){
        String myQuery = null;
        ArrayList prereqCourses = new ArrayList();

        //parse queryResult
        try {
            while(queryResult.next()==true){
                prereqCourses.add(queryResult.getString(1));
            }
            System.out.println(prereqCourses);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //check if all returned queryResult are in the student enrollment table
        for(int i=0; i<prereqCourses.size(); i++){
            myQuery = "select "+enrolTable_grade+" from "+enrolTable
                    +" where "+enrolTable_studentID+"="+studentID
                    +" and " + enrolTable_subjectID+"=\""+prereqCourses.get(i)+"\"";
            try {
                result=conn.createStatement().executeQuery(myQuery);

                if(result.next()==false){   // if the prerequisite course(s) not taken by the student
                    return false;
                }else if(result.getString(enrolTable_grade).matches("F")||result.getString(enrolTable_grade).matches("IPG")){ // if the student failed one prerequisite course
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean enrollCourse(int studentID, String courseID){
        // insert into enrollment values (studentID, subjectID, grade)
        String myQuery = "insert into "+enrolTable+" values (" +studentID+", \""+courseToEnroll+"\", \"IPG\")";
        System.out.println(myQuery);
        try {
            conn.createStatement().executeUpdate(myQuery);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	

}
