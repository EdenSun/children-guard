package eden.sun.childrenguard.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.RelationshipSpinnerAdapter;
import eden.sun.childrenguard.dto.RelationshipItemView;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestHelper;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;

public class ChildrenListAddActivity extends CommonActivity {
	private Button addChildBtn;
	private Button cancelBtn;
	private EditText mobileEditText;
	private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText nicknameEditText;
	private Spinner relationshipSpinner;
	private RelationshipSpinnerAdapter relationshipSpinnerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children_list_add);

		initComponent();

		addChildBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doAddChild();

			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ChildrenListAddActivity.this.finish();
			}
		});

	}

	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}


	private void initData() {
		loadRelationship();

	}

	private void loadRelationship() {
		String title = "Add Person";
		String msg = "Loading relationship,Please wait...";
		showProgressDialog(title, msg);

		RequestHelper helper = getRequestHelper();
		String url = String.format(Config.BASE_URL_MVC
				+ RequestURLConstants.URL_LIST_ALL_RELATIONSHIP);

		helper.doGet(url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				dismissProgressDialog();
				ViewDTO<List<RelationshipViewDTO>> view = JSONUtil
						.getRelationshipViewDTO(response);

				if (view.getMsg().equals(ViewDTO.MSG_SUCCESS)) {
					relationshipSpinnerAdapter.reloadData(view.getData());
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("TAG", error.getMessage(), error);
				AlertDialog.Builder dialog = UIUtil.getServerErrorDialog(ChildrenListAddActivity.this);
	    		
				dialog.show();
			}
		});
	}

	private void doAddChild() {
		boolean isPassed = doValidation();
		
		if( isPassed ){
			RequestHelper helper = getRequestHelper();
	
			String url = Config.BASE_URL_MVC
					+ RequestURLConstants.URL_LIST_ADD_CHILD;
	
			String title = "Add Person";
			String msg = "Please wait...";
			showProgressDialog(title, msg);
	
			Map<String, String> params = this.getAddChildParams();
			helper.doPost(url, params, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					dismissProgressDialog();
	
					final ViewDTO<ChildViewDTO> view = JSONUtil.getAddChildViewDTO(response);
	
					if (view.getMsg().equals(ViewDTO.MSG_SUCCESS)) {
	
						new AlertDialog.Builder(ChildrenListAddActivity.this)
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle("Add Child")
								.setMessage("Add Child Success!")
								.setPositiveButton("OK",
										new DialogInterface.OnClickListener() {
	
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
	
												ChildrenListAddActivity.this
														.finish();
											}
	
						}).show();
	
					}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildrenListAddActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
					
				}
				
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Log.e("TAG", error.getMessage(), error);
				}
			});
		
		}
	}

	private boolean doValidation() {
		String firstName = UIUtil.getEditTextValue(firstNameEditText);
		String lastName = UIUtil.getEditTextValue(lastNameEditText);
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String relationship = UIUtil.getSpinnerValue(relationshipSpinner);
		String nickname = UIUtil.getEditTextValue(nicknameEditText);
				

		if( StringUtil.isBlank(mobile) ){
			String title = "Add Person";
			String msg = "Mobile can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ChildrenListAddActivity.this,
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
			return false;
		}
		
		if( StringUtil.isBlank(firstName) ){
			String title = "Add Person";
			String msg = "First Name can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ChildrenListAddActivity.this,
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
			return false;
		}
		
		if( StringUtil.isBlank(lastName) ){
			String title = "Add Person";
			String msg = "Last Name can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ChildrenListAddActivity.this,
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
			return false;
		}
		
		if( StringUtil.isBlank(nickname) ){
			String title = "Add Person";
			String msg = "Nickname can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ChildrenListAddActivity.this,
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
			return false;
		}
		
		
		if( StringUtil.isBlank(relationship) ){
			String title = "Add Person";
			String msg = "Relationship can not be blank.";
			String btnText = "OK";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
				ChildrenListAddActivity.this,
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
			return false;
		}
		
		return true;
	}

	private Map<String, String> getAddChildParams() {
		String firstName = UIUtil.getEditTextValue(firstNameEditText);
		String lastName = UIUtil.getEditTextValue(lastNameEditText);
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String relationshipId = UIUtil.getSpinnerValue(relationshipSpinner);
		String nickname = UIUtil.getEditTextValue(nicknameEditText);
		String parentAccessToken = getAccessToken();
		
		Map<String, String> param = new HashMap<String, String>();

		param.put("firstName", firstName);
		param.put("lastName", lastName);
		param.put("mobile", mobile);
		param.put("relationshipId", relationshipId);
		param.put("nickname", nickname);
		param.put("parentAccessToken", parentAccessToken);
		
		return param;
	}

	private void initComponent() {
		addChildBtn = (Button) findViewById(R.id.addBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		mobileEditText = (EditText) findViewById(R.id.mobileEditText);
		firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
		lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
		nicknameEditText = (EditText) findViewById(R.id.nicknameEditText);
		relationshipSpinner = (Spinner) findViewById(R.id.relationshipSpinner);

		List<RelationshipItemView> data = new ArrayList<RelationshipItemView>();
		relationshipSpinnerAdapter = new RelationshipSpinnerAdapter(
				ChildrenListAddActivity.this, data);
		relationshipSpinner.setAdapter(relationshipSpinnerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.children_list_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
