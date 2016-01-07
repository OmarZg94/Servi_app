package net.servi.util;

import android.os.Environment;

public class Config {

	public final static String TAG = "Servi Log";

	public final static String NAME_DB = "Servi.db";
	public final static String PATH_DB = Environment
			.getExternalStorageDirectory().getPath() + "/Servi/" + NAME_DB;
	public final static Integer VERSION_DB = 1;

	public final static String IP = "sindral.net:8080";
	public final static String PATH_SERVICE = "Servi/rest/";

	public final static String NAME_SHARE_PREFERENCE = "Servi";
	public static final String SH_USER_NAME = "user_name";
	public static final String SH_PASSWORD = "password";
	public final static String SH_IP = "ip";
	public final static String SH_PATH_SERVICE = "path_service";
	public final static String SH_TIME_SYNC = "time_sync";

	/* Hour and a half to download new data */
	public final static int TIME_SYNCHRONIZE = 1000 * 60 * 90;
}
