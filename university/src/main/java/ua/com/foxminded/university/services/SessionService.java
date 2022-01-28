package ua.com.foxminded.university.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.LectureSessionsDao;
import ua.com.foxminded.university.model.LectureSessions;

@Service
@Transactional(readOnly = true)
public class SessionService {

	private final LectureSessionsDao lectureSessionsDao;

	private static final Logger log = LoggerFactory.getLogger(SessionService.class);

	public SessionService(LectureSessionsDao lectureSessionsDao) {
		super();
		this.lectureSessionsDao = lectureSessionsDao;
	}

	public LectureSessions findSessiontById(Long sessionId) {
		Optional<LectureSessions> session = lectureSessionsDao.findById(sessionId);
		if (session.isPresent()) {
			log.info("Finded session {}, {}, {}", session.get().getPeriod(), session.get().getStartTime(),
					session.get().getEndTime());
		} else {
			log.warn("Could not find session {}, {}, {}", session.get().getPeriod(), session.get().getStartTime(),
					session.get().getEndTime());
		}
		return session.get();
	}

}
