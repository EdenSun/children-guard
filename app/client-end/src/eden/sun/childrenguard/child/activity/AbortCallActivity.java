package eden.sun.childrenguard.child.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import eden.sun.childrenguard.child.R;


public class AbortCallActivity extends CommonActivity {
	private String phoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abort_call);
		
		try{  
            Bundle bundle = this.getIntent().getExtras();  
            phoneNumber = bundle.getString("phoneNumber");  
            ShowDialog();  
        }catch(Exception ex){  
        }  
	}
	private void ShowDialog(){  
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  
        builder.setMessage("您确定要拨号给"+phoneNumber+"吗?");  
        builder.setTitle("提醒");  
        builder.setPositiveButton("确定", new OnClickListener(){  
            public void onClick(DialogInterface dialog,int which){  
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));   
                startActivity(intent);  
                Finish();  
            }  
        });  
        builder.setNegativeButton("取消", new OnClickListener(){  
            public void onClick(DialogInterface dialog,int which){  
                Finish();  
            }  
        });  
        builder.create().show();  
    }
	
    private void Finish(){  
        this.finish();  
    }  
}
