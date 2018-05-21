package cinnabar.core.constant;

public enum UserStatus {
	
	ACTIVE("active"), DEACTIVE("deactive"), LOCKED("locked");
	
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
