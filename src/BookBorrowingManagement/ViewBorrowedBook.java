package BookBorrowingManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import utils.FileSystem;

public class ViewBorrowedBook {
  private static FileSystem fs = new FileSystem();

  private static void displayInfo(String book) throws IOException {
    BufferedReader reader = new BufferedReader(new StringReader(book));
    List<String> details = new LinkedList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      details.add(line);
    }

    System.out.println("============== Borrower Information ==============");
    System.out.println("Student ID: " + details.get(0));
    System.out.println("Student Name: " + details.get(1));
    System.out.println("Over Due Payment Type: " + details.get(2));
    System.out.println("Borrowed Book/s: ");
    String[] borrowInfo = details.get(3).split("\\|");
    int totalBook = 0;
    for (int i = 0; i < borrowInfo.length; i++) {
      String info = borrowInfo[i];
      String[] individualInfo = info.split("=");
      System.out.println("  Book ID: " + individualInfo[0]);
      System.out.println("  Quantity: " + individualInfo[1] + "\n");
      totalBook += Integer.parseInt(individualInfo[1]);
    }
    System.out.println("Total Borrowed Book/s: " + totalBook);
    System.out.println("Date Borrowed: " + details.get(4));
    System.out.println("==================================================\n");
  }

  private static List<String> getBooks(List<String> records) {
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

  public void start() throws IOException {
    List<String> records = List.of(fs.readBorrow());

    if (records.size() <= 0) {
      System.out.println("No records!\n");
      return;
    }

    List<String> books = getBooks(records);

    for (String book : books) {
      displayInfo(book);
    }
  }
}
