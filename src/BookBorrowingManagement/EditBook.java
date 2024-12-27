package BookBorrowingManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import utils.FileSystem;
import utils.ValidateInput;

public class EditBook {
  private static FileSystem fs = new FileSystem();
  private static Scanner scan = new Scanner(System.in);
  private static ValidateInput validate = new ValidateInput();

  private static List<String> getBooks(List<String> records) throws IOException {
    List<String> books = new LinkedList<>();
    StringBuilder book = new StringBuilder();

    for (String record : records) {
      if (record.equals("=====")) {
        books.add(book.toString());
        book.setLength(0);
      } else {
        book.append(record).append("\n");
      }
    }

    if (book.length() > 0) {
      books.add(book.toString().trim());
    }

    return books;
  }

  private static void editBook() throws IOException {
    String bookId = validate.prompt("Enter book ID to edit: ");
    boolean bookIdExist = validate.checkExistance(bookId);

    if (!bookIdExist) {
      System.out.println("Book ID does not exist.");
      return;
    }

    String title = validate.prompt("Enter Book Title: ");
    String author = validate.prompt("Enter Book Author: ");
    String genre = validate.prompt("Enter Book Genre: ");
    LocalDate datePublished = validate.promptDate("Enter Publication Date: ");
    int quantity = validate.promptInt("Enter Book Quantity: ");
    LocalDate dateNow = LocalDate.now();

    String newDetails = bookId + "\n" + title + "\n" + author + "\n" + genre + "\n" + datePublished + "\n" + quantity
        + "\n"
        + dateNow;

    List<String> records = List.of(fs.read());
    List<String> books = getBooks(records);

    for (int i = 0; i < books.size(); i++) {
      String[] book = books.get(i).split("\n");
      String bookID = book[0];

      if (bookID.equalsIgnoreCase(bookId)) {
        books.set(i, newDetails.toString().trim());
        break;
      }
    }

    List<String> updatedRecords = new ArrayList<>();
    for (int i = 0; i < books.size(); i++) {
      String book = books.get(i).trim();
      updatedRecords.add(book);
      updatedRecords.add("=====");
    }

    System.out.println("Book Information updated successfully");

    fs.writeEdit(updatedRecords);
  }

  public void askEdit() throws IOException {
    System.out.print("Do you want to edit a book? (y/n): ");
    char choice = scan.next().charAt(0);
    if (choice == 'y') {
      editBook();
    }
  }
}
