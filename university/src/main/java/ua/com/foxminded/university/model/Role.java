package ua.com.foxminded.university.model;

import java.util.Objects;

public class Role extends IdEntity {

	private String name;

	public Role(String name) {
		super(null);
		this.name = name;
	}

	public Role(Long id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", getId()=" + getId() + "]";
	}

}
