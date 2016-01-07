package net.servi.model;

import net.gshp.APINetwork.NetworkTask;
import net.servi.network.NetworkConfig;
import net.servi.util.Config;

import org.apache.http.HttpStatus;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ModelSynchronize {

	private final int STORAGE_OK = 1;
	private NetworkConfig networkConfig;
	private HandlerTask handlerTask;
	private Handler handlerGUI;
	private boolean SC_CONFLICT = false;
	private boolean SC_NOT_FOUND = false;
	private ModelDatabaseSync modelDataBaseSync;
	private HandlerStorage handlerStorage;

	private int noSavedReports = 0;
	private Message msgUI;
	// private MySharedPreferences mySharedPreferences;

	/*
	 * The total services to be consulted, it helps to know when all the info
	 * were download then send a finish message
	 */
	private final int CATALOG_NUMBER = 2;

	private int numReportDownload = 0;

	public ModelSynchronize(Handler handlerGUI, Context context) {
		handlerTask = new HandlerTask();
		this.handlerGUI = handlerGUI;
		handlerStorage = new HandlerStorage();
		modelDataBaseSync = new ModelDatabaseSync(handlerStorage);
		networkConfig = new NetworkConfig(handlerTask, context);
		// mySharedPreferences=new MySharedPreferences(context);
	}

	public void startDownload() {
		new Thread() {
			public void run() {
				networkConfig.GET("horario/all", "horario");
				networkConfig.GET("reporte/all", "reporte");
//				networkConfig.GET("hueco/by-day", "hueco");
			};
		}.start();
	}

	class HandlerTask extends Handler {
		@Override
		public void handleMessage(Message msg) {
			NetworkTask nt = (NetworkTask) msg.obj;
			numReportDownload++;
			Log.i("Sync", nt.getTag() + " ----- " + nt.getResponseStatus());
			if (nt.getResponseStatus() == HttpStatus.SC_OK) {
				/* Send the info to the database */
				modelDataBaseSync.syncInsertion(nt);
				Log.e(Config.TAG, "Json received");
			} else if (nt.getResponseStatus() == HttpStatus.SC_NOT_FOUND) {
				SC_NOT_FOUND = true;
				numReportDownload = CATALOG_NUMBER;
				noSavedReports = CATALOG_NUMBER;
			} else if (nt.getResponseStatus() == HttpStatus.SC_CONFLICT) {
				SC_CONFLICT = true;
				numReportDownload = CATALOG_NUMBER;
				noSavedReports = CATALOG_NUMBER;
			} else {
				SC_CONFLICT = true;
				noSavedReports++;
			}
			infoUI();

		}
	}

	class HandlerStorage extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == STORAGE_OK)
				noSavedReports++;
			infoUI();
		}
	}

	/**
	 * Check the results of the synchronization and send them to the UI
	 */
	private void infoUI() {
		int porcent = (noSavedReports * 100) / CATALOG_NUMBER;
		if (!SC_NOT_FOUND && !SC_CONFLICT) {
			handlerGUI.sendEmptyMessage(porcent);
		}
		if (numReportDownload == CATALOG_NUMBER
				&& noSavedReports == CATALOG_NUMBER) {
			numReportDownload = 0; // Reset the count
			noSavedReports = 0;
			msgUI = handlerGUI.obtainMessage();
			if (!SC_NOT_FOUND && !SC_CONFLICT) {
				msgUI.what = HttpStatus.SC_OK;
			} else if (SC_NOT_FOUND) {
				msgUI.what = HttpStatus.SC_NOT_FOUND;
				SC_NOT_FOUND = false;
			} else if (SC_CONFLICT) {
				msgUI.what = HttpStatus.SC_CONFLICT;
				SC_CONFLICT = false;
			}
			handlerGUI.sendMessage(msgUI);
		}
	}
}
