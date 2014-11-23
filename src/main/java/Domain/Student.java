package Domain;

public class Student {
	
	private Integer studentID;
	private String gender;
	private String stream;
	private String name;
	private String password;
	

	public Integer getStudentID() {
		return studentID;
	}


	public void setStudentID(Integer studentID) {
		this.studentID = studentID;
	}


	public String getStream() {
		return stream;
	}


	public void setStream(String stream) {
		this.stream = stream;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Student( Integer studentId ,String gender,String stream, String name, String password) {
		
		this.studentID = studentId;
		this.gender=gender;
		this.stream = stream;
		this.name = name;
		this.password = password;

	}


	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	

}
