package koossa.plaasbestuur.utils;

public enum Language {
	AFRIKAANS("afr"),
	ENGLISH_UK("eng_uk");
	
	
	private String data;
	
	private Language(String string) {
		data = string;
	}
	
	public String getData() {
		return data;
	}

}
