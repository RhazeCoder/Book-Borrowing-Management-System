package BookBorrowingManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.Calculate;
import utils.FileSystem;
import utils.ValidateInput;

public class ReturnBook {
  private static Calculate calculate = new Calculate();
  private static FileSystem fs = new FileSystem();
  private static ValidateInput validate = new ValidateInput();

  private static Scanner scan = new Scanner(System.in);

  private static void returnInventory(String bookInfo) throws IOException {
    List<String> records = List.of(fs.read());
    List<String> updatedRecords = new ArrayList<>();
    List<String> books = validate.getBooks(records);

    String[] borrowInfo = bookInfo.split("\\|");

    for (String info : borrowInfo) {
      if (!info.isEmpty()) {
        String[] individualInfo = info.split("=");
        String bookId = individualInfo[0];
        int quantity = Integer.parseInt(individualInfo[1]);

        for (String book : books) {
          StringBuilder updatedRecord = new StringBuilder();
          String[] lines = book.split("\n");
          String bookID = lines[0].trim();
          int bookQuantity = Integer.parseInt(lines[5].trim());

          if (bookID.equalsIgnoreCase(bookId)) {
            int newBookQuantity = bookQuantity + quantity;
            lines[5] = String.valueOf(newBookQuantity);

            for (String line : lines) {
              updatedRecord.append(line).append("\n");
            }

            updatedRecords.clear();
            updatedRecords.add(updatedRecord.toString().trim());
            validate.saveUpdate(updatedRecords, bookId);
            break;
          }
        }
      }
    }
  }

  public void start() throws IOException {
    String studentId = validate.prompt("Enter student ID: ");
    boolean studentExists = validate.checkStudentExistance(studentId);

    if (!studentExists) {
      System.out.println("Student does not exist.");
      return;
    }

    List<String> records = List.of(fs.readBorrow());
    List<String> books = validate.getBooks(records);
    String bookInfo = null;

    LocalDate dateBorrowed = null;
    String reportData = "";
    for (String book : books) {
      String[] lines = book.split("\n");
      String studentID = lines[0].trim();

      if (studentID.equalsIgnoreCase(studentId)) {
        System.out.println("============== Borrower Information ==============");
        System.out.println("Student ID: " + lines[0]);
        System.out.println("Student Name: " + lines[1]);
        System.out.println("Borrowed Book/s: ");
        bookInfo = lines[2];
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
        dateBorrowed = LocalDate.parse(lines[3]);
        System.out.println("==================================================\n");
        reportData = lines[0] + "\n" + lines[1] + "\n" + lines[2] + "\n" + lines[3];
        break;
      }
    }

    LocalDate currentDate = LocalDate.now();
    int overDueDays = calculate.overDueDays(dateBorrowed, currentDate);
    String payment = "-";
    String overDuePay = "0";
    if (overDueDays <= 7) {
      System.out.println("No overdue payment.");
    } else {
      payment = validate.paymentType("Enter payment type (cash, card, gcash): ");
      overDuePay = "" + (overDueDays - 7) * 50;
      System.out.println("Overdue payment: " + (overDueDays - 7) * 50); // suktam ti rate mo per day nu overdue
    }

    System.out.print("\nConfirm book return (y/n): ");
    char choice = scan.next().charAt(0);

    if (choice == 'y') {
      reportData += "\n" + payment + "\n" + overDuePay + "\n" + currentDate + "\n=====";
      fs.writeReport(reportData);
      returnInventory(bookInfo);
      fs.removeBorrow(studentId);
      System.out.println("\nBook returned successfully.");
    } else {
      System.out.println("\nBook return cancelled.");
    }

  }

}
