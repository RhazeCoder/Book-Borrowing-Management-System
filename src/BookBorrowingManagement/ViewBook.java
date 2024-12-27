package BookBorrowingManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import utils.FileSystem;

public class ViewBook {
  private static FileSystem fs = new FileSystem();

  private static void displayInfo(String book) throws IOException {
    BufferedReader reader = new BufferedReader(new StringReader(book));
    List<String> details = new LinkedList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      details.add(line);
    }

    System.out.println("================ Book Information ================");
    System.out.println("Book ID: " + details.get(0));
    System.out.println("Title: " + details.get(1));
    System.out.println("Author: " + details.get(2));
    System.out.println("Genre: " + details.get(3));
    System.out.println("Date Published: " + details.get(4));
    System.out.println("Book Quantity: " + details.get(5));
    System.out.println("Date Added: " + details.get(6));
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
    List<String> records = List.of(fs.read());

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
