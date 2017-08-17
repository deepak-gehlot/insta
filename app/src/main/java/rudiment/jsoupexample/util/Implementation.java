package rudiment.jsoupexample.util;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Implementation extends ValidationTemplate {

	private static volatile Implementation instance = null;

	// private constructor
	private Implementation() {
	}

	public static Implementation getInstance() {
		if (instance == null) {
			synchronized (Implementation.class) {
				// Double check
				if (instance == null) {
					instance = new Implementation();
				}
			}
		}
		return instance;
	}

	@Override
	public boolean email_validation(String text) {

		Pattern emailPattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
				+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
		Matcher emailMatcher = emailPattern.matcher(text);
		return emailMatcher.matches();
	}

	@Override
	public boolean nullity(String text) {

		if (text.trim().equals("")) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean internet_wifi_validation(Context context) {

		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}

		return false;
	}

	@Override
	public boolean GPS_availability(Context context) {

		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public boolean file_validation(String file_name) {

		File file = new File(file_name);

		if (!file.exists()) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public boolean file_extension(String filetype) {

		if (filetype.endsWith(".jpg") || filetype.endsWith(".png") || filetype.endsWith(".mp3") || filetype.endsWith(".avi")) {

			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean isNumeric(String number) {

		final String REGEX = ("^(?:0091|\\+[0-9]|+[0-9][0-9]|0)[1-9][0-9]{9}$");

		final String REGEX1 = ("[1-9][0-9]{9}$");

		final String REGEX2 = ("^(?:0091|\\+[0-9]|+[0-9][0-9]|0)[1-9][0-9]{8}$");

		final String REGEX3 = ("[1-9][0-9]{8}$");
		final String REGEX4 = ("^(?:0091|\\-[0-9]|-[0-9][0-9]|0)[1-9][0-9]{8}$");
		boolean p = Pattern.matches(REGEX1, number);

		if (p == false) {
			try {
				p = Pattern.matches(REGEX, number);
			} catch (Exception e) {
				p = false;
			}
			if (p == false) {
				try {
					p = Pattern.matches(REGEX2, number);
				} catch (Exception e) {
					p = false;
				}
				if (p == false) {
					try {
						p = Pattern.matches(REGEX3, number);
					} catch (Exception e) {
						p = false;
					}
					if (p == false) {
						try {
							p = Pattern.matches(REGEX4, number);
						} catch (Exception e) {
							p = false;
						}
					}
				}

			}

		}

		return p;

	}

}
