package Hotel;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Room> rooms = new ArrayList<>();


    public Hotel() {
//this constructor runs every time a Hotel instance is created
        Room room1 = new Room(1, 1, true, true);
        Room room2 = new Room(2, 3, false, true);
        Room room3 = new Room(3, 4, true, true);
        Room room4 = new Room(4, 2, true, true);
        Room room5 = new Room(5, 3, true, true);
        Room room6 = new Room(6, 4, true, true);
        Room room7 = new Room(7, 2, true, true);
        Room room8 = new Room(8, 3, true, true);
        Room room9 = new Room(9, 4, true, true);


        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
        rooms.add(room8);
        rooms.add(room9);

    }

    public List<Room> getRooms() {
        return rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "rooms=" + rooms +
                '}';
    }
}