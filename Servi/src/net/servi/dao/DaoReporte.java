package net.servi.dao;

import java.util.ArrayList;
import java.util.List;

import net.servi.dto.DtoReporte;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DaoReporte extends DAO {

	private SQLiteDatabase db;
	private Cursor cursor;

	public static String TABLE_NAME = "reporte";
	public static String PK_FIELD = "id";

	private final String ID = "id";
	private final String ID_HORARIO = "id_horario";
	private final String ESTADO = "estado";
	private final String IP_ALUMNO = "ip_alumno";
	private final String REPORTED_ON = "reported_on";

	public DaoReporte() {
		super(TABLE_NAME, PK_FIELD);
	}

	/**
	 * Insert
	 */
	public int Insert(List<DtoReporte> obj) {
		db = helper.getWritableDatabase();
		int resp = 0;
		try {
			SQLiteStatement insStmnt = db.compileStatement("" + "INSERT INTO "
					+ TABLE_NAME + " (" + ID + "," + ID_HORARIO + "," + ESTADO + ","
					+ IP_ALUMNO + "," + REPORTED_ON + ") VALUES(?,?,?,?,?);");
			db.beginTransaction();
			for (DtoReporte DtoCatalog : obj) {
				try {
					insStmnt.bindLong(1, DtoCatalog.getId());
				} catch (Exception e) {
					insStmnt.bindNull(1);
				}
				try {
					insStmnt.bindLong(2, DtoCatalog.getIdHorario());
				} catch (Exception e) {
					insStmnt.bindNull(2);
				}
				try {
					insStmnt.bindLong(3, DtoCatalog.getEstado());
				} catch (Exception e) {
					insStmnt.bindNull(3);
				}
				try {
					insStmnt.bindString(4, DtoCatalog.getIpAlumno());
				} catch (Exception e) {
					insStmnt.bindNull(4);
				}
				try {
					insStmnt.bindString(5, DtoCatalog.getReportedOn());
				} catch (Exception e) {
					insStmnt.bindNull(5);
				}
				insStmnt.executeInsert();
			}

			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		db.close();
		return resp;
	}

	/**
	 * Insert
	 */
	public int Insert(DtoReporte obj) {
		db = helper.getWritableDatabase();
		ContentValues cv;
		int resp = 0;
		try {
			db.beginTransaction();
			cv = new ContentValues();
			cv.put(ID, obj.getId());
			cv.put(IP_ALUMNO, obj.getIpAlumno());
			cv.put(ESTADO, obj.getEstado());
			cv.put(ID_HORARIO, obj.getIdHorario());
			cv.put(REPORTED_ON, obj.getReportedOn());
			resp = (int) db.insert(TABLE_NAME, null, cv);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		db.close();
		return resp;
	}

	/**
	 * Select
	 */
	public List<DtoReporte> Select() {
		db = helper.getReadableDatabase();
		cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " ORDER BY reporte.id ASC", null);
		List<DtoReporte> obj = new ArrayList<DtoReporte>();
		DtoReporte catalogo;
		if (cursor.moveToFirst()) {
			int id = cursor.getColumnIndexOrThrow(ID);
			int ip_alumno = cursor.getColumnIndexOrThrow(IP_ALUMNO);
			int id_horario = cursor.getColumnIndexOrThrow(ID_HORARIO);
			int estado = cursor.getColumnIndexOrThrow(ESTADO);
			int reported_on = cursor.getColumnIndexOrThrow(REPORTED_ON);
			do {
				catalogo = new DtoReporte();
				catalogo.setId(cursor.getInt(id));
				catalogo.setIpAlumno(cursor.getString(ip_alumno));
				catalogo.setIdHorario(cursor.getInt(id_horario));
				catalogo.setEstado(cursor.getInt(estado));
				catalogo.setReportedOn(cursor.getString(reported_on));
				obj.add(catalogo);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return obj;
	}
}
