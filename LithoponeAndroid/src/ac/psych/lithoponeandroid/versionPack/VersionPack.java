package ac.psych.lithoponeandroid.versionPack;

import java.util.ArrayList;

import ac.psych.lithoponeADRXML.Test;
import ac.psych.lithoponeandroid.Names;
import ac.psych.lithoponeandroidYAAN.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.TextView;

public class VersionPack 
{
	public static String VERSION_CODE = "YAAN";
	
	public void DoReport(Test test, 
			ArrayList<Integer> userSelection, Activity context)
	{
		float shock = 0;
		float recover = 0;
		float ptgi = 0;
		float scoreBuf = 0;
		
		int count = test.Items.size();
		int userSelBuf = 0;
		for(int i = 0; i < count; i++)
		{
			userSelBuf = userSelection.get(i);
			//System.out.println(userSelBuf);
			//System.out.println(test.Items.get(i).Selections.size());
			if(userSelBuf == Integer.MIN_VALUE)
			{
				scoreBuf = 0;
			}
			else
			{
				scoreBuf = test.Items.get(i).Selections.get(userSelBuf).Value;
			}
			
			if(i >=1 && i <= 13)
			{
				shock += scoreBuf;
			}
			else if(i >= 15 && i <= 24)
			{
				recover += scoreBuf;
			}
			else if(i >= 26 && i <= 35)
			{
				ptgi += scoreBuf;
			}
		}
		
		String text = "您的冲击量表得分：" + shock + 
				"；您的复原力量表得分：" + recover + 
				"；您的创伤后成长量表分为：" + ptgi;
		
		((TextView)context.findViewById(R.id.amTvReportStr)).setText(text);

	}
}
