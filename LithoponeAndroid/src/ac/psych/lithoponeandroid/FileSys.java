package ac.psych.lithoponeandroid;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import ac.psych.lithoponeADRXML.Test;
import ac.psych.lithoponeandroid.versionPack.VersionPack;
import android.app.Activity;
import android.content.SharedPreferences;

public class FileSys 
{
	public static String mPath;
	private File mFile = null;
	private Activity mActivity;
	private Test mTest;
	private ArrayList<Integer> mUserSelection;
	
	public FileSys(Activity context, Test test, ArrayList<Integer> userSelection)
	{
		mActivity = context;
		initFileSystem();
		mUserSelection = userSelection;
		mTest = test;
	}
	
	protected void initFileSystem()
	{
		mPath = "mnt/sdcard/lithopone/";
		mFile = new File(mPath);
		if(!mFile.exists())
		{
			mFile.mkdirs();
		}
		
		mPath = mPath + VersionPack.VERSION_CODE;
		mFile = new File(mPath);
		if(!mFile.exists())
		{
			try
			{
				mFile.createNewFile();
				mFile = new File(mPath);
				initFile();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
	
	public void AppendRecord()
	{
		String target = "";
		
		SharedPreferences sp = mActivity.getSharedPreferences(Names.USER_INFO, 0);
		target += sp.getString(Names.NAME, "null") + "\t";
		target += sp.getInt(Names.AGE, -1) + "\t";
		target += sp.getString(Names.GENDER, "null") + "\t";
		target += sp.getString(Names.ETHNIC, "null") + "\t";
		target += sp.getString(Names.OCCUPATION, "null") + "\t";
		target += sp.getString(Names.ADDR, "null") + "\t";
		target += mTest.Items.size() + "\t";
		
		int itemCount = mUserSelection.size();
		int cursel = -1;
		float curval = 0;
		float total = 0;
		if(itemCount != -1)
		{
			for(int i = 0; i < itemCount; i++)
			{
				//selected idx
				cursel = mUserSelection.get(i);
				target += cursel + "\t";
				//value
				if(cursel == Integer.MIN_VALUE)
				{
					curval = 0;
				}
				else
				{
					curval = mTest.Items.get(i).Selections.get(cursel).Value;
				}
				target += curval + "\t";
				total += curval;
			}
			
			target += total + "\r\n";
		}
		
		writeStr2File(target, true);
	}
	
	private void writeStr2File(String content, boolean append)
	{
		try
		{
			FileWriter fw = new FileWriter(mFile, append);
			fw.write(content);
			fw.flush();
			fw.close();
		}
		catch(Exception e)
		{}
	}
	
	protected void initFile()
	{
		writeStr2File(makeHeader(), false);
	}
	
	protected String makeHeader()
	{
		String retval = "";
		retval += Names.NAME + "\t" + Names.AGE + "\t" + Names.GENDER + "\t" + 
				Names.ETHNIC + "\t" + Names.OCCUPATION + "\t" + Names.ADDR + "\t";
		
		SharedPreferences sp = mActivity.getSharedPreferences(Names.TEST_INFO, 0);
		int itemCount = sp.getInt(Names.ITEM_COUNT, -1);
		
		retval += Names.ITEM_COUNT + "\t";
		
		for(int i = 0; i < itemCount; i++)
		{
			retval += "item" + i + "\t" + "score" + i + "\t";
		}
		
		retval += "total\r\n";
		
		return retval;
	}
}
