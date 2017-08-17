package rudiment.jsoupexample.util;

import android.content.Context;

public class Extension {

	static ValidationTemplate vali;

	private static volatile Extension instance = null;

	// private constructor
	private Extension() {
	}

	public static Extension getInstance() {
		if (instance == null) {
			synchronized (Extension.class) {
				// Double check
				if (instance == null) {
					instance = new Extension();
				}
			}
		}
		vali = Implementation.getInstance();
		return instance;
	}

	public boolean executeStrategy(Context context, String text_if_needed, String check_tag) {

		return vali.template(context, text_if_needed, check_tag);

	}

}
