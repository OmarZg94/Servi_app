package net.servi.dao;

import java.util.ArrayList;
import java.util.List;

import net.servi.dto.DtoHorario;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DaoHorario extends DAO {

	private SQLiteDatabase db;
	private Cursor cursor;
	private String query;

	public static String TABLE_NAME = "horario";
	public static String PK_FIELD = "id";

	private final String ID = "id";
	private final String SALON = "salon";
	private final String GRUPO = "grupo";
	private final String MATERIA = "materia";
	private final String PROFESOR = "profesor";
	private final String LUNES = "lunes";
	private final String MARTES = "martes";
	private final String MIERCOLES = "miercoles";
	private final String JUEVES = "jueves";
	private final String VIERNES = "viernes";

	public DaoHorario() {
		super(TABLE_NAME, PK_FIELD);
	}

	/**
	 * Insert
	 */
	public int Insert(List<DtoHorario> obj) {
		db = helper.getWritableDatabase();
		int resp = 0;
		try {
			SQLiteStatement insStmnt = db.compileStatement("" + "INSERT INTO "
					+ TABLE_NAME + " (" + ID + "," + SALON + "," + GRUPO + ","
					+ MATERIA + "," + PROFESOR + "," + LUNES + "," + MARTES
					+ "," + MIERCOLES + "," + JUEVES + "," + VIERNES
					+ ") VALUES(?,?,?,?,?,?,?,?,?,?);");
			db.beginTransaction();
			for (DtoHorario DtoCatalog : obj) {
				try {
					insStmnt.bindLong(1, DtoCatalog.getId());
				} catch (Exception e) {
					insStmnt.bindNull(1);
				}
				try {
					insStmnt.bindLong(2, DtoCatalog.getSalon());
				} catch (Exception e) {
					insStmnt.bindNull(2);
				}
				try {
					insStmnt.bindString(3, DtoCatalog.getMateria());
				} catch (Exception e) {
					insStmnt.bindNull(3);
				}
				try {
					insStmnt.bindString(4, DtoCatalog.getGrupo());
				} catch (Exception e) {
					insStmnt.bindNull(4);
				}
				try {
					insStmnt.bindString(5, DtoCatalog.getProfesor());
				} catch (Exception e) {
					insStmnt.bindNull(5);
				}
				try {
					insStmnt.bindString(6, DtoCatalog.getLunes());
				} catch (Exception e) {
					insStmnt.bindNull(6);
				}
				try {
					insStmnt.bindString(7, DtoCatalog.getMartes());
				} catch (Exception e) {
					insStmnt.bindNull(7);
				}
				try {
					insStmnt.bindString(8, DtoCatalog.getMiercoles());
				} catch (Exception e) {
					insStmnt.bindNull(8);
				}
				try {
					insStmnt.bindString(9, DtoCatalog.getJueves());
				} catch (Exception e) {
					insStmnt.bindNull(9);
				}
				try {
					insStmnt.bindString(10, DtoCatalog.getViernes());
				} catch (Exception e) {
					insStmnt.bindNull(10);
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
	public int Insert(DtoHorario obj) {
		db = helper.getWritableDatabase();
		ContentValues cv;
		int resp = 0;
		try {
			db.beginTransaction();
			cv = new ContentValues();
			cv.put(ID, obj.getId());
			cv.put(MATERIA, obj.getMateria());
			cv.put(GRUPO, obj.getGrupo());
			cv.put(SALON, obj.getSalon());
			cv.put(PROFESOR, obj.getProfesor());
			cv.put(LUNES, obj.getLunes());
			cv.put(MARTES, obj.getMartes());
			cv.put(MIERCOLES, obj.getMiercoles());
			cv.put(JUEVES, obj.getJueves());
			cv.put(VIERNES, obj.getViernes());
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
	public List<DtoHorario> Select() {
		db = helper.getReadableDatabase();
		cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " ORDER BY horario.id ASC", null);
		List<DtoHorario> obj = new ArrayList<DtoHorario>();
		DtoHorario catalogo;
		if (cursor.moveToFirst()) {
			int id = cursor.getColumnIndexOrThrow(ID);
			int materia = cursor.getColumnIndexOrThrow(MATERIA);
			int salon = cursor.getColumnIndexOrThrow(SALON);
			int grupo = cursor.getColumnIndexOrThrow(GRUPO);
			int profesor = cursor.getColumnIndexOrThrow(PROFESOR);
			int lunes = cursor.getColumnIndexOrThrow(LUNES);
			int martes = cursor.getColumnIndexOrThrow(MARTES);
			int miercoles = cursor.getColumnIndexOrThrow(MIERCOLES);
			int jueves = cursor.getColumnIndexOrThrow(JUEVES);
			int viernes = cursor.getColumnIndexOrThrow(VIERNES);
			do {
				catalogo = new DtoHorario();
				catalogo.setId(cursor.getInt(id));
				catalogo.setMateria(cursor.getString(materia));
				catalogo.setSalon(cursor.getInt(salon));
				catalogo.setGrupo(cursor.getString(grupo));
				catalogo.setProfesor(cursor.getString(profesor));
				catalogo.setLunes(cursor.getString(lunes));
				catalogo.setMartes(cursor.getString(martes));
				catalogo.setMiercoles(cursor.getString(miercoles));
				catalogo.setJueves(cursor.getString(jueves));
				catalogo.setViernes(cursor.getString(viernes));
				obj.add(catalogo);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return obj;
	}

	/**
	 * Select
	 */
	public List<DtoHorario> SelectAvailableRooms(String day, String time) {
		db = helper.getReadableDatabase();
		query = "SELECT DISTINCT salon, "
				+ day
				+ " as day  \n"
				+ "FROM horario  \n"
				+ "WHERE salon   \n"
				+ "NOT IN (SELECT DISTINCT salon FROM horario WHERE  \n"
				+ day
				+ " BETWEEN time('now', 'localtime', '-01:30:00') AND time('now', 'localtime')) \n"
				+ "AND (day LIKE '%" + time + "%' "
				+ (time.equals("AM") ? "OR day LIKE '%PM%')" : ")") + "\n"
				+ "AND time('now','localtime'"+(time.equals("PM")?",'-12:00:00'":"")+")<=day\n"
				+ "ORDER BY salon ASC, day ASC";
		cursor = db.rawQuery(query, null);
		List<DtoHorario> catalog = new ArrayList<DtoHorario>();
		DtoHorario obj;
		int salonInit = 0;
		String timeInit = "";
		if (cursor.moveToFirst()) {
			salonInit = cursor.getInt(cursor.getColumnIndexOrThrow(SALON));
			timeInit = cursor.getString(cursor.getColumnIndexOrThrow("day"));
			do {
				obj = new DtoHorario();
				obj.setSalon(cursor.getInt(cursor.getColumnIndexOrThrow(SALON)));
				obj.setDay(cursor.getString(cursor.getColumnIndexOrThrow("day")));
				if (salonInit == obj.getSalon()) {
					if (timeInit.equals(cursor.getString(cursor
							.getColumnIndexOrThrow("day")))) {
						catalog.add(obj);
					} else {
						timeInit = cursor.getString(cursor
								.getColumnIndexOrThrow("day"));
					}
				} else {
					salonInit = cursor.getInt(cursor
							.getColumnIndexOrThrow(SALON));
					timeInit = cursor.getString(cursor
							.getColumnIndexOrThrow("day"));
					catalog.add(obj);
				}
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return catalog;
	}
}
