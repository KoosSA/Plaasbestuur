package koossa.plaasbestuur.data.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import koossa.plaasbestuur.PlaasBestuur;

public abstract class Savable<T> implements Serializable {

	private static final long serialVersionUID = 5956507102542611247L;

	public void save() {
		try {
			File folder = new File("Data/" + PlaasBestuur.getCurrentUser());
			if (!folder.exists()) {
				folder.mkdirs();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(folder, getClass().getSimpleName())));
			out.writeObject(this);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			File folder = new File("Data/" + PlaasBestuur.getCurrentUser());
			if (!folder.exists()) {
				folder.mkdirs();
			}
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(folder, getClass().getSimpleName())));
			Object obj = in.readObject();
			in.close();
			onLoad((T) obj);
		} catch (FileNotFoundException e) {
			save();
			load();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void onLoad(T loaded);
	
}
