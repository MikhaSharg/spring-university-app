package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.PersonDao;
import ua.com.foxminded.university.model.Person;

@Service
@Transactional (readOnly=true)
public class PersonService {

	private final PersonDao personDao;

	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

	Person savePerson(Person newPerson) {
		return personDao.save(newPerson);
	}

	public void deletePersonById(Long id) {
		personDao.deleteById(id);
	}

	public List<Person> findAllExistPersons() {
		return personDao.findAll();
	}

	public Optional<Person> findPersonById(Long personId) {
		return personDao.findById(personId);
	}

	public List<Person> saveAllPersons(List<Person> person) {
		return personDao.saveAll(person);
	}
}
