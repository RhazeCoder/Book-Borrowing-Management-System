package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileSystem {
    private static final String FILE_NAME = System.getProperty("user.dir") + "\\BookInventory.txt";
    private static final String BORROWED_FILE_NAME = System.getProperty("user.dir") + "\\BorrowedBook.txt";
    private static final String REPORT_FILE_NAME = System.getProperty("user.dir") + "\\Report.txt";
    private static final String TEMP_FILE_NAME = System.getProperty("user.dir") + "\\temp.txt";

    private static File file = new File(FILE_NAME);
    private static File bFile = new File(BORROWED_FILE_NAME);
    private static File rFile = new File(REPORT_FILE_NAME);

    private static void create() throws IOException {
        if (!file.exists())
            file.createNewFile();
    }

    private static void createBorrow() throws IOException {
        if (!bFile.exists())
            bFile.createNewFile();
    }

    public String[] read() throws IOException {
        create();
        List<String> lines = new ArrayList<>();

        try (FileReader fileReader = new FileReader(FILE_NAME);
                BufferedReader reader = new BufferedReader(fileReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines.toArray(new String[0]);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new String[] { e.getMessage() };
        }
    }

    public String[] readBorrow() throws IOException {
        createBorrow();
        List<String> lines = new ArrayList<>();

        try (FileReader fileReader = new FileReader(BORROWED_FILE_NAME);
                BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines.toArray(new String[0]);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return new String[] { e.getMessage() };
        }
    }

    public List<String> getBooks(List<String> records) throws IOException {
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

    public void removeBorrow(String bookId) throws IOException {
        List<String> records = List.of(readBorrow());
        List<String> books = getBooks(records);
        List<String> updatedBooks = new ArrayList<>();

        for (String book : books) {
            String[] lines = book.split("\n");
            String bookID = lines[0].trim();

            if (bookID.equalsIgnoreCase(bookId)) {
                continue;
            }

            updatedBooks.add(book.trim());
        }

        List<String> updatedRecords = new ArrayList<>();
        for (String updatedBook : updatedBooks) {
            updatedRecords.add(updatedBook);
            updatedRecords.add("=====");
        }

        writeBorrowEdit(updatedRecords);
    }

    public void write(String content) throws IOException {
        create();
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter writer = new PrintWriter(bufferedWriter)) {

            writer.println(content);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeBorrow(String content) throws IOException {
        createBorrow();
        try (FileWriter fileWriter = new FileWriter(BORROWED_FILE_NAME, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter writer = new PrintWriter(bufferedWriter)) {

            writer.println(content);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeBorrowEdit(List<String> updatedContent) throws IOException {
        createBorrow();

        File tempFile = new File(TEMP_FILE_NAME);

        try (FileWriter fileWriter = new FileWriter(tempFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter writer = new PrintWriter(bufferedWriter)) {

            for (String line : updatedContent) {
                writer.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (bFile.exists()) {
            bFile.delete();
        }

        tempFile.renameTo(bFile);
    }

    public void writeEdit(List<String> updatedContent) throws IOException {
        create();

        File tempFile = new File(TEMP_FILE_NAME);

        try (FileWriter fileWriter = new FileWriter(tempFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter writer = new PrintWriter(bufferedWriter)) {

            for (String line : updatedContent) {
                writer.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (file.exists()) {
            file.delete();
        }

        tempFile.renameTo(file);
    }

    public void writeReport(String content) throws IOException {
        try (FileWriter fileWriter = new FileWriter(REPORT_FILE_NAME, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter writer = new PrintWriter(bufferedWriter)) {
            writer.println(content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
