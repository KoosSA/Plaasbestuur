package koossa.plaasbestuur.data.rain;

import java.time.LocalDate;

public class RainEntry {
	
	private double amount;
	private LocalDate date;
	private String location;
	
	public RainEntry(LocalDate date, String location, double amount) {
		this.date = date;
		this.location = location;
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public String getLocation() {
		return location;
	}
	

}
