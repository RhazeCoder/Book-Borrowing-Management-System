package BookBorrowingManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.FileSystem;

public class BookDetails {
  private static FileSystem fs = new FileSystem();

  private static void displayInfo(String member) throws IOException {
    String[] details = member.split("\n");

    System.out.println("================ Book Information ================");
    System.out.println("Book ID: " + details[0]);
    System.out.println("Title: " + details[1]);
    System.out.println("Author: " + details[2]);
    System.out.println("Genre: " + details[3]);
    System.out.println("Date Published: " + details[4]);
    System.out.println("Book Quantity: " + details[5]);
    System.out.println("Date Added: " + details[6]);
    System.out.println("==================================================\n");

  }

  private static List<String> getMembers(List<String> records) {
    List<String> members = new ArrayList<>();
    StringBuilder member = new StringBuilder();

    for (String record : records) {
      if (record.equals("=====")) {
        members.add(member.toString());
        member.setLength(0);
      } else {
        member.append(record).append("\n");
      }
    }

    if (member.length() > 0) {
      members.add(member.toString().trim());
    }

    return members;
  }

  public void show(String bookId) throws IOException {
    List<String> records = List.of(fs.read());
    List<String> books = getMembers(records);

    for (String book : books) {
      String bookID = book.split("\n")[0];
      if (bookID.equalsIgnoreCase(bookId)) {
        displayInfo(book);
      }
    }
  }
}
