package koossa.plaasbestuur.utils;

import java.io.File;

import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.attach.util.Services;

public class Storage {
	
	private static File privateFolder, publicFolder;
	private static boolean publicAvailable = false;
	
	public static void init() {
		Services.get(StorageService.class).ifPresentOrElse(ss -> {
			ss.getPrivateStorage().ifPresent(priv -> {
				privateFolder = priv;
			});
			if (ss.isExternalStorageWritable()) {
				publicAvailable = true;
				ss.getPublicStorage("Plaasbestuur").ifPresent(store -> {
					publicFolder = store;
				});
			} else {
				System.err.println("External storage not writable");
				publicFolder = privateFolder;
			}
		}, () -> {
			System.err.println("Storage service not present");
		});
	}

	public static File getPrivateFolder() {
		return privateFolder;
	}

	public static File getPublicFolder() {
		return publicFolder;
	}
	
	public static boolean isPublicStorageAvailable() {
		return publicAvailable;
	}

}
