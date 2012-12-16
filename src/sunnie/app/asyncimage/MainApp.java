package sunnie.app.asyncimage;

import sunnie.app.asyncimage.widget.ImageLoader;
import android.app.Application;

public class MainApp extends Application {
	private static MainApp sInstance;
	private ImageLoader mImgLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		
		sInstance = this;
		mImgLoader = new ImageLoader(this);
	}
	
	public static MainApp instance(){
		return sInstance;
	}
	
	public ImageLoader getImageLoader(){
		return mImgLoader;
	}
}
