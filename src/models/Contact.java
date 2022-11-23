package models;

public class Contact {
	private int id; 
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	
	public Contact() {}
	
	public Contact(String firstName, String lastName, String phone, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	public Contact(int id, String firstName, String lastName, String phone, String email) {
		this(firstName, lastName, phone, email);
		setId(id);
	}
	
	public Contact(String firstName, String lastName, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}
	
	public Contact(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		String s = id+" "+ firstName+ " "+lastName;
		if(phone!=null && !phone.trim().equals("")) {
			s+= " - "+ phone;
		}
		if(email!=null && !email.trim().equals("")) {
			s+= " - "+ email;
		}
		return s;
	}
	
}








