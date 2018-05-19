package cinnabar.core.configuration;

public class InformationProvider {
	
	private static final String BASE_PACKAGE = "cinnabar.core.";
	
	public static String getRedisConfigPackage() {
		return BASE_PACKAGE + "redis";
	}
	
	public static String getRPCConfigPackage() {
		return BASE_PACKAGE + "rpc";
	}
	
}
