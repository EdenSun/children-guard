package eden.sun.childrenguard.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.RelationshipSpinnerAdapter;
import eden.sun.childrenguard.dto.RelationshipItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.StringUtil;
import eden.sun.childrenguard.util.UIUtil;
import eden.sun.childrenguard.util.UploadUtil;

public class ChildrenListAddActivity extends CommonActivity {
	private static final String TAG = "ChildrenListAddActivity";
	private Button addChildBtn;
	private Button cancelBtn;
	private EditText mobileEditText;
	private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText nicknameEditText;
	private Spinner relationshipSpinner;
	private RelationshipSpinnerAdapter relationshipSpinnerAdapter;
	private Intent resultIntent;
	
	private LinearLayout photoLine;
	private ImageView photoImageView;
	
	private String picPath = null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children_list_add);

		setResult();
		
		initComponent();

		photoLine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
                intent.setType("image/*");  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                startActivityForResult(intent, 1); 
			}
		});
		
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

		initData();
	}

	
	private void setResult() {
		resultIntent = new Intent();
		setResult(0,resultIntent);		
	}


	private void initData() {
		loadRelationship();

	}

	private void loadRelationship() {
		String title = "Add Person";
		String msg = "Loading relationship,Please wait...";
		showProgressDialog(title, msg);

		String url = String.format(Config.BASE_URL_MVC
				+ RequestURLConstants.URL_LIST_ALL_RELATIONSHIP);

		getRequestHelper().doGet(url,this.getClass(), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				dismissProgressDialog();
				ViewDTO<List<RelationshipViewDTO>> view = JSONUtil
						.getRelationshipViewDTO(response);

				if (view.getMsg().equals(ViewDTO.MSG_SUCCESS)) {
					relationshipSpinnerAdapter.reloadData(view.getData());
				}

			}
		}, new DefaultVolleyErrorHandler(ChildrenListAddActivity.this));
	}

	private void doAddChild() {
		boolean isPassed = doValidation();
		
		if( isPassed ){
			String url = Config.BASE_URL_MVC
					+ RequestURLConstants.URL_LIST_ADD_CHILD;
	
			String title = "Add Person";
			String msg = "Please wait...";
			showProgressDialog(title, msg);
	
			Map<String, String> params = this.getAddChildParams();
			getRequestHelper().doPost(url, params, this.getClass(),new Response.Listener<String>() {
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
	
												resultIntent = new Intent();
												resultIntent.putExtra("result", "success");
												setResult(0,resultIntent);		
												
												ChildrenListAddActivity.this
														.finish();
											}
	
						}).show();
	
					}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildrenListAddActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
					
				}
				
			}, new DefaultVolleyErrorHandler(ChildrenListAddActivity.this));
		
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
		
		photoLine = (LinearLayout)findViewById(R.id.photoLine);
		photoImageView = (ImageView)findViewById(R.id.photoImageView);
	}


	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.i(TAG, "uri = " + uri);
            try {
                String[] pojo = { MediaStore.Images.Media.DATA };
 
                Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);
                if (cursor != null) {
                    ContentResolver cr = this.getContentResolver();
                    int colunm_index = cursor
                            .getColumnIndexOrThrow(pojo[0]);
                    cursor.moveToFirst();
                    String path = cursor.getString(colunm_index);
                    cursor.close();
                    *//***
                     * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                     * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                     *//*
                    if (path.endsWith("jpg") || path.endsWith("png")) {
                        picPath = path;
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(uri));
                        photoImageView.setImageBitmap(bitmap);
                        
                        doUploadImage();
                    } else {
                    	showNotValidImageDialog();
                    }
                } else {
                	showNotValidImageDialog();
                }
 
            } catch (Exception e) {
            }
        }
	        
		super.onActivityResult(requestCode, resultCode, data);
	}*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.i(TAG, "uri = " + uri);
            try {
            	ContentResolver cr = this.getContentResolver();
                
                 Bitmap bitmap = BitmapFactory.decodeStream(cr
                         .openInputStream(uri));
                 photoImageView.setImageBitmap(bitmap);
                 
                 doUploadImage();
 
            } catch (Exception e) {
            }
        }
	        
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public String getImagePath(Uri uri) {
	    String selectedImagePath;
	    // 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    if (cursor != null) {
	        int column_index = cursor
	                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();
	        selectedImagePath = cursor.getString(column_index);
	    } else {
	        selectedImagePath = null;
	    }

	    if (selectedImagePath == null) {
	        // 2:OI FILE Manager --- call method: uri.getPath()
	        selectedImagePath = uri.getPath();
	    }
	    return selectedImagePath;
	}

	private void showNotValidImageDialog() {
		String title = "Add Person";
		String msg = "Selected file is not valid image.";
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
	}


	private void doUploadImage() {
		if (picPath == null) {
			 
            Toast.makeText(ChildrenListAddActivity.this, "Please select photo!", 1000).show();
        } else {
            final File file = new File(picPath);

            if (file != null) {
            	String uploadUrl = Config.BASE_URL_MVC + RequestURLConstants.URL_UPLOAD_PHOTO;
                int responseCode = UploadUtil.uploadFile(file, uploadUrl);
            }
        }
	}

}
