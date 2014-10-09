package eden.sun.childrenguard.server.dto;

public class ViewDTO<T>{
	public final static String MSG_SUCCESS = "success";
	public final static String MSG_ERROR = "error";
	private String msg;
	private String info;
	private T data;
	
	public ViewDTO() {
		super();
		this.msg = MSG_SUCCESS;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
