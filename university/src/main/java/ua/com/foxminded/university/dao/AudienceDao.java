package ua.com.foxminded.university.dao;

import java.time.LocalDate;
import java.util.List;

import ua.com.foxminded.university.model.Audience;

public interface AudienceDao extends CrudDao<Audience, Long> {
	
	List<Audience> findAllFreeAudiencesByDateAndSession (LocalDate date, Long SessinId);
	
}
