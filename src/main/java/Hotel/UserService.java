package Hotel;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserService {
    private Hotel hotel = new Hotel();
    private List<Room> rooms = hotel.getRooms();
    private List<Guest> temporaryGuestsList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);


    //1. Pobierz listę wszystkich pokoi wraz z ich statusem (wolny-zajęty)
    public List<Room> getRooms() {
        return rooms;
    }

    public void getRoomsAndStatus() {
        rooms.stream()
                .forEach(room -> System.out.println("Numer pokoju: " + room.getRoomNumber() + " Dostępny? = " + room.isAvailable()));
    }

    //2. Pobierz listę wszystkich dostępnych pokoi. Nieposprzątanego pokoju nie można zarezerwować pomimo że jest wolny.
    public void getAllAvailableRooms() {
        int availableCount = 0;
        System.out.println("Oto lista dostępnych pokoi oraz liczba osób w każdym pokoju");
        for (Room room : rooms) {
            if (room.isAvailable() && room.isClean()) {
                System.out.println("Pokój numer: " + room.getRoomNumber() + "  Ile osób: " + room.getHowManyPeople());
                availableCount++;
            }
        }
        if (availableCount == 0) {
            System.out.println("Wszystkie pokoje są obecnie zajęte lub nieposprzątane");
        }

       /* return rooms.stream()
                .filter(room -> room.isAvailable() && room.isClean())
                .map(room -> room.getRoomNumber())
                .collect(Collectors.toList());*/
    }

    public List<Room> printListOfDirtyRooms() {
        List<Room> dirtyRooms = rooms.stream()
                .filter(room -> !room.isClean())
                .collect(Collectors.toList());
        if (dirtyRooms.isEmpty()) {
            System.out.println("W tym momencie nie ma pokoi do sprzątania");
            return null;
        }
        System.out.println("Lista nieposprzątanych pokoi:\n" + dirtyRooms);
        System.out.println("Który pokój chcesz posprzątać? Podaj -1, jeśli nie chcesz sprzątać pokoi.");
        int roomToClean = scanner.nextInt();
        scanner.nextLine();
        if (roomToClean == -1) {
            return null;
        }
        cleanTheRoom(roomToClean);
        return dirtyRooms;

    }

    public void cleanTheRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.setClean(true);
                System.out.println("Pokój numer " + room.getRoomNumber() + " został posprzątany");
            }
        }
    }


    //3. Rezerwuj pokój (podaj nr pokoju & gości - jeśli jest dostępny i przynajmniej jedna osoba jest pełnoletnia to
    // go zarezerwuj).

    public void reserveRoomIfFreeAndOneAdultPresent() {
        checkIfAtLeastOneAdult(createGuestList());

    }

    public List<Guest> createGuestList() {
        System.out.println("\nDla każdego gościa, którego chcesz zameldować, podaj imię i datę urodzenia. Minimum " +
                "jedna" +
                " osoba musi być pełnoletnia.\n" +
                "Gdy skończysz " +
                "podawać dane gości, wpisz -1\n");
        do {
            System.out.println("Podaj imię i nazwisko gościa");
            String guestName = scanner.nextLine();
            if (guestName.equals("-1")) {
                return temporaryGuestsList;
            }

            try {
                System.out.println("Podaj urodziny gościa w formacie YYYY-MM-DD");
                String guestBirthday = scanner.nextLine();
                Guest guest = new Guest(guestName, guestBirthday);
                temporaryGuestsList.add(guest);
            } catch (DateTimeParseException e) {
                System.out.println("Data urodzenia musi być w formacie YYYY-MM-DD");
            }

        } while (true);
    }

    public void checkIfAtLeastOneAdult(List<Guest> guests) {
        int countAdults = 0;
        LocalDate todayDate = LocalDate.now();
        for (Guest guest : guests) {
            LocalDate dateOfBirth = guest.getBirthday();
            Period age = Period.between(dateOfBirth, todayDate);
            if (age.getYears() >= 18) {
                countAdults++;
            }
        }
        if (countAdults > 0) {
            System.out.println("Przynajmniej jedna osoba jest pełnoletnia. Sprawdzam, czy pokój jest dostępny.");
            reserveRoomIfFree();
        } else {
            System.out.println("Wśród gości powinna być minimum jedna osoba dorosła. Nie można zarezerwować pokoju");
        }

    }

    //Nieposprzątanego pokoju nie można zarezerwować pomimo że jest wolny

    public void reserveRoomIfFree() {
        System.out.println("Podaj numer pokoju, który chcesz zarezerwować.");
        getAllAvailableRooms();
        int roomNumber = Integer.parseInt(scanner.next());
        scanner.nextLine();
        for (Room room : rooms) {
            if (room.isAvailable() && room.isClean() && room.getRoomNumber() == roomNumber) {
                System.out.println("Podaj datę zameldowania w formacie YYYY-MM-DD");
                String checkInDate = scanner.nextLine();
                System.out.println("Podaj datę wymeldowania w formacie YYYY-MM-DD");
                String checkOutDate = scanner.nextLine();
                room.setCheckInDate(checkInDate);
                room.setCheckOutDate(checkOutDate);
                room.setAvailable(false);
                room.setGuestsInRoom(temporaryGuestsList);
                temporaryGuestsList = new ArrayList<>();
                System.out.println("Zarezerwowano pokój numer " + room.getRoomNumber());
                return;
            }
        }
        System.out.println("Nie dało się zarezerwować podanego numeru pokoju, bo był nieposprzątany lub zajęty");
    }


    //4. Zwolnij pokój (podaj nr pokoju i jesli jest zajety, to go zwolnij). Jeśli gość się wymelduje, pokój zmienia
    // status na nieposprzątany.

    public void freeYourRoom() {
        System.out.println("Podaj numer pokoju, który chcesz zwolnić");
        int roomNumber = Integer.parseInt(scanner.next());
        scanner.nextLine();
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isAvailable()) {
                room.setAvailable(true);
                room.setClean(false);
                room.setGuestsInRoom(null); //nie ma gości w pokoju po wymeldowaniu
                System.out.println("Zwolniono pokój numer: " + room.getRoomNumber());
                return;
            }
        }
        System.out.println("Pokoju numer " + roomNumber + " nie dało się zwolnić, bo nie był zajęty");
    }

    public void printListOfOccupiedRooms() {
        rooms.stream()
                .filter(room -> !room.isAvailable())
                .forEach(room -> System.out.println("Pokój numer: " + room.getRoomNumber() + " Data wymeldowania: " + room.getCheckOutDate()));

    }

    @Override
    public String toString() {
        return "UserService{" +
                "hotel=" + hotel +
                ", rooms=" + rooms +
                '}';
    }

}

