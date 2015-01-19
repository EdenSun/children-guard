package eden.sun.childrenguard.helper;

import java.lang.reflect.Field;

import android.content.DialogInterface;

public class DialogHelper {
	public static void preventDialogClose(DialogInterface dialog) {
		try {   
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");   
            field.setAccessible(true);   
            field.set(dialog, false);  


            } catch (Exception e) {   
            e.printStackTrace();   
        }  
	}

	public static void closeDialog(DialogInterface dialog) {
		try {  
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");  
            field.setAccessible(true);  
            field.set(dialog, true);  
            } catch (Exception e) {  
            e.printStackTrace();  
        }  							
	}
}
