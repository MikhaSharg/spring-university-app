package ua.com.foxminded.university.model;

import java.util.List;

public class LecturesShedule {

    private List<Lecture> lectures;

    public LecturesShedule(List<Lecture> lectures) {

        this.lectures = lectures;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((lectures == null) ? 0 : lectures.hashCode());
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
        LecturesShedule other = (LecturesShedule) obj;
        if (lectures == null) {
            if (other.lectures != null)
                return false;
        } else if (!lectures.equals(other.lectures))
            return false;
        return true;
    }

}
