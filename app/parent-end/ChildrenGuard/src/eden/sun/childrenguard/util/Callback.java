package eden.sun.childrenguard.util;

public interface Callback {

	void execute(CallbackResult result);
	
	class CallbackResult{
		private boolean isSuccess;
		private String info;
		public boolean isSuccess() {
			return isSuccess;
		}
		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
	}
}
