package koossa.plaasbestuur.utils;

import java.time.LocalDate;

public class Filter {
	
	public static boolean onFilterByDate(LocalDate start, LocalDate end, LocalDate value) {
		if ((value.getYear() < start.getYear()) || (value.getYear() > end.getYear())) {
			return false;
		}
		if (value.getYear() == start.getYear()) {
			if (value.getDayOfYear() < start.getDayOfYear()) {
				return false;
			}
		} 
		if (value.getYear() == end.getYear()) {
			if (value.getDayOfYear() > end.getDayOfYear()) {
				return false;
			}
		}
		return true;
	}

}
