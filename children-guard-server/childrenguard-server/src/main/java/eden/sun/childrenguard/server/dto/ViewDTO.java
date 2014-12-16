package eden.sun.childrenguard.server.dto;

public class ViewDTO<T>{
	public final static String MSG_SUCCESS = "success";
	public final static String MSG_ERROR = "error";
	/**
	 * "success" 表示成功 ，"error" 表示失败
	 */
	private String msg;
	/**
	 * 若失败，info表示失败的原因
	 */
	private String info;
	/**
	 * 返回的数据
	 */
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
