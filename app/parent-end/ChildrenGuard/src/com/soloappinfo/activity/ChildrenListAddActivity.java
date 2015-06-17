package com.soloappinfo.activity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.soloappinfo.R;
import com.soloappinfo.errhandler.DefaultVolleyErrorHandler;
import com.soloappinfo.helper.SMSHelper;
import com.soloappinfo.util.BitmapUtil;
import com.soloappinfo.util.Config;
import com.soloappinfo.util.JSONUtil;
import com.soloappinfo.util.RealPathUtil;
import com.soloappinfo.util.RequestURLConstants;
import com.soloappinfo.util.StringUtil;
import com.soloappinfo.util.UIUtil;
import com.soloappinfo.util.UploadUtil;
import com.soundcloud.android.crop.Crop;

import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;

public class ChildrenListAddActivity extends CommonActivity {
	private static final String TAG = "ChildrenListAddActivity";
	protected static final int REQUEST_CODE_SELECT_PIC = 2;
	protected static final int REQUEST_CODE_SELECT_CONTACT = 3;
	private Button addFromContactsBtn;
	
	private Button addChildBtn;
	private Button cancelBtn;
	private EditText mobileEditText;
	private EditText nameEditText;
	private EditText passwordEditText;
	/*private EditText firstNameEditText;
	private EditText lastNameEditText;
	private EditText nicknameEditText;
	private Spinner relationshipSpinner;
	private RelationshipSpinnerAdapter relationshipSpinnerAdapter;*/
	private Intent resultIntent;
	
	private LinearLayout photoLine;
	private ImageView photoImageView;
	
	private String picPath = null;
	private String remotePhotoPath = null;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_children_list_add);

		setResult();
		
		initComponent();

		addFromContactsBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(
		                Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_SELECT_CONTACT);
			}
			
		});
		
		photoLine.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
                intent.setType("image/*");  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                startActivityForResult(intent, REQUEST_CODE_SELECT_PIC); 
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

	}

	
	private void setResult() {
		resultIntent = new Intent();
		setResult(0,resultIntent);		
	}

	/*
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
	}*/

	private void doAddChild() {
		boolean isPassed = doValidation();
		
		if( isPassed ){
			String url = Config.getInstance().BASE_URL_MVC
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
	
						/*new AlertDialog.Builder(ChildrenListAddActivity.this)
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
	
						}).show();*/
						onAddSuccess(view.getData());
					}else{
			    		AlertDialog.Builder dialog = UIUtil.getErrorDialog(ChildrenListAddActivity.this,view.getInfo());
			    		
						dialog.show();
					}
					
					
				}

			}, new DefaultVolleyErrorHandler(ChildrenListAddActivity.this));
		
		}
	}

	private void onAddSuccess(ChildViewDTO child) {
		if( child != null && child.getMobile() != null ){
			sendDownloadLinkToChild(child.getMobile());
		}
		
		resultIntent = new Intent();
		resultIntent.putExtra("result", "success");
		setResult(0,resultIntent);		
		
		ChildrenListAddActivity.this
				.finish();
	}
	
	private void sendDownloadLinkToChild(final String childMobile) {
		
		String url = String.format(Config.getInstance().BASE_URL_MVC
				+ RequestURLConstants.URL_GET_CHILD_APP_DOWNLOAD_LINK);

		
		getRequestHelper().doGet(url,this.getClass(), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				dismissProgressDialog();
				ViewDTO<String> view = JSONUtil
						.getGetChildAppDownloadLinkViewDTO(response);

				if (view.getMsg().equals(ViewDTO.MSG_SUCCESS)) {
					String content = "Click to download person end app:" + view.getData();
					SMSHelper.sendSms(childMobile,content);
				}

			}

		}, new DefaultVolleyErrorHandler(ChildrenListAddActivity.this));		
		
			
	}

	private boolean doValidation() {
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String name = UIUtil.getEditTextValue(nameEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
				

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
		
		if( StringUtil.isBlank(name) ){
			String title = "Add Person";
			String msg = "Name can not be blank.";
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
		
		if( StringUtil.isBlank(password) ){
			String title = "Add Person";
			String msg = "Password can not be blank.";
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
		String name = UIUtil.getEditTextValue(nameEditText);
		String mobile = UIUtil.getEditTextValue(mobileEditText);
		String password = UIUtil.getEditTextValue(passwordEditText);
		String parentAccessToken = getAccessToken();
		
		Map<String, String> param = new HashMap<String, String>();

		param.put("name", name);
		param.put("mobile", mobile);
		param.put("password", password);
		param.put("parentAccessToken", parentAccessToken);
		if( remotePhotoPath != null ){
			param.put("photoImage", remotePhotoPath);
		}
		
		return param;
	}

	private void initComponent() {
		addFromContactsBtn = (Button) findViewById(R.id.addFromContactsBtn);
		addChildBtn = (Button) findViewById(R.id.addBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		mobileEditText = (EditText) findViewById(R.id.mobileEditText);
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);

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
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SELECT_PIC) {
            Uri uri = data.getData();
            Log.i(TAG, "uri = " + uri);
            try {
            	if(Build.VERSION.SDK_INT < 11){
            		picPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
            	}
            	// SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19){
                	picPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
                }
                 // SDK > 19 (Android 4.4)
                else{
                	picPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                }
            	 
				if (picPath.endsWith("jpg") || picPath.endsWith("png")) {
					
					Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
			        new Crop(uri).output(outputUri).asSquare().start(this);
			        
					/*
					 * int wid = 60;
					int hei = 60;
					Bitmap bitmap = BitmapUtil.resizeBitmap(picPath, wid, hei);
					 * photoImageView.setImageBitmap(bitmap);

					doUploadImage();*/
				}else {
                	showNotValidImageDialog();
                }
 
            } catch (Exception e) {
            }
        }else if (resultCode == RESULT_OK && requestCode == Crop.REQUEST_CROP) {
        	Uri cripedPhotoUri = Crop.getOutput(data);
        	
        	/*if(Build.VERSION.SDK_INT < 11){
        		picPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, cripedPhotoUri);
        	}
        	// SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19){
            	picPath = RealPathUtil.getRealPathFromURI_API11to18(this, cripedPhotoUri);
            }
             // SDK > 19 (Android 4.4)
            else{
            	picPath = RealPathUtil.getRealPathFromURI_API19(this, cripedPhotoUri);
            }*/
        	
        	picPath = cripedPhotoUri.getPath();
        	int wid = 60;
			int hei = 60;
			Bitmap bitmap = BitmapUtil.resizeBitmap(picPath, wid, hei);
			photoImageView.setImageBitmap(bitmap);

			doUploadImage();
        }else if (resultCode != RESULT_OK && requestCode == Crop.REQUEST_CROP ){
        	Toast.makeText(this, "cancel crop", 1000).show();
        }else if( requestCode == REQUEST_CODE_SELECT_CONTACT && resultCode == RESULT_OK){
        	try {
				ContentResolver reContentResolverol = getContentResolver();
				Uri contactData = data.getData();
				@SuppressWarnings("deprecation")
				Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
				cursor.moveToFirst();
				
				String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				        null, 
				        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, 
				        null, 
				        null);
				
				while (phone.moveToNext()) {
				    String mobile = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				    
				    mobileEditText.setText(mobile);
				    nameEditText.setText(username);
				}
			} catch (Exception e) {
				Toast.makeText(ChildrenListAddActivity.this, "error", Toast.LENGTH_SHORT);
			}
        }
	        
		super.onActivityResult(requestCode, resultCode, data);
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
            	final String uploadUrl = Config.getInstance().BASE_URL_MVC + RequestURLConstants.URL_UPLOAD_PHOTO;
            	
            	String title = "Uploading photo";
        		String msg = "Please wait...";
        		showProgressDialog(title, msg);
        		
            	new AsyncTask<Void,Integer,String>(){

					@Override
					protected String doInBackground(Void... params) {
						ViewDTO<String> view = UploadUtil.uploadFile(file, uploadUrl);
						
						if( view != null && view.getMsg() != null && view.getMsg().equals(ViewDTO.MSG_SUCCESS)){
							remotePhotoPath = view.getData();
							
							Log.i(TAG, "Upload finish, photo url:" + remotePhotoPath);
							
							return "success";
						}else{
							Toast.makeText(ChildrenListAddActivity.this, "Upload photo fail,please try later.", 2000);
						}
						return null;
					}

					@Override
					protected void onPostExecute(String result) {
						dismissProgressDialog();
						super.onPostExecute(result);
					}
					
					
            	}.execute();
                
            }
        }
	}

}
