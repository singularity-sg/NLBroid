package org.singularity.util;

public class NLBUtil {

	public static boolean isSDCardPresent() {
		return android.os.Environment.getExternalStorageState()
		.equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
}
