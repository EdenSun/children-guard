package eden.sun.childrenguard.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.AppSectionsPagerAdapter;
import eden.sun.childrenguard.dto.AppManageListItemView;
import eden.sun.childrenguard.dto.MoreListItemView;
import eden.sun.childrenguard.errhandler.DefaultVolleyErrorHandler;
import eden.sun.childrenguard.fragment.ChildAppManageFragment;
import eden.sun.childrenguard.fragment.ChildBasicInfoFragment;
import eden.sun.childrenguard.fragment.ChildManageMoreFragment;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.server.dto.ChildViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Config;
import eden.sun.childrenguard.util.JSONUtil;
import eden.sun.childrenguard.util.RequestURLConstants;
import eden.sun.childrenguard.util.UIUtil;

public class ChildrenManageActivity extends CommonFragmentActivity implements ActionBar.TabListener,View.OnClickListener{
	public static final String TAG = "ChildrenManageActivity";
	public static final int RESULT_CODE_DELETE_PERSON = 1;

    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    private ViewPager mViewPager;
    
    private ChildBasicInfoViewDTO childBasicInfo;
    
    /* TABS */
    View basicInfoTab;
    View appManageTab ;
    View moreTab;
    ImageView basicInfoTabImageView ;
    ImageView appManageTabImageView;
    ImageView moreTabImageView ;
    TextView basicInfoTabText ;
    TextView appManageTabText;
    TextView moreTabText ;
    
    private boolean isConfigChanges;
    private Integer childId;
    private int changesRequestCnt;
    private boolean saveAndExit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_manage);
        changesRequestCnt = 2;
        childId = getIntent().getIntExtra("childId", 0);
        
        isConfigChanges = false;
        
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	clearTabStatus();
            	
            	if( position == 0 ){
            		//Child Basic Info
            		Log.i(TAG, "basic info show");
            		basicInfoTabImageView.setImageResource(R.drawable.tab_icon_child_basic_info_selected);
            		basicInfoTabText.setTextColor(Color.WHITE);
            		
            	}else if( position == 1 ){
            		//App Manage
            		Log.i(TAG, "app manage show");
            		appManageTabImageView.setImageResource(R.drawable.tab_icon_child_app_manage_selected);
            		appManageTabText.setTextColor(Color.WHITE);
            		
            	}else if( position == 2 ){
            		//More
            		Log.i(TAG, "more setting show");
            		moreTabImageView.setImageResource(R.drawable.tab_icon_more_selected);
            		moreTabText.setTextColor(Color.WHITE);
            	}
            	
            	initMenu();
            }

        });
        
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        
        initTab();
	}

	
	
	private void initTab() {
    	basicInfoTab = findViewById(R.id.basicInfoTab);
    	basicInfoTab.setOnClickListener(this);
    	appManageTab = findViewById(R.id.appManageTab);
    	appManageTab.setOnClickListener(this);
    	moreTab = findViewById(R.id.moreTab);
    	moreTab.setOnClickListener(this);
    	
    	basicInfoTabImageView = (ImageView)basicInfoTab.findViewById(R.id.basicInfoTabImageView);
        appManageTabImageView = (ImageView)appManageTab.findViewById(R.id.appManageTabImageView);
        moreTabImageView = (ImageView)moreTab.findViewById(R.id.moreTabImageView);
        
        basicInfoTabText = (TextView)findViewById(R.id.basicInfoTabText);
        appManageTabText = (TextView)findViewById(R.id.appManageTabText);
        moreTabText = (TextView)findViewById(R.id.moreTabText);
        
	}
	
	
	@Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * 恢复tab正常状态
     */
    private void clearTabStatus() {
		basicInfoTabImageView.setImageResource(R.drawable.tab_icon_child_basic_info_normal);
		appManageTabImageView.setImageResource(R.drawable.tab_icon_child_app_manage_normal);
		moreTabImageView.setImageResource(R.drawable.tab_icon_more_normal);
		
		basicInfoTabText.setTextColor(getResources().getColor(R.color.tab_text));
		appManageTabText.setTextColor(getResources().getColor(R.color.tab_text));
		moreTabText.setTextColor(getResources().getColor(R.color.tab_text));
	}
    
    /**
     * tab点击事件
     */
	@Override
	public void onClick(View v) {
		if( v.getId() == R.id.basicInfoTab){
			/*homeImageView.setImageResource(R.drawable.home_selected);
			homeTabText.setTextColor(getResources().getColor(R.color.tab_text_selected));*/
			mViewPager.setCurrentItem(0);
		}else if( v.getId() == R.id.appManageTab ){
			/*userHomeImageView.setImageResource(R.drawable.user_home_selected);
			userHomeTabText.setTextColor(getResources().getColor(R.color.tab_text_selected));*/
			mViewPager.setCurrentItem(1);
		}else if( v.getId() == R.id.moreTab ){
			//设置
			/*settingImageView.setImageResource(R.drawable.setting_selected);
			settingTabText.setTextColor(R.color.tab_text_selected);*/
			mViewPager.setCurrentItem(2);
		}
		
	}


	public void setChildBasicInfo(ChildBasicInfoViewDTO childBasicInfo) {
		this.childBasicInfo = childBasicInfo;
	}



	public ChildBasicInfoViewDTO getChildBasicInfo() {
		return childBasicInfo;
	}

	private MenuItem applyChangeMenu;
	private MenuItem deletePersonMenu;
	private MenuItem lockAllAppMenu;
	private MenuItem unlockAllAppMenu;
	private MenuItem presetLockMenu;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.children_manage, menu);
		applyChangeMenu = menu.getItem(0);
		deletePersonMenu = menu.getItem(1);
		lockAllAppMenu = menu.getItem(2);
		unlockAllAppMenu = menu.getItem(3);
		presetLockMenu = menu.getItem(4);
		
		if( applyChangeMenu != null ){
			applyChangeMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			applyChangeMenu.setEnabled(false);
		}
		
		initMenu();
		
		return true;
	}
	
	private void initMenu() {
		if(  mViewPager != null ){
			if( mViewPager.getCurrentItem() == 0 ){
				deletePersonMenu.setVisible(true);
			}else{
				deletePersonMenu.setVisible(false);
			}
			
			if( mViewPager.getCurrentItem() == 1 ){
				lockAllAppMenu.setVisible(true);
				unlockAllAppMenu.setVisible(true);
			}else{
				lockAllAppMenu.setVisible(false);
				unlockAllAppMenu.setVisible(false);
			}
			
			if( mViewPager.getCurrentItem() == 2 ){
				presetLockMenu.setVisible(true);
			}else{
				presetLockMenu.setVisible(false);
			}
		}
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.apply) {
			Log.d(TAG, "apply person menu click.");
			
			saveAndExit = false;
			this.saveChanges();
			
			return true;
		}else if( id == R.id.deletePerson ){
			Log.d(TAG, "delete person menu click.");
			ChildBasicInfoFragment childBasicInfoFragment = (ChildBasicInfoFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_BASIC_INFO);
			childBasicInfoFragment.doDeletePerson(new Callback<ChildViewDTO>(){

				@Override
				public void execute(CallbackResult<ChildViewDTO> result) {
					if( result != null && result.isSuccess() ){
						//delete success
						ChildViewDTO deletedChild = result.getData();
						Toast.makeText(ChildrenManageActivity.this, "Person " + deletedChild.getNickname() + " have been deleted." , Toast.LENGTH_LONG).show();
						
						ChildrenManageActivity.this.setResult(RESULT_CODE_DELETE_PERSON);
						ChildrenManageActivity.this.finish();
					}
					
				}
				
			});
			return true;
		}else if( id == R.id.lockAllApp ){
			Log.d(TAG, "lock all app menu click.");
			
			ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_APP_MANAGE);
			
			appManageFragment.doLockAllApp(new Callback<Boolean>(){
				@Override
				public void execute(CallbackResult<Boolean> result) {
					if( result != null && result.isSuccess() ){
						// lock all app success, refresh app list
						Toast.makeText(ChildrenManageActivity.this, "All applications have been locked." , Toast.LENGTH_LONG).show();
					}
					
				}
			});
			
			return true;
		}else if( id == R.id.unlockAllApp ){
			Log.d(TAG, "unlock all app menu click.");
			ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_APP_MANAGE);
			
			
			appManageFragment.doUnlockAllApp(new Callback<Boolean>(){
				@Override
				public void execute(CallbackResult<Boolean> result) {
					if( result != null && result.isSuccess() ){
						// lock all app success, refresh app list
						Toast.makeText(ChildrenManageActivity.this, "All applications have been unlocked." , Toast.LENGTH_LONG).show();
					}
					
				}
			});			
			
			return true;
		}else if( id == R.id.presetLock ){
			Log.d(TAG, "preset lock menu click.");
			Intent intent = new Intent(this,PresetLockActivity.class);
			intent.putExtra("childId", childId);
			startActivity(intent);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void applyMoreSettingChanges(Integer childId,
			List<MoreListItemView> settingList) {
		if( childId != null && settingList != null && settingList.size() > 0 ){
			String url = Config.BASE_URL_MVC + RequestURLConstants.URL_APPLY_SETTING_CHANGES;  

			Map<String,String> params = this.getApplySettingChangesParams(childId,settingList);
			getRequestHelper().doPost(
				url,
				params,
				ChildrenManageActivity.this.getClass(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						final ViewDTO<Boolean> view = JSONUtil.getApplySettingChangesView(response);
								
						if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
							changesFinish();
						}else{
							String title = "Error";
							String msg = view.getInfo();
							String btnText = "OK";
							
							AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
								ChildrenManageActivity.this,
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
				new DefaultVolleyErrorHandler(ChildrenManageActivity.this));
		}else{
			changesFinish();
		}
		
	}



	private Map<String, String> getApplySettingChangesParams(Integer childId2,
			List<MoreListItemView> settingList) {
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("childId", childId.toString());
		param.put("settingListJson", JSONUtil.transMoreListItemViewList2String(settingList));

		return param;
	}



	private void applyAppChanges(Integer childId,List<AppManageListItemView> itemList) {
		if( childId != null && itemList != null && itemList.size() > 0 ){
			String url = Config.BASE_URL_MVC + RequestURLConstants.URL_APPLY_APP_CHANGES;  

			Map<String,String> params = this.getApplyAppChangesParams(childId,itemList);
			getRequestHelper().doPost(
				url,
				params,
				ChildrenManageActivity.this.getClass(),
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						final ViewDTO<Boolean> view = JSONUtil.getApplyAppChangesView(response);
								
						if( view.getMsg().equals(ViewDTO.MSG_SUCCESS) ){
							changesFinish();
						}else{
							String title = "Error";
							String msg = view.getInfo();
							String btnText = "OK";
							
							AlertDialog.Builder dialog = UIUtil.getAlertDialogWithOneBtn(
								ChildrenManageActivity.this,
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
				new DefaultVolleyErrorHandler(ChildrenManageActivity.this));
		}else{
			changesFinish();
		}
	}



	private Map<String, String> getApplyAppChangesParams(Integer childId, List<AppManageListItemView> appList) {
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("childId", childId.toString());
		param.put("appListJson", JSONUtil.transAppManageListItemViewList2String(appList));

		return param;
	}



	/**
	 * A placeholder fragment containing a simple view.
	 *//*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_children_manage,
					container, false);
			return rootView;
		}
	}*/
	
	public void setConfigChanges(boolean isChanges){
		this.isConfigChanges = isChanges;
		if( isChanges == true ){
			if( applyChangeMenu != null ){
				applyChangeMenu.setEnabled(true);
			}
		}else {
			if( applyChangeMenu != null ){
				applyChangeMenu.setEnabled(false);
			}
			
			clearAppChangesData();
			clearSettingChangesData();
		}
	}

	private void clearSettingChangesData() {
		ChildManageMoreFragment moreFragment = (ChildManageMoreFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_MORE_MANAGE);
		moreFragment.clearChangesSetting();
	}

	private void clearAppChangesData() {
		ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_APP_MANAGE);
		appManageFragment.clearChangesApp();
	}

	private void saveChanges() {
		String title = "Apply Changes";
		String msg = "Please wait...";
		showProgressDialog(title,msg);	
		
		// apply app changes
		ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_APP_MANAGE);
		List<AppManageListItemView> appList = appManageFragment.getChangesApp();

		applyAppChanges(childId,appList);
		
		// apply more settings
		ChildManageMoreFragment moreFragment = (ChildManageMoreFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_MORE_MANAGE);
		List<MoreListItemView> settingList = moreFragment.getChangesSetting();

		applyMoreSettingChanges(childId,settingList);		
	}


	@Override
	public void onBackPressed() {
		if( isConfigChanges == false ){
			// no changes	
			super.onBackPressed();
		}else{
			String title = "Setting Changes";
			String msg = "Setting changes, Press Save to save and return, Discard to discard and return.Press back button again to cancel.";
			String leftBtnText = "Save";
			String rightBtnText = "Discard";
			
			AlertDialog.Builder dialog = UIUtil.getAlertDialogWithTwoBtn(
				ChildrenManageActivity.this,
				title,
				msg,
				leftBtnText,
				rightBtnText,
				new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            	saveAndExit = true;
		            	saveChanges();
		            }

		        },
		        new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
		            	dialog.dismiss();
		            	ChildrenManageActivity.this.finish();
		            }
		        }
			);
			
			dialog.show();
		}
	}
	
	
	private synchronized void changesFinish(){
		changesRequestCnt --;
		if( changesRequestCnt <= 0 ){
			changesRequestCnt = 2;
			Toast.makeText(ChildrenManageActivity.this, "Apply changes success", 2000).show();
			this.setConfigChanges(false);
			dismissProgressDialog();
			
			if( saveAndExit == true ){
				ChildrenManageActivity.this.finish();
			}
		}
	}
}
