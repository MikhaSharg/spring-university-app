package ua.com.foxminded.university.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.AudienceDao;
import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.Subject;

@Service
@Transactional(readOnly = true)
public class AudienceService {

private final AudienceDao audienceDao;	
	
private static final Logger log = LoggerFactory.getLogger(LectureService.class);

public AudienceService(AudienceDao audienceDao) {
	super();
	this.audienceDao = audienceDao;
}

public Audience findAudienceById (Long id) {
	Optional<Audience> audience = audienceDao.findById(id);
	
		if (audience.isPresent()) {
			log.info("Finded audience {}, {}", audience.get().getId(), audience.get().getRoomNumber());
		} else {
			log.warn("Could not find audiece with ID {}", id);
		}
		return audience.get();
	}

}
