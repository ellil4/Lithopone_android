package ac.psych.lithoponeandroid;

import ac.psych.lithoponeandroidYAAN.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

public class DemographyActivity extends Activity
{
	protected int mSelectedId = 0;
	private boolean mOnceWork = false;
	
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demography);
        
        Button btnConfirm = (Button)findViewById(R.id.amBtnDemoConfirm);
        btnConfirm.setOnClickListener(new OnConfirmBtnClickListener());
        RadioGroup rg =(RadioGroup)findViewById(R.id.amRadioGrpGender);
        rg.setOnCheckedChangeListener(new OnRadioCheckedChageListener());
        if(!mOnceWork)
        {
        	rg.check(-1);
        	mOnceWork = true;;
        }
    }
    
    private class OnRadioCheckedChageListener implements RadioGroup.OnCheckedChangeListener
    {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) 
		{
			mSelectedId = checkedId;
		}
    }
    
    protected int getRadioSelectedIndex(RadioGroup rg)
    {
    	int retval = -1;
    	int count = rg.getChildCount();
    	
    	for(int i = 0; i < count; i++)
    	{
    		if(((RadioButton)rg.getChildAt(i)).getId() == mSelectedId)
    		{
    			retval = i;
    			break;
    		}
    	}
    	
    	return retval;
    }
    
    private boolean allPass()
    {
    	boolean retval = true;
    	
    	String name = ((EditText)findViewById(R.id.amEditTextName)).getText().toString();
    	if(name == null || name.equals(""))
    		retval = false;
    	
    	int index = getRadioSelectedIndex((RadioGroup)findViewById(R.id.amRadioGrpGender));
    	if(index != 0 && index != 1)
    		retval = false;
    	
    	String age = ((EditText)findViewById(R.id.amEditTextAge)).getText().toString();
    	if(age == null || age.equals(""))
    		retval = false;
    	
    	String ethString = ((EditText)findViewById(R.id.amEditTextEthic))
				.getText().toString();
    	if(ethString == null || ethString.equals(""))
    		retval = false;
    	
    	String occuStr = ((EditText)findViewById(R.id.amEditTextOccup))
				.getText().toString();
    	if(occuStr == null || occuStr.equals(""))
    		retval = false;
    	
    	String addrStr = ((EditText)findViewById(R.id.amEditTextAddr))
				.getText().toString();
    	if(addrStr == null || addrStr.equals(""))
    		retval = false;
    	
    	return retval;
    }
    
    private class OnConfirmBtnClickListener implements Button.OnClickListener
    {

		@Override
		public void onClick(View v) 
		{
			if(allPass())
			{
				//start test activity
				startActivity(new Intent(DemographyActivity.this, TestActivity.class));
				saveUserInfo2SharedPreference();
				DemographyActivity.this.finish();
			}
			else
			{
				Toast.makeText(
						getApplicationContext(), 
						"请完整填写信息", Toast.LENGTH_LONG).show();
			}
		}
		
		public void saveUserInfo2SharedPreference()
		{
			SharedPreferences sp = getSharedPreferences(Names.USER_INFO, 0);
			sp.edit().clear().commit();
			
			sp.edit().putString(Names.NAME, 
					((EditText)findViewById(R.id.amEditTextName))
					.getText().toString()).commit();
			
			RadioGroup rg =(RadioGroup)findViewById(R.id.amRadioGrpGender);
			
			if(getRadioSelectedIndex(rg) == 0)
			{
				sp.edit().putString(Names.GENDER, "male").commit();
			}
			else if(getRadioSelectedIndex(rg) == 1)
			{
				sp.edit().putString(Names.GENDER," female").commit();
			}
			else
			{
				sp.edit().putString(Names.GENDER," null").commit();
			}
			
			String ageStr = ((EditText)findViewById(R.id.amEditTextAge)).getText().toString();
			int ageInt = -1;
			
			try
			{
				ageInt = Integer.parseInt(ageStr);
				
			}
			catch(Exception e)
			{}
			
			sp.edit().putInt(Names.AGE, ageInt).commit();
			
			sp.edit().putString(Names.ETHNIC,
					((EditText)findViewById(R.id.amEditTextEthic))
					.getText().toString()).commit();
			
			sp.edit().putString(Names.OCCUPATION, 
					((EditText)findViewById(R.id.amEditTextOccup))
					.getText().toString()).commit();
			
			sp.edit().putString(Names.ADDR, 
					((EditText)findViewById(R.id.amEditTextAddr))
					.getText().toString()).commit();
			
		}
    	
    }

}
