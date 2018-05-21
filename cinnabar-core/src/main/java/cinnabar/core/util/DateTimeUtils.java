package cinnabar.core.util;

import java.text.SimpleDateFormat;

public class DateTimeUtils {
	
	private static final ThreadLocal<SimpleDateFormat> SDF_POOL = new ThreadLocal<>();
	
	public static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat sdf = SDF_POOL.get();
		if (sdf == null) {
			SDF_POOL.set(new SimpleDateFormat(format));
		}
		return SDF_POOL.get();
	}
	
}
