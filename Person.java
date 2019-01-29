/* This class creates a Person object that is used in the SQL database.
 * 
 */
public class Person {
	
	private String firstName;
	private String lastName;
	private int age;
	private long ssn;
	private long creditCard;
	
	Person(String firstName, String lastName, int age, long ssn, long creditCard) {
		setFirstName(firstName);
		setLastName(lastName);
		setAge(age);
		setSSN(ssn);
		setCreditCard(creditCard);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	public void setSSN(long ssn) {
		this.ssn = ssn;
	}
	public long getSSN() {
		return ssn;
	}
	public void setCreditCard(long creditCard) {
		this.creditCard = creditCard;
	}
	public long getCreditCard() {
		return creditCard;
	}
	public String toString() {
		return "This person's name is " + firstName + " " + lastName + ", they are age " + age + ". Their SSN is " + ssn + " and their credit card number is " + creditCard + ".";
	}
}