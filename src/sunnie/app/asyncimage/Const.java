package sunnie.app.asyncimage;

import java.io.File;

import android.os.Environment;


public class Const {
	public static final File SD_CACHE = new File(Environment.getExternalStorageDirectory(),
			".image_cache");
}
