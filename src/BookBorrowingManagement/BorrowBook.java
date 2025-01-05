package BookBorrowingManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import utils.FileSystem;
import utils.Sys;
import utils.ValidateInput;

public class BorrowBook {
  private static BookDetails bookDetails = new BookDetails();
  private static FileSystem fs = new FileSystem();
  private static Scanner scan = new Scanner(System.in);
  private static Sys sys = new Sys();
  private static ValidateInput validate = new ValidateInput();

  private static boolean isBorrowed = false;

  private static String addData() throws IOException {
    String studentId = validate.studentId("Enter Student ID: ");
    boolean studentIdExist = validate.checkStudentExistance(studentId);

    if (studentIdExist) {
      System.out.println("\nStudent with that id already exist!");
      return "existed";
    }

    String studentName = validate.prompt("Enter Student Name: ");

    Map<String, Integer> books = new HashMap<>();
    char addMore = 'x';
    int bitems = 0;
    do {
      String bookId = validate.prompt("Enter Book ID: ");
      boolean bookIdExist = validate.checkExistance(bookId);

      if (!bookIdExist) {
        System.out.println("\nBook with that id does not exist!");
        if (bitems == 0)
          isBorrowed = false;
      } else {
        bookDetails.show(bookId.trim());

        int bookQuantity = validate.checkQuantity("Quantity to Borrow: ", bookId);

        if (bookQuantity >= 0) {
          books.put(bookId, books.getOrDefault(bookId, 0) + bookQuantity);
          System.out.println("Book added!");
          bitems++;
          isBorrowed = true;
        }
      }

      System.out.print("\nAdd more book? (y, n): ");
      addMore = scan.next().charAt(0);
    } while (addMore == 'y');

    StringBuilder booksData = new StringBuilder();
    for (Map.Entry<String, Integer> entry : books.entrySet()) {
      booksData.append(entry.getKey()).append("=").append(entry.getValue()).append("|");
    }

    String data = studentId + "\n" + studentName + "\n" + booksData + "\n" + LocalDate.now()
        + "\n=====";

    return data;
  }

  public void start() throws IOException {
    boolean addMore = false;

    do {
      String data = addData();
      if (!data.equals("existed")) {
        if (isBorrowed) {
          fs.writeBorrow(data);
          System.out.println("\nRecord saved!");
        } else {
          System.out.println("\nNo changes made!");
        }
      }
      System.out.print("\nAdd more student? (y/n): ");
      char choice = scan.next().charAt(0);
      addMore = (choice == 'y');
      sys.cls();
    } while (addMore);
  }
}
