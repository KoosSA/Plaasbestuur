package koossa.plaasbestuur.data.utils;

import java.util.HashMap;
import java.util.Map;

public class DataTable<T,K,V> {
	
	private Map<T, Map<K, V>> table = new HashMap<T, Map<K,V>>();
	
	public V getEntry(T column, K row) {
		return table.get(column).get(row);
	}
	
	public void setEntry(T column, K row, V value) {
		if (!table.containsKey(column)) {
			table.put(column, new HashMap<K, V>());
		}
		table.get(column).put(row, value);
	}
	
	public void put(T column, K row, V value) {
		setEntry(column, row, value);
	}

}
