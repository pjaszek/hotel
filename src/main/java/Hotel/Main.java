package Hotel;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private UserService userService = new UserService();

    public static void main(String[] args) {

        Main main = new Main();
        do {
            main.displayMenu();
        } while (true);

    } //end main


    public void displayMenu() {

        System.out.println("\n======Hotel Menu=======");
        System.out.println(
                "Wybierz jedną z podanych opcji: \n" +
                        "1) Wyświetl listę pokoi wraz z ich statusem (wolny-zajęty) \n" +
                        "2) Wyświetl listę tylko dostępnych pokoi \n" +
                        "3) Zarezerwuj pokój \n" +
                        "4) Zwolnij pokój \n" +
                        "5) Wyświetl listę pokoi do posprzątania \n" +
                        "6) Wyświetl listę zajętych pokoi wraz z datą opuszczenia pokoju \n" +
                        "7) Wyjdź z programu"
        );
        selectOption();
    }

    public void selectOption() {
        try {
            int selection = scanner.nextInt();
            scanner.nextLine();
            switch (selection) {
                case 1:
                    System.out.println("Lista pokoi wraz z ich statusem (wolny-zajęty)");
                    userService.getRoomsAndStatus();
                    break;
                case 2:
                    System.out.println("Lista dostępnych pokoi");
                    userService.getAllAvailableRooms();
                    break;
                case 3:
                    System.out.println("Rezerwacja pokoju.");
                    userService.reserveRoomIfFreeAndOneAdultPresent();
                    break;
                case 4:
                    System.out.println("Zwolnienie pokoju");
                    userService.freeYourRoom();
                    break;
                case 5:
                    userService.printListOfDirtyRooms();
                    break;
                case 6:
                    System.out.println("Lista zajętych pokoi");
                    userService.printListOfOccupiedRooms();
                    break;

                case 7:
                    exit();
                default:
                    System.out.println("Proszę podać numer opcji od 1 do 4");
                    break;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Dopuszczalne są tylko cyfry od 1 do 4. Nie używaj innych znaków");
        }
    }

    private static void exit() {
        System.out.println("Wychodzę z programu");
        System.exit(1);
    }
}
