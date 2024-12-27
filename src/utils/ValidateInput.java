package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class ValidateInput {
	private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	private static FileSystem fs = new FileSystem();

	private static boolean isValidDate(String input) throws IOException {
		try {
			String[] parts = input.split("/");
			int month = Integer.parseInt(parts[0]);
			int day = Integer.parseInt(parts[1]);
			int year = Integer.parseInt(parts[2]);

			if (month == 2 && day == 29) {
				return LocalDate.of(year, 1, 1).isLeapYear();
			} else if (month == 2 && day > 28) {
				return false;
			}

			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkStudentExistance(String studentId) throws IOException {
		List<String> records = List.of(fs.readBorrow());
		String content = String.join("\n", records);
		String[] individualRecords = content.split("=====\\s*");

		for (String record : individualRecords) {
			record = record.trim();
			String[] lines = record.split("\n");

			if (lines.length > 0) {
				String studentID = lines[0].trim();
				if (studentID.equalsIgnoreCase(studentId))
					return true;
			}
		}

		return false;
	}

	public boolean checkExistance(String bookId) throws IOException {
		List<String> records = List.of(fs.read());
		String content = String.join("\n", records);
		String[] individualRecords = content.split("=====\\s*");

		for (String record : individualRecords) {
			record = record.trim();
			String[] lines = record.split("\n");

			if (lines.length > 0) {
				String bookID = lines[0].trim();
				if (bookID.equalsIgnoreCase(bookId))
					return true;
			}
		}

		return false;
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

	public void saveUpdate(List<String> details, String bookId) throws IOException {
		StringBuilder newDetails = new StringBuilder();
		for (String detail : details) {
			newDetails.append(detail).append("\n");
		}

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

		fs.writeEdit(updatedRecords);
	}

	public int checkQuantity(String prompt, String bookId) throws IOException {
		while (true) {
			System.out.print(prompt);
			try {
				String input = read.readLine().trim();
				if (input == null) {
					throw new IOException("Input stream closed.");
				}

				int quantity = Integer.parseInt(input);
				List<String> records = List.of(fs.read());
				List<String> updatedRecords = new ArrayList<>();

				List<String> books = getBooks(records);

				for (String book : books) {
					StringBuilder updatedRecord = new StringBuilder();
					String[] lines = book.split("\n");
					String bookID = lines[0].trim();
					int bookQuantity = Integer.parseInt(lines[5].trim());

					if (quantity > bookQuantity && bookID.equalsIgnoreCase(bookId)) {
						System.out.println("Borrow quantity too high");
					}

					if (bookID.equalsIgnoreCase(bookId)) {
						int newBookQuantity = bookQuantity - quantity;
						lines[5] = String.valueOf(newBookQuantity);

						for (String line : lines) {
							updatedRecord.append(line).append("\n");
						}

						updatedRecords.add(updatedRecord.toString().trim());
					}

				}

				saveUpdate(updatedRecords, bookId);
				return quantity;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a numeric value.");
			}
		}
	}

	public String prompt(String prompt) throws IOException {
		String input = null;

		while (input == null || input.isEmpty()) {
			System.out.print(prompt);
			input = read.readLine().trim();
		}

		return input;
	}

	public LocalDate promptDate(String user_prompt) throws IOException {
		LocalDate date = null;

		while (date == null) {
			try {
				String input = prompt(user_prompt);
				date = LocalDate.parse(input, dateFormat);

				if (!isValidDate(input)) {
					System.out.println("Invalid date. Please check your input.");
					date = null;
					continue;
				}

			} catch (DateTimeParseException e) {
				System.out.println("Invalid format. Please use (dd/mm/yyyy).");
			} catch (InputMismatchException e) {
				System.out.println("Invalid format. Please use (dd/mm/yyyy).");
			}
		}

		return date;
	}

	public int promptInt(String prompt) throws IOException {
		while (true) {
			System.out.print(prompt);
			try {
				String input = read.readLine().trim();
				if (input == null) {
					throw new IOException("Input stream closed.");
				}
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a numeric value.");
			}
		}
	}
}
