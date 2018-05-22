package cinnabar.core.constant;

public enum UserStatus {
	
	ACTIVE("active"), // available user
	DEACTIVE("deactive"),  // unavailable user
	LOCKED("locked"),  // user was locked
	UNKNOWN("unkown"); // unknown
	
	private String val;
	
	private UserStatus(String val) {
		this.val = val;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
}
