package ac.psych.lithoponeandroid;

import ac.psych.lithoponeADRXML.XML;
import ac.psych.lithoponeandroidYAAN.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class TitleActivity extends Activity 
{

	private boolean mOnceWork = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }
    
    protected void onStart()
    {
    	super.onStart();
    	
    	if(!mOnceWork)
    	{
    		ShowTitle();
    		mOnceWork = true;
    	}
    }
    
    private class OnStartBtnClickListener implements Button.OnClickListener
    {

		@Override
		public void onClick(View arg0) 
		{
			startActivity(new Intent(TitleActivity.this, DemographyActivity.class));
			TitleActivity.this.finish();
		}
    	
    }
    
    public void ShowTitle()
    {
    	//set button
    	Button btnStart = (Button)findViewById(R.id.amBtnStart);
    	btnStart.setOnClickListener(new OnStartBtnClickListener());
    	//XML work
        XML xml = new XML();
        TextView dscp = (TextView)findViewById(R.id.amDescriptionText);
        dscp.setText(xml.GetDescription(getResources().openRawResource(R.raw.src)));
    }
    
}
