package Domain;

public class Teacher {

	private Integer tID;
	private String name;
	public Integer gettID() {
		return tID;
	}
	public void settID(Integer tID) {
		this.tID = tID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Teacher(Integer tid,String name){
		this.tID = tid;
		this.name = name;
	}
}
