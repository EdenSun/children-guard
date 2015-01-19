package eden.sun.childrenguard.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
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
import eden.sun.childrenguard.R;
import eden.sun.childrenguard.adapter.AppSectionsPagerAdapter;
import eden.sun.childrenguard.fragment.ChildAppManageFragment;
import eden.sun.childrenguard.helper.MyViewPager;
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.util.Callback;

public class ChildrenManageActivity extends CommonFragmentActivity implements ActionBar.TabListener,View.OnClickListener{
	public static final String TAG = "ChildrenManageActivity";
	public static final int RESULT_CODE_DELETE_PERSON = 1;

    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    private MyViewPager mViewPager;
    
    private ChildBasicInfoViewDTO childBasicInfo;
    
    /* TABS */
    private View infoTab;
    private View controlTab ;
    private View scheduleTab;
    private View settingTab;
    private ImageView infoTabImageView ;
    private ImageView controlTabImageView;
    private ImageView scheduleTabImageView;
    private ImageView settingTabImageView ;
    private TextView infoTabText ;
    private TextView controlTabText;
    private TextView scheduleTabText;
    private TextView settingTabText ;
    
    private Integer childId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_manage);
        childId = getIntent().getIntExtra("childId", 0);
        
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        
        mViewPager = (MyViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
            	clearTabStatus();
            	
            	if( position == 0 ){
            		//Child Basic Info
            		Log.i(TAG, "basic info show");
            		infoTabImageView.setImageResource(R.drawable.tab_icon_child_basic_info_selected);
            		infoTabText.setTextColor(Color.WHITE);
            		
            	}else if( position == 1 ){
            		//App Manage
            		Log.i(TAG, "app manage show");
            		controlTabImageView.setImageResource(R.drawable.tab_icon_child_app_manage_selected);
            		controlTabText.setTextColor(Color.WHITE);
            		
            	}else if( position == 2 ){
            		//More
            		Log.i(TAG, "schedule show");
            		scheduleTabImageView.setImageResource(R.drawable.tab_icon_more_selected);
            		scheduleTabText.setTextColor(Color.WHITE);
            	}else if( position == 3 ){
            		//More
            		Log.i(TAG, "more setting show");
            		settingTabImageView.setImageResource(R.drawable.tab_icon_more_selected);
            		settingTabText.setTextColor(Color.WHITE);
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
    	infoTab = findViewById(R.id.infoTab);
    	infoTab.setOnClickListener(this);
    	controlTab = findViewById(R.id.controlTab);
    	controlTab.setOnClickListener(this);
    	scheduleTab = findViewById(R.id.scheduleTab);
    	scheduleTab.setOnClickListener(this);
    	settingTab = findViewById(R.id.settingTab);
    	settingTab.setOnClickListener(this);
    	
    	infoTabImageView = (ImageView)infoTab.findViewById(R.id.infoTabImageView);
    	controlTabImageView = (ImageView)controlTab.findViewById(R.id.controlTabImageView);
    	scheduleTabImageView = (ImageView)scheduleTab.findViewById(R.id.scheduleTabImageView);
        settingTabImageView = (ImageView)settingTab.findViewById(R.id.settingTabImageView);
        
        infoTabText = (TextView)findViewById(R.id.infoTabText);
        controlTabText = (TextView)findViewById(R.id.controlTabText);
        scheduleTabText = (TextView)findViewById(R.id.scheduleTabText);
        settingTabText = (TextView)findViewById(R.id.settingTabText);
        
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
		infoTabImageView.setImageResource(R.drawable.tab_icon_child_basic_info_normal);
		controlTabImageView.setImageResource(R.drawable.tab_icon_child_app_manage_normal);
		scheduleTabImageView.setImageResource(R.drawable.tab_icon_child_app_manage_normal);
		settingTabImageView.setImageResource(R.drawable.tab_icon_more_normal);
		
		infoTabText.setTextColor(getResources().getColor(R.color.tab_text));
		controlTabText.setTextColor(getResources().getColor(R.color.tab_text));
		scheduleTabText.setTextColor(getResources().getColor(R.color.tab_text));
		settingTabText.setTextColor(getResources().getColor(R.color.tab_text));
	}
    
    /**
     * tab点击事件
     */
	@Override
	public void onClick(View v) {
		if( v.getId() == R.id.infoTab){
			/*homeImageView.setImageResource(R.drawable.home_selected);
			homeTabText.setTextColor(getResources().getColor(R.color.tab_text_selected));*/
			mViewPager.setCurrentItem(0);
		}else if( v.getId() == R.id.controlTab ){
			/*userHomeImageView.setImageResource(R.drawable.user_home_selected);
			userHomeTabText.setTextColor(getResources().getColor(R.color.tab_text_selected));*/
			mViewPager.setCurrentItem(1);
		}else if( v.getId() == R.id.scheduleTab ){
			/*userHomeImageView.setImageResource(R.drawable.user_home_selected);
			userHomeTabText.setTextColor(getResources().getColor(R.color.tab_text_selected));*/
			mViewPager.setCurrentItem(2);
		}else if( v.getId() == R.id.settingTab ){
			//设置
			/*settingImageView.setImageResource(R.drawable.setting_selected);
			settingTabText.setTextColor(R.color.tab_text_selected);*/
			mViewPager.setCurrentItem(3);
		}
		
	}


	public void setChildBasicInfo(ChildBasicInfoViewDTO childBasicInfo) {
		this.childBasicInfo = childBasicInfo;
	}



	public ChildBasicInfoViewDTO getChildBasicInfo() {
		return childBasicInfo;
	}

	private MenuItem lockAllAppMenu;
	private MenuItem unlockAllAppMenu;
	private MenuItem addSchedule;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.children_manage, menu);
		lockAllAppMenu = menu.getItem(0);
		unlockAllAppMenu = menu.getItem(1);
		addSchedule = menu.getItem(2);
		
		initMenu();
		
		return true;
	}
	
	private void initMenu() {
		if(  mViewPager != null ){
			lockAllAppMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			unlockAllAppMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
			addSchedule.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			
			lockAllAppMenu.setVisible(false);
			unlockAllAppMenu.setVisible(false);
			addSchedule.setVisible(false);

			if( mViewPager.getCurrentItem() == 1 ){
				lockAllAppMenu.setVisible(true);
				unlockAllAppMenu.setVisible(true);
			}else if( mViewPager.getCurrentItem() == 2 ){
				addSchedule.setVisible(true);
			}
		}
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if( id == R.id.lockAllApp ){
			Log.d(TAG, "lock all app menu click.");
			
			ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_CONTROL);
			
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
			ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_CONTROL);
			
			
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
		}else if( id == R.id.addSchedule ){
			Log.d(TAG, "add schedule menu click.");
			Intent intent = new Intent(this,PresetLockActivity.class);
			
			startActivity(intent);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	/*private void applyMoreSettingChanges(Integer childId,
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
		
	}*/



/*	private Map<String, String> getApplySettingChangesParams(Integer childId2,
			List<MoreListItemView> settingList) {
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("childId", childId.toString());
		param.put("settingListJson", JSONUtil.transMoreListItemViewList2String(settingList));

		return param;
	}*/



	/*private void applyAppChanges(Integer childId,List<AppManageListItemView> itemList) {
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
	}*/



/*	private Map<String, String> getApplyAppChangesParams(Integer childId, List<AppManageListItemView> appList) {
		Map<String, String> param = new HashMap<String,String>();
		
		param.put("childId", childId.toString());
		param.put("appListJson", JSONUtil.transAppManageListItemViewList2String(appList));

		return param;
	}*/



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
	
	/*public void setConfigChanges(boolean isChanges){
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
*/
	/*private void clearSettingChangesData() {
		ChildManageMoreFragment moreFragment = (ChildManageMoreFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_SETTING);
		moreFragment.clearChangesSetting();
	}

	private void clearAppChangesData() {
		ChildAppManageFragment appManageFragment = (ChildAppManageFragment)mAppSectionsPagerAdapter.getItem(AppSectionsPagerAdapter.FRAGMENT_INDEX_CONTROL);
		appManageFragment.clearChangesApp();
	}*/

	/*private void saveChanges() {
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
	}*/


	/*private synchronized void changesFinish(){
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
	}*/
}
