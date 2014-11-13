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
        builder.setMessage("��ȷ��Ҫ���Ÿ�"+phoneNumber+"��?");  
        builder.setTitle("����");  
        builder.setPositiveButton("ȷ��", new OnClickListener(){  
            public void onClick(DialogInterface dialog,int which){  
                Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));   
                startActivity(intent);  
                Finish();  
            }  
        });  
        builder.setNegativeButton("ȡ��", new OnClickListener(){  
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
