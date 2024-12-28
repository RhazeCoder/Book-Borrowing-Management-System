package BookBorrowingManagement;

import java.io.IOException;
import java.util.Scanner;

import utils.Sys;

public class MainMenu {
    private static Scanner scan = new Scanner(System.in);
    private static Sys sys = new Sys();

    // INFO: menu
    AddBook addBook = new AddBook();
    ViewBook viewBook = new ViewBook();
    BorrowBook borrowBook = new BorrowBook();
    ViewBorrowedBook viewBorrowed = new ViewBorrowedBook();
    ReturnBook returnBook = new ReturnBook();
    Reports reports = new Reports();

    private void navigateMenu(char choice) throws IOException {
        switch (choice) {
            case '1':
                addBook.start();
                sys.pause();
                break;
            case '2':
                viewBook.start();
                sys.pause();
                break;
            case '3':
                borrowBook.start();
                sys.pause();
                break;
            case '4':
                viewBorrowed.start();
                sys.pause();
                break;
            case '5':
                returnBook.start();
                sys.pause();
                break;
            case '6':
                reports.start();
                sys.pause();
                break;
            case '7':
                System.out.println("System Closed!");
                scan.close();
                break;
            default:
                System.out.println("\nInvalid Input!");
                break;
        }
    }

    public void show() throws IOException {
        char choice;
        do {
            sys.cls();
            System.out.println("\n\t+|  MAIN MENU  |+");
            System.out.println("\t  [1] Add Book");
            System.out.println("\t  [2] View Inventory");
            System.out.println("\t  [3] Borrow Book");
            System.out.println("\t  [4] View Borrowed Book");
            System.out.println("\t  [5] Return Book");
            System.out.println("\t  [6] Report");
            System.out.println("\t  [7] Exit");
            System.out.print("\nEnter choice: ");
            choice = scan.next().charAt(0);
            sys.cls();
            navigateMenu(choice);
        } while (choice != '7');
    }
}
