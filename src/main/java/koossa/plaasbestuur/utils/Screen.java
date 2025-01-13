package koossa.plaasbestuur.utils;

import com.gluonhq.attach.display.DisplayService;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.attach.util.Services;

import javafx.stage.Window;

public class Screen {
	
	private static double width, height, scale, resX, resY;
	
	public static void init() {
		Services.get(DisplayService.class).ifPresent(ds -> {
			width = ds.getDefaultDimensions().getWidth();
			height = ds.getDefaultDimensions().getHeight();
			scale = ds.getScreenScale();
			resX = ds.getScreenResolution().getWidth();
			resY = ds.getScreenResolution().getHeight();
		});
	}
	
	public static void fitToWidthIfMobile(Window window) {
		if (!Platform.isDesktop()) {
			window.setWidth(width);
		}
	}
	
	public static void fitToScreenIfMobile(Window window) {
		if (!Platform.isDesktop()) {
			window.setWidth(width);
			window.setHeight(height);
		}
	}

	public static double getWidth() {
		return width;
	}

	public static double getHeight() {
		return height;
	}

	public static double getScale() {
		return scale;
	}

	public static double getResolutionX() {
		return resX;
	}

	public static double getResolutionY() {
		return resY;
	}
	
	

}
