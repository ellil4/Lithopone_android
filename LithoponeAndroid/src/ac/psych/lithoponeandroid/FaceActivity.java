package ac.psych.lithoponeandroid;

import ac.psych.lithoponeandroidYAAN.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FaceActivity extends Activity 
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_face);
		((Button)findViewById(R.id.amBeginBtn)).
			setOnClickListener(new OnClickListener());
	}
	
	private class OnClickListener implements Button.OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			startActivity(new Intent(FaceActivity.this, TitleActivity.class));
			FaceActivity.this.finish();
		}
		
	}
}
