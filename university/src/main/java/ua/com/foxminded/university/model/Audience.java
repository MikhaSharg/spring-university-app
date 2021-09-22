package ua.com.foxminded.university.model;

public class Audience extends IdEntity {
    
    private Integer roomNumber;

    public Audience(Long id, Integer roomNumber) {
        super(id);
        this.roomNumber = roomNumber;
    }

    public Audience(Integer roomNumber) {
        super(null);
        this.roomNumber = roomNumber;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((roomNumber == null) ? 0 : roomNumber.hashCode());
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
        Audience other = (Audience) obj;
        if (roomNumber == null) {
            if (other.roomNumber != null)
                return false;
        } else if (!roomNumber.equals(other.roomNumber))
            return false;
        return true;
    }

    
    
}
