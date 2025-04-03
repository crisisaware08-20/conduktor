package conduktor.people.model;

import java.util.List;

public class Person {
	public String _id;
	public String name;
	public String dob;
	public Address address;
	public String telephone;
	public List<String> pets;
	public double score;
	public String email;
	public String url;
	public String description;
	public boolean verified;
	public int salary;

	public static class Address {
		public String street;
		public String town;
		public String postode;
		@Override
		public String toString() {
			return "Address [street=" + street + ", town=" + town + ", postode=" + postode + "]";
		}
	}

	@Override
	public String toString() {
		return "Person [_id=" + _id + ", name=" + name + ", dob=" + dob + ", address=" + address + ", telephone="
				+ telephone + ", pets=" + pets + ", score=" + score + ", email=" + email + ", url=" + url + ", description="
				+ description + ", verified=" + verified + ", salary=" + salary + ", getClass()=" + getClass() + "]";
	}

}
