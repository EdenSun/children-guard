package eden.sun.childrenguard.comet;

public class BaseMessageListener {
	private Callback callback;
	
	public void addCallback(Callback callback){
		if( callback != null ){
			this.callback = callback;
		}
	}
	
	protected void fireSuccess(){
		if( callback != null ){
			callback.onSuccess();
		}
	}
}
