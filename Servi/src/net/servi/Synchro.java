package net.servi;

import net.servi.context.ContextApp;
import net.servi.model.ModelSynchronize;
import net.servi.util.Config;
import net.servi.util.MySharedPreferences;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Synchro extends Activity implements OnClickListener {

	private Context context;
	private ProgressBar progressBar;
	private TextView txtPercent;
	private ImageButton btnContinue;
	private ModelSynchronize modelSynchronize;
	private MySharedPreferences mySharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_synchro);
		init();
	}

	private void init() {
		context = this;
		mySharedPreferences = new MySharedPreferences(ContextApp.context);
		/* Before starting the components is necessary to check the last time of synchronization; if
		 * the time is minor than 1:30 hrs then it will download new data, if not then start the Home
		 * activity */
		if ((System.currentTimeMillis()-mySharedPreferences.getLong(Config.SH_TIME_SYNC, 0L))>Config.TIME_SYNCHRONIZE) {
			modelSynchronize = new ModelSynchronize(UIHandler, context);
			modelSynchronize.startDownload();
			progressBar = (ProgressBar) findViewById(R.id.progressbar_syncro);
			txtPercent = (TextView) findViewById(R.id.txt_percent_syncro);
			btnContinue = (ImageButton)findViewById(R.id.btn_continue_synchro);
			btnContinue.setOnClickListener(this);
		} else {
			startActivity(new Intent(context, Home.class));
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.syncro, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private Handler UIHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HttpStatus.SC_OK:
//				Toast.makeText(context, getString(R.string.synchro_successfull),
//						Toast.LENGTH_SHORT).show();
				btnContinue.setVisibility(View.VISIBLE);
				break;
			case HttpStatus.SC_CONFLICT:
				Toast.makeText(context,
						getString(R.string.synchro_failed),
						Toast.LENGTH_SHORT).show();
				txtPercent.setText(R.string.menu_dialog_error_red);
				break;
			default:
				progressBar.setProgress(msg.what);
				txtPercent.setText("% " + msg.what);
				break;
			}
		};
	};

	@Override
	public void onClick(View arg0) {
		mySharedPreferences.putLong(Config.SH_TIME_SYNC, System.currentTimeMillis());
		startActivity(new Intent(context, Home.class));
		finish();
	}
}
