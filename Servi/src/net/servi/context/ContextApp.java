package net.servi.context;

import android.app.Application;
import android.content.Context;

import com.gosharp.apis.db.DBAPI;

public class ContextApp extends Application {

	public static Context context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		context=getApplicationContext();
		
		DBAPI dbApi=DBAPI.getInstance();
		dbApi.loadPropertiesFromFile(this.getApplicationContext().getResources());
		dbApi.loadDatabaseFromXML(this.getApplicationContext().getResources());
		dbApi.createDB(this.getApplicationContext());
	}
}
