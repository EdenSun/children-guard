package eden.sun.childrenguard.util;

public class RequestURLConstants {

	public static final String URL_LOGIN = "parent/auth/login";
	public static final String URL_IS_FIRST_LOGIN = "parent/auth/isFirstLogin";
	public static final String URL_LIST_MY_CHILDREN = "parent/childrenManage/listMyChildren";
	public static final String URL_LIST_ADD_CHILD = "parent/childrenManage/addChild";
	public static final String URL_DELETE_CHILD = "parent/childrenManage/deleteChild";
	public static final String URL_REGISTER = "parent/reg/register";
	
	/********* Reset password ***************/
	public static final String URL_RESET_PASSWORD = "parent/reset/passwordReset";
	public static final String URL_RESET_SEND_PWD_TO_MOBILE = "parent/reset/sendPwdToMobile";
	/****************************************/
	
	public static final String URL_LIST_ALL_RELATIONSHIP = "parent/relationship/listAll";
	public static final String URL_GET_CHILD_BASIC_INFO = "parent/childrenManage/basicInfo";
	public static final String URL_LIST_CHILD_APP = "parent/childrenManage/listChildApp";
	public static final String URL_CHANGE_LOCK_PASSWORD = "parent/childrenManage/modifyLockPassword";
	
	public static final String URL_SAVE_REGISTION_ID = "parent/push/saveRegistionId";
	public static final String URL_LIST_EMERGENCY_CONTACT_BY_CHILD = "parent/emergencyContact/listByChild";
	public static final String URL_ADD_EMERGENCY_CONTACT = "parent/emergencyContact/add";
	public static final String URL_APPLY_APP_CHANGES = "parent/childrenManage/applyAppChanges";
	public static final String URL_APPLY_SETTING_CHANGES = "parent/childrenManage/applySettingChanges";
	public static final String URL_LOAD_CHILD_SETTING = "parent/childrenManage/loadChildSetting";
	public static final String URL_DO_CHANGE_PASSWORD = "parent/reset/doChangePassword";
	public static final String URL_UPLOAD_PHOTO = "parent/childrenManage/uploadPhoto";
	public static final String URL_LOCK_ALL_APP = "parent/childrenManage/lockAllAppByChild";
	public static final String URL_UNLOCK_ALL_APP = "parent/childrenManage/unlockAllAppByChild";
	
	/*****Preset Lock**********/
	// static final String URL_LOAD_PRESET_LOCK_DATA = "parent/childrenManage/loadPresetLockData";
	public static final String URL_NEW_PRESET_LOCK = "parent/presetlock/newPresetLock";
	public static final String URL_APPLY_PRESET_LOCK = "parent/presetlock/applyPresetLock";
	
	public static final String URL_LOAD_PRESET_LOCK_BY_ID = "parent/presetlock/loadPresetLockById";
	public static final String URL_LIST_CHILD_PRESET_LOCK_APP = "parent/presetlock/listChildPresetLockAppByPresetLockId";
	/**************************/
	
	/***** Push Message ***********/
	public static final String URL_LIST_PUSH_MESSAGE = "/parent/pushmsg/list";
	public static final String URL_DELETE_PUSH_MESSAGE = "/parent/pushmsg/delete";
	/******************************/
	/***** Schedule Lock ***********/
	public static final String URL_LIST_PRESET_LOCK = "/parent/presetlock/list";
	public static final String URL_BATCH_DELETE_SCHEDULE_LOCK = "/parent/presetlock/batchdelete";
	public static final String URL_DELETE_PRESET_LOCK = "/parent/presetlock/delete";
	public static final String URL_SWITCH_PRESET_LOCK = "/parent/presetlock/switchPresetLock";
	/******************************/
	
	/***** Setting ***********/
	public static final String URL_EMAIL_SETTING_SAVE = "/parent/setting/saveEmail";
	/******************************/
	
	public static final String URL_GET_CHILD_APP_DOWNLOAD_LINK = "/parent/childrenManage/getChildAppDownloadLink";
	
	/***** Person Setting ***********/
	public static final String URL_APPLY_SETTING = "/parent/personSetting/applySetting";
	/******************************/
	
}
