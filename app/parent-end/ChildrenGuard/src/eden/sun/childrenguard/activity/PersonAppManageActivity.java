package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.helper.IPersonAppManageFragmentInterface;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class PersonAppManageActivity extends CommonFragmentActivity{
	private static final String TAG = "PersonAppManageActivity";
	private IPersonAppManageFragmentInterface personAppManageFragmentInterface;
	private Integer childId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		childId = getIntent().getIntExtra("childId", 0);
        
		setContentView(R.layout.activity_person_app_lock_manage);
	}
	
	private MenuItem lockAllAppMenu;
	private MenuItem unlockAllAppMenu;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.person_app_manage, menu);
		//lockAllAppMenu = menu.getItem(0);
		//unlockAllAppMenu = menu.getItem(1);
//		
//		initMenu();
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if( id == R.id.lockAllApp ){
			Log.d(TAG, "lock all app menu click.");
			
			doLockAllApp(new Callback<Boolean>(){
				@Override
				public void execute(CallbackResult<Boolean> result) {
					if( result != null && result.isSuccess() ){
						// lock all app success, refresh app list
						Toast.makeText(PersonAppManageActivity.this, "All applications have been locked." , Toast.LENGTH_LONG).show();
					}
					
				}
			});
			
			return true;
		}else if( id == R.id.unlockAllApp ){
			Log.d(TAG, "unlock all app menu click.");
			
			doUnlockAllApp(new Callback<Boolean>(){
				@Override
				public void execute(CallbackResult<Boolean> result) {
					if( result != null && result.isSuccess() ){
						// lock all app success, refresh app list
						Toast.makeText(PersonAppManageActivity.this, "All applications have been unlocked." , Toast.LENGTH_LONG).show();
					}
					
				}
			});			
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	public void doLockAllApp(final Callback<Boolean> successCallback) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_LOCK_ALL_APP;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("childId", childId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			PersonAppManageActivity.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getLockAllAppView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Callback.CallbackResult<Boolean> result = new Callback.CallbackResult<Boolean>();
						result.setSuccess(true);
						result.setData(view.getData());
						successCallback.execute(result);
						
						personAppManageFragmentInterface.reloadApplications();
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							PersonAppManageActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            }
					        }
						);
						
						dialog.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(PersonAppManageActivity.this));		
	}


	public void doUnlockAllApp(final Callback<Boolean> successCallback) {
		String url = Config.BASE_URL_MVC + RequestURLConstants.URL_UNLOCK_ALL_APP;  

		Map<String,String> params = new HashMap<String,String>();
		params.put("childId", childId.toString());
		
		getRequestHelper().doPost(
			url,
			params,
			PersonAppManageActivity.this.getClass(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					final ViewDTO<Boolean> view = JSONUtil.getLockAllAppView(response);
							
					if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
						Callback.CallbackResult<Boolean> result = new Callback.CallbackResult<Boolean>();
						result.setSuccess(true);
						result.setData(view.getData());
						successCallback.execute(result);
						
						personAppManageFragmentInterface.reloadApplications();
					}else{
						String title = "Error";
						String msg = view.getInfo();
						String btnText = "OK";
						
						AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
							PersonAppManageActivity.this,
							title,
							msg,
							btnText,
							new DialogInterface.OnClickListener() {
					            @Override
					            public void onClick(DialogInterface dialog, int which) {
					            	dialog.dismiss();
					            }
					        }
						);
						
						dialog.show();
					}
				}
			}, 
			new DefaultVolleyErrorHandler(PersonAppManageActivity.this));		
	}

	public void setPersonAppManageFragmentInterface(
			IPersonAppManageFragmentInterface personAppManageFragmentInterface) {
		this.personAppManageFragmentInterface = personAppManageFragmentInterface;
	}
	
	
}
