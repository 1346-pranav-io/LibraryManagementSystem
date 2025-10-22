import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> library.addBook();
                case 2 -> library.viewBooks();
                case 3 -> library.issueBook();
                case 4 -> library.returnBook();
                case 5 -> {
                    System.out.println("Exiting... 👋");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
