package com.soloappinfo.util;

public interface Callback<T> {

	void execute(CallbackResult<T> result);
	
	class CallbackResult<T>{
		private boolean isSuccess;
		private String info;
		private T data;
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
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
	}
}
