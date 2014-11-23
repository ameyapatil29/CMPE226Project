package Domain;

public class Subject {
	
	private Integer subjectID;
	private String subjectName;
	private Integer capacity;
	public Integer getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(Integer subjectID) {
		this.subjectID = subjectID;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	public Subject(Integer subjectID,String subjectName,Integer capacity){
		this.subjectID = subjectID;
		this.subjectName = subjectName;
		this.capacity = capacity;
	}
	public Subject() {
		// TODO Auto-generated constructor stub
	}


}
