package eden.sun.childrenguard.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import eden.sun.childrenguard.server.dto.ChildBasicInfoViewDTO;
import eden.sun.childrenguard.util.Callback;
import eden.sun.childrenguard.util.Callback.CallbackResult;
import eden.sun.childrenguard.util.UIUtil;

public class ChildrenManageActivity extends FragmentActivity implements ActionBar.TabListener,View.OnClickListener{
	public static final String TAG = "ChildrenManageActivity";

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
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children_manage);
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.children_manage, menu);
		if( menu.getItem(0) != null ){
			applyChangeMenu = menu.getItem(0);
			applyChangeMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			applyChangeMenu.setEnabled(false);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.apply) {
			Toast.makeText(this, "Applying changes...", 2000).show();;
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		}
	}

	private void saveChanges(Callback callback) {
		// TODO Auto-generated method stub
		
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
		            	saveChanges(new Callback(){

							@Override
							public void execute(CallbackResult result) {
								if(result.isSuccess()){
									ChildrenManageActivity.this.finish();								
								}
							}
		            		
		            	});
		            	
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
	
	
	
}
