package net.servi.database;

public class Tables {

	public static final String Table_Horario = "CREATE TABLE horario(id INTEGER, grupo TEXT,"
			+ "materia TEXT, profesor TEXT, salon INTEGER, lunes TEXT, martes TEXT, miercoles TEXT,"
			+ "jueves TEXT, viernes TEXT)";
	
	public static final String Table_Reporte = "CREATE TABLE reporte(id INTEGER, id_horario INTEGER," +
			"ip_alumno TEXT, estado INTEGER, reported_on TEXT)";
	
	public static final String Table_Hueco = "CREATE TABLE hueco(salon INTEGER, dia TEXT, hora TEXT)";
}
