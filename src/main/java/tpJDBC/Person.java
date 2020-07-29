package tpJDBC;

import java.util.Date;

public class Person {
	private Integer id;
	private String first_name;
	private String last_name;
	private Date dob;
	
	/**
	 * @param id
	 * @param first_name
	 * @param last_name
	 * @param dob
	 */
	public Person(int id, String first_name, String last_name, Date dob) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
	}
	
	/**
	 * @param first_name
	 * @param last_name
	 * @param dob
	 */
	public Person(String first_name, String last_name, Date dob) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.dob = dob;
	}

	/**
	 * 
	 */
	public Person() {
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", dob=" + dob + "]";
	}
}
