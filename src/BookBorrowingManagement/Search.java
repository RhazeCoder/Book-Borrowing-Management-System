package BookBorrowingManagement;

import java.util.*;
import java.io.*;

import utils.FileSystem;
import utils.ValidateInput;

public class Search {
  private static Scanner scan = new Scanner(System.in);
  private static FileSystem fs = new FileSystem();
  private static ValidateInput validate = new ValidateInput();

  public void searchBook() throws IOException {
    List<String> records = List.of(fs.read());

    if (records.size() <= 0) {
      System.out.println("No records!\n");
      return;
    }

    int bookId = validate.promptInt("Enter book ID to search: ");
    boolean bookIdExist = validate.checkExistance(String.valueOf(bookId));

    if (!bookIdExist) {
      System.out.println("Book ID does not exist!\n");
      return;
    }

    List<String> books = new ArrayList<>();
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

    for (String record : books) {
      String[] lines = record.split("\n");
      if (lines.length > 0) {
        String bookID = lines[0].trim();
        if (bookID.equalsIgnoreCase(String.valueOf(bookId))) {
          System.out.println("============== Book Information ==============");
          System.out.println("Book ID: " + bookID);
          System.out.println("Title: " + lines[1]);
          System.out.println("Author: " + lines[2]);
          System.out.println("Genre: " + lines[3]);
          System.out.println("Date Published: " + lines[4]);
          System.out.println("Book Quantity: " + lines[5]);
          System.out.println("Book Added: " + lines[6]);
          System.out.println("=============================================\n");
          System.out.println();
        }
      }
    }
  }

  public void searchBorrowed() throws IOException {
    List<String> records = List.of(fs.readBorrow());

    if (records.size() <= 0) {
      System.out.println("No records!\n");
      return;
    }
    String studentId = validate.studentId("Enter student ID to search: ");
    boolean studentIdExist = validate.checkStudentExistance(studentId);
    if (!studentIdExist) {
      System.out.println("Student does not exist!\n");
      return;
    }
    List<String> books = new ArrayList<>();
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
    for (String record : books) {
      String[] lines = record.split("\n");
      if (lines.length > 0) {
        String studentID = lines[0].trim();
        if (studentID.equalsIgnoreCase(String.valueOf(studentId))) {
          System.out.println("============== Borrower Information ==============");
          System.out.println("Student ID: " + lines[0]);
          System.out.println("Student Name: " + lines[1]);
          System.out.println("Borrowed Book/s: ");
          String[] borrowInfo = lines[2].split("\\|");
          int totalBook = 0;
          for (int i = 0; i < borrowInfo.length; i++) {
            String info = borrowInfo[i];
            String[] individualInfo = info.split("=");
            System.out.println("  Book ID: " + individualInfo[0]);
            System.out.println("  Quantity: " + individualInfo[1] + "\n");
            totalBook += Integer.parseInt(individualInfo[1]);
          }
          System.out.println("Total Borrowed Book/s: " + totalBook);
          System.out.println("Date Borrowed: " + lines[3]);
          System.out.println("==================================================\n");
        }
      }
    }
  }

  public void searchReports() throws IOException {
    List<String> records = List.of(fs.readReports());

    if (records.size() <= 0) {
      System.out.println("No records!\n");
      return;
    }

    String studentId = validate.studentId("Enter book ID to search: ");
    boolean studentIdExist = validate.checkReportExistance(studentId);

    if (!studentIdExist) {
      System.out.println("Student does not exist!\n");
      return;
    }
    List<String> books = new ArrayList<>();
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
    for (String record : books) {
      String[] lines = record.split("\n");
      if (lines.length > 0) {
        String studentID = lines[0].trim();
        if (studentID.equalsIgnoreCase(String.valueOf(studentId))) {
          System.out.println("================== Reports ==================");
          System.out.println("Student ID: " + lines[0]);
          System.out.println("Student Name: " + lines[1]);
          System.out.println("Borrowed Book/s: ");
          String[] borrowInfo = lines[2].split("\\|");
          int totalBook = 0;
          for (int i = 0; i < borrowInfo.length; i++) {
            String info = borrowInfo[i];
            String[] individualInfo = info.split("=");
            System.out.println("  Book ID: " + individualInfo[0]);
            System.out.println("  Quantity: " + individualInfo[1] + "\n");
            totalBook += Integer.parseInt(individualInfo[1]);
          }
          System.out.println("Total Borrowed Book/s: " + totalBook);
          System.out.println("Date Borrowed: " + lines[3]);
          System.out.println("Over Due Payment Type: " + lines[4]);
          System.out.println("Overdue Amount: " + lines[5]);
          System.out.println("Date Returned: " + lines[6]);
          System.out.println("==================================================\n");
        }
      }
    }

  }

  public void start() throws IOException {
    char choice;
    do {
      System.out.println("=== SEARCH RECORDS ===");
      System.out.println("[a] Book");
      System.out.println("[b] Borrowed Book");
      System.out.println("[c] Reports");
      System.out.println("[d] Cancel");
      System.out.print("Enter choice: ");
      choice = scan.next().charAt(0);

      switch (choice) {
        case 'a':
          searchBook();
          return;
        case 'b':
          searchBorrowed();
          return;
        case 'c':
          searchReports();
          return;
        case 'd':
          System.out.println("Editing Canceled\n");
          break;
      }
    } while (choice != 'd');
  }
}
