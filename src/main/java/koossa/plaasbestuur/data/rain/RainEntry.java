package koossa.plaasbestuur.data.rain;

import java.io.Serializable;
import java.time.LocalDate;

public class RainEntry implements Serializable {
	
	private static final long serialVersionUID = -5108236343302630169L;
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

	public void setLoacation(String location) {
		this.location = location;
	}
	

}
