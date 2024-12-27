package utils;

import java.time.LocalDate;
import java.time.Period;

public class Calculate {
	public int overDueDate(LocalDate dateBorrowed, LocalDate currentDate) {
		Period period = Period.between(dateBorrowed, currentDate);
		return period.getDays();
	}
}
