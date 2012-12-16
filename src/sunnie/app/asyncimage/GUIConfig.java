package sunnie.app.asyncimage;

import android.os.Build;

public final class GUIConfig {
	//cause ICS uses GPU(OpenGL ES) accelerate on image views.
	public static final boolean SCALE_IMAGES = 
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
			
	public static final int SIZE_TO_SCALE = 4000;
}
