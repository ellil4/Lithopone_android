package ac.psych.lithoponeandroid;

import java.util.ArrayList;

import ac.psych.lithoponeADRXML.Test;
import ac.psych.lithoponeADRXML.XML;
import ac.psych.lithoponeandroid.versionPack.VersionPack;
import ac.psych.lithoponeandroidYAAN.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

public class ReportActivity extends Activity
{
	private boolean mOnceWork = false;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		
		((Button)findViewById(
				R.id.amBtnFinish)).setOnClickListener(
						new OnClickFinishBtn());
		if(!mOnceWork)
		{
			doReport();
			mOnceWork = true;
		}
	}
	
	protected ArrayList<Integer> collectUserSelection()
	{
		ArrayList<Integer> userSelection = new ArrayList<Integer>();
		SharedPreferences sp = getSharedPreferences(Names.TEST_INFO, 0);
		int count = sp.getInt(Names.ITEM_COUNT, 0);
		
		for(int i = 0; i < count; i++)
		{
			if(sp.contains(i + ""))
			{
				userSelection.add(sp.getInt(i + "", 0));
			}
			else
			{
				userSelection.add(Integer.MIN_VALUE);
			}
		}
		
		return userSelection;
		
	}
	
	protected void saveResult2File(Test test, ArrayList<Integer> userSelection)
	{
		FileSys fs = new FileSys(this, test, userSelection);
		fs.AppendRecord();
	}
	
	protected void doReport()
	{
		Test test = 
				(new XML()).GetTest(getResources().openRawResource(R.raw.src));

		
		ArrayList<Integer> userSelection = collectUserSelection();

		
		VersionPack vp = new VersionPack();

		vp.DoReport(test, userSelection, this);

		
		saveResult2File(test, userSelection);

	}
	
	private class OnClickFinishBtn implements Button.OnClickListener
	{
		@Override
		public void onClick(View arg0) 
		{
			ReportActivity.this.finish();
		}
	}
}
