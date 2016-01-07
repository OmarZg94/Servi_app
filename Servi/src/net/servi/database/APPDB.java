package net.servi.database;

import net.servi.context.ContextApp;
import net.servi.util.Config;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class APPDB extends SQLiteOpenHelper {

	public APPDB() {
		super(ContextApp.context, Config.PATH_DB, null, Config.VERSION_DB);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Tables.Table_Horario);
		db.execSQL(Tables.Table_Reporte);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (oldVersion) {
		case 1:

		default:
			break;
		}

	}

}
