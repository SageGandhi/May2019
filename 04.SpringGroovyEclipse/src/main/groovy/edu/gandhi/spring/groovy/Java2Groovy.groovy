package edu.gandhi.spring.groovy

@groovy.transform.ToString()
class Java2Groovy {
	Long id;
	String firstName;
	String lastName;
	String email;
	Date dob;

	public User(String first,String last,String email,Date dob) {
		this.firstName = first;
		this.lastName = last;
		this.email = email;
		this.dob = dob;
	}

	public void printFullName(){
		println "FullName: $firstName $lastName"
	}
}
