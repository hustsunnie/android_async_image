package sunnie.app.asyncimage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Utils {
	public interface CopyProgressListener {
		void bytesCopied(int bytes);
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		CopyStream(is, os, null);
	}

	public static void CopyStream(InputStream is, OutputStream os,
			CopyProgressListener cpListener) {
		final int buffer_size = 1024;
		int total_size = 0;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);

				if (cpListener != null) {
					total_size += count;
					cpListener.bytesCopied(total_size);
				}
			}
		} catch (Exception ex) {
			Log.e("utils", "copy stream failed.", ex);
		}
	}

	public static boolean haveInternet(Context cxt) {
		ConnectivityManager connMgr = (ConnectivityManager) cxt
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr != null) {
			NetworkInfo info = connMgr.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				return true;
			}
		}
		return false;
	}

	public static boolean haveWiFi(Context cxt) {
		ConnectivityManager connMgr = (ConnectivityManager) cxt
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connMgr != null) {
			NetworkInfo info = connMgr.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				return info.getType() == ConnectivityManager.TYPE_WIFI;
			}
		}
		return false;
	}

	public static String genFileName(String url) {
		String suffix = url.substring(url.lastIndexOf('.'));
		String prefix = String.valueOf(url.hashCode());
		return prefix + suffix;
	}

	public static boolean httpGetImage(String url) {
		String filename = String.valueOf(url.hashCode());
		File f = new File(Const.SD_CACHE, filename);
		if(f.exists()){
			return true;
		}
		
		try {
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl
					.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
			Utils.CopyStream(is, os);
			os.close();
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}