package BookBorrowingManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

import utils.FileSystem;
import utils.Sys;
import utils.ValidateInput;

public class AddBook {
  private static FileSystem fs = new FileSystem();
  private static Scanner scan = new Scanner(System.in);
  private static Sys sys = new Sys();
  private static ValidateInput validate = new ValidateInput();

  private static String addData() throws IOException {
    String bookId = validate.prompt("Enter Book ID: ");
    boolean bookIdExist = validate.checkExistance(bookId);

    if (bookIdExist) {
      System.out.println("\nBook with that id already exist!");
      return "existed";
    }

    String title = validate.prompt("Enter Book Title: ");
    String author = validate.prompt("Enter Book Author: ");
    String genre = validate.prompt("Enter Book Genre: ");
    LocalDate datePublished = validate.promptDate("Enter Publication Date: ");
    int quantity = validate.promptInt("Enter Book Quantity: ");
    LocalDate dateNow = LocalDate.now();

    String data = bookId + "\n" + title + "\n" + author + "\n" + genre + "\n" + datePublished + "\n" + quantity + "\n"
        + dateNow + "\n=====";

    return data;
  }

  public void start() throws IOException {
    boolean addMore = false;

    do {
      String data = addData();
      if (!data.equals("existed")) {
        fs.write(data);
        System.out.println("\nRecord saved!");
      }
      System.out.print("\nAdd more book? (y/n): ");
      char choice = scan.next().charAt(0);
      addMore = (choice == 'y') ? true : false;
      sys.cls();
    } while (addMore);
  }
}
