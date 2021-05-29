package Hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomNumber;
    private int howManyPeople;
    private boolean hasBathroom;
    private boolean isAvailable;
    private List<Guest> guestsInRoom = new ArrayList<>();
    private boolean clean = true;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;


    public Room(int roomNumber, int howManyPeople, boolean hasBathroom, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.howManyPeople = howManyPeople;
        this.hasBathroom = hasBathroom;
        this.isAvailable = isAvailable;
    }


    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getHowManyPeople() {
        return howManyPeople;
    }

    public void setGuestsInRoom(List<Guest> guestsInRoom) {
        this.guestsInRoom = guestsInRoom;
    }

    public boolean isClean() {
        return clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = LocalDate.parse(checkInDate);
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = LocalDate.parse(checkOutDate);
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", howManyPeople=" + howManyPeople +
                ", hasBathroom=" + hasBathroom +
                ", isAvailable=" + isAvailable +
                ", guestsInRoom=" + guestsInRoom +
                ", clean=" + clean +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}