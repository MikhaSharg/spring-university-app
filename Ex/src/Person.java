import java.util.Objects;

public class Person {

String name; 
String gender;
public Person(String name, String gender) {
	super();
	this.name = name;
	this.gender = gender;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
@Override
public int hashCode() {
	return Objects.hash(gender, name);
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Person other = (Person) obj;
	return Objects.equals(gender, other.gender) && Objects.equals(name, other.name);
}


}
