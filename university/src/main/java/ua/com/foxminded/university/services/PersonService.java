package ua.com.foxminded.university.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.PersonDao;
import ua.com.foxminded.university.model.Person;

@Service
@Transactional(readOnly = true)
public class PersonService {

	private final PersonDao personDao;
	private final Logger log = LoggerFactory.getLogger(PersonService.class);

	public PersonService(PersonDao personDao) {
		this.personDao = personDao;
	}

	Person savePerson(Person newPerson) {
		Person person = personDao.save(newPerson);
		if (newPerson.getId() != null) {
			log.info("Updated person {}, {}, {}", person.getId(), person.getFirstName(), person.getLastName());
		} else {
			log.info("Saved person {}, {}, {}", person.getId(), person.getFirstName(), person.getLastName());
		}

		return person;
	}

	public void deletePersonById(Long id) {
		personDao.deleteById(id);
		log.info("Deleted person {}", id);
	}

	public List<Person> findAllExistPersons() {
		List<Person> persons = personDao.findAll();
		if (!persons.isEmpty()) {
			log.info("Finded {} persons", persons.size());
		} else {
			log.warn("Could not find any persons");
		}
		return persons;
	}

	public Optional<Person> findPersonById(Long personId) {
		Optional<Person> person = personDao.findById(personId);
		if (person.isPresent()) {
			log.info("Finded person {}, {}, {}", person.get().getId(), person.get().getFirstName(),
					person.get().getLastName());
		} else {
			log.warn("Could not find person with Id {}", personId);
		}

		return person;
	}
}
