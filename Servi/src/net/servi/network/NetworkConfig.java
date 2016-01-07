package net.servi.network;

import java.util.ArrayList;

import net.gshp.APINetwork.APINetwork;
import net.gshp.APINetwork.NetworkTask;
import net.gshp.APINetwork.NetworkTask.TaskMode;
import net.servi.util.Config;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

public class NetworkConfig {

	private Handler handler;
	private SharedPreferences mSharedPreferences;
	
	public NetworkConfig(Handler handler, Context context) {
		this.handler = handler;
		mSharedPreferences = context.getSharedPreferences(Config.NAME_SHARE_PREFERENCE, Context.MODE_PRIVATE);
		
		APINetwork.setUSERNAME(mSharedPreferences.getString(Config.SH_USER_NAME, null));
		APINetwork.setPASSWORD(mSharedPreferences.getString(Config.SH_PASSWORD, null));
		APINetwork.setSERVICE_IP(mSharedPreferences.getString(Config.SH_IP, Config.IP));
		APINetwork.setSERVICE_NAME(mSharedPreferences.getString(Config.SH_PATH_SERVICE, Config.PATH_SERVICE));
	}
	
	public void GET(String params, String tag) {
		NetworkTask Ntask = new NetworkTask(handler).setMode(TaskMode.GET)
				.setTag(tag).setParams(params).setBasicauth(false).setGzip(false);
		APINetwork.taskManager.addTask(Ntask);
	}
	
	public void POST(String params, ArrayList<NameValuePair> nameValuePairs,
			String tag) {
		NetworkTask Ntask = new NetworkTask(handler).setMode(TaskMode.POST)
				.setTag(tag).setPayload(nameValuePairs).setParams(params)
				.setBasicauth(false).setGzip(false);
		APINetwork.taskManager.addTask(Ntask);
	}

}
