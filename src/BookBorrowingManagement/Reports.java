package BookBorrowingManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import utils.FileSystem;

public class Reports {
  private static FileSystem fs = new FileSystem();

  private static void displayInfo(String student) throws IOException {
    BufferedReader reader = new BufferedReader(new StringReader(student));
    List<String> details = new LinkedList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      details.add(line);
    }

    System.out.println("============== Borrower Information ==============");
    System.out.println("Student ID: " + details.get(0));
    System.out.println("Student Name: " + details.get(1));
    System.out.println("Borrowed Book/s: ");
    String[] borrowInfo = details.get(2).split("\\|");
    int totalBook = 0;
    for (int i = 0; i < borrowInfo.length; i++) {
      String info = borrowInfo[i];
      String[] individualInfo = info.split("=");
      System.out.println("  Book ID: " + individualInfo[0]);
      System.out.println("  Quantity: " + individualInfo[1] + "\n");
      totalBook += Integer.parseInt(individualInfo[1]);
    }
    System.out.println("Total Borrowed Book/s: " + totalBook);
    System.out.println("Date Borrowed: " + details.get(3));
    System.out.println("Over Due Payment Type: " + details.get(4));
    System.out.println("Overdue Amount: " + details.get(5));
    System.out.println("Date Returned: " + details.get(6));
    System.out.println("==================================================\n");
  }

  private static List<String> getReport(List<String> records) {
    List<String> students = new LinkedList<>();
    StringBuilder student = new StringBuilder();

    for (String record : records) {
      if (record.equals("=====")) {
        students.add(student.toString());
        student.setLength(0);
      } else {
        student.append(record).append("\n");
      }
    }

    if (student.length() > 0) {
      students.add(student.toString().trim());
    }

    return students;
  }

  public void start() throws IOException {
    List<String> records = List.of(fs.readReports());

    if (records.size() <= 0) {
      System.out.println("No records!\n");
      return;
    }

    List<String> students = getReport(records);

    for (String student : students) {
      displayInfo(student);
    }
  }
}
