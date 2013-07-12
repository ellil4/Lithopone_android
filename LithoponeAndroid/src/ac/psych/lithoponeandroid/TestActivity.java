package ac.psych.lithoponeandroid;

import ac.psych.lithoponeADRXML.Test;
import ac.psych.lithoponeADRXML.XML;
import ac.psych.lithoponeandroidYAAN.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;

public class TestActivity extends Activity 
{
	private Test mTest = null;
	private int mCurPageIndex = 0;
	private RadioGroup mCurGroup = null;
	private LinearLayout mLayout = null;
	private SharedPreferences mTestInfoPref = null;
	private int mSelectedId = -1;
	
	private boolean mOnceWork = false;
	
	
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        
        if(!mOnceWork)
        {
        	//test
	        mTest = (new XML()).GetTest(getResources().openRawResource(R.raw.src));
	        //shared preference
	        mTestInfoPref = getSharedPreferences(Names.TEST_INFO, 0);
	        mTestInfoPref.edit().clear().commit();
	        mTestInfoPref.edit().putInt(Names.ITEM_COUNT,
	        		mTest.Items.size()).commit();
	        
	        //listener
	        ((Button)findViewById(R.id.amBtnNext)).
	        	setOnClickListener(new OnNextBtnListener());
	       
	        
	        refreshPanel();
	        
	        mOnceWork = true;
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
	
	protected void refreshPanel()
	{	
		EditText mainCasual = new EditText(this);
		mainCasual.setFocusable(false);
		mainCasual.setText("\r\n\r\n" + mTest.Items.get(mCurPageIndex).Casual);
		
		mLayout = (LinearLayout)findViewById(R.id.amSelectionLayout);
		
		mLayout.addView(mainCasual);
		
		mCurGroup = new RadioGroup(this);
		mCurGroup.setOnCheckedChangeListener(new OnRadioCheckedChageListener());
		for(int i = 0; i < mTest.Items.get(mCurPageIndex).Selections.size(); i++)
		{
			RadioButton rbtn = new RadioButton(this);
			rbtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, 60));
			
			rbtn.setText(mTest.Items.get(mCurPageIndex).Selections.get(i).Casual);
			mCurGroup.addView(rbtn);
		}
		
		mLayout.addView(mCurGroup);
		
		((TextView)findViewById(R.id.amTextViewPageNum)).setText(
				(mCurPageIndex + 1) + "/" + mTest.ItemCount);
	}
	
	private class OnNextBtnListener implements Button.OnClickListener
	{
		@Override
		public void onClick(View arg0) 
		{
			int selIndex = getRadioSelectedIndex(mCurGroup);
			if(selIndex != -1)
			{
				//if(mTestInfoPref == null)
								
				//save item selection value
				mTestInfoPref.edit().putInt(mCurPageIndex + "", 
						selIndex).commit();
				
				mSelectedId = -1;
				
				mCurPageIndex++;
				if(mCurPageIndex < mTest.ItemCount)
				{
					mLayout.removeAllViews();
					refreshPanel();
				}
				else
				{
					startActivity(new Intent(TestActivity.this, ReportActivity.class));
					TestActivity.this.finish();
				}

			}
			else
			{
				Toast.makeText(
						getApplicationContext(), 
						"Çë×ö³öÑ¡Ôñ", Toast.LENGTH_SHORT).show();
			}
			
		}
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jump, menu);
        return true;
         
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch(item.getItemId())
    	{
    	case R.id.item1:
    		mCurPageIndex = 0;
    		break;
    	case R.id.item2:
    		mCurPageIndex = 14;
    		break;
    	case R.id.item3:
    		mCurPageIndex = 25;
    		break;
    	case R.id.item4://finish
			startActivity(new Intent(TestActivity.this, ReportActivity.class));
			TestActivity.this.finish();
    		break;
    	}
    	
		mLayout.removeAllViews();
		refreshPanel();
    	
		return false;
    	
    }
}
