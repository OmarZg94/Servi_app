package net.servi.util;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Clase que guarda todos los SharedPreferences. El constructor recive como
 * parametro un {@link Context}. Guarda datos de tipo - {@link String} -
 * {@link Integer} - {@link Boolean} - {@link Set} de {@link String} -
 * {@link Long}. También cuenta con un método para limpiar todos los datos del
 * sharedpreferences. Para el archivo donde se guardaran dichos datos, se
 * obtiene de {@link Config}, el archivo tendrá el nombre que contiene la
 * constante {@link #NAME_SHARE_PREFERENCE}.
 */
public class MySharedPreferences {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;

	// ===========================================================
	// Constructors
	// ===========================================================
	/**
	 * Constructor MySharedPreferences
	 * 
	 * @param context
	 */
	public MySharedPreferences(Context context) {
		mSharedPreferences = context.getSharedPreferences(
				Config.NAME_SHARE_PREFERENCE, Context.MODE_PRIVATE);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	public boolean clearInformation() {
		try {
			mEditor.clear();
			mEditor.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * putString Método que sirve para guardar un String, se envía la llave que
	 * se usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link String}.
	 */
	public void putString(String key, String defValue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putString(key, defValue);
		mEditor.commit();
	}

	/**
	 * getString Método que sirve para obtener un String, se envía la llave que
	 * se usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link String}.
	 * @return - {@link String}.
	 */
	public String getString(String key, String defValue) {
		return mSharedPreferences.getString(key, defValue);
	}

	/**
	 * putInt Método que sirve para guardar un int, se envía la llave que se
	 * usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Integer}.
	 */
	public void putInt(String key, int defValue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putInt(key, defValue);
		mEditor.commit();
	}

	/**
	 * getInt Método que sirve para obtener un int, se envía la llave que se
	 * usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Integer}.
	 * @return - {@link Integer}.
	 */
	public int getInt(String key, int defValue) {
		return mSharedPreferences.getInt(key, defValue);
	}

	/**
	 * putBoolean Método que sirve para guardar un boolean, se envía la llave
	 * que se usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Boolean}.
	 */
	public void putBoolean(String key, boolean defValue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putBoolean(key, defValue);
		mEditor.commit();
	}

	/**
	 * getBoolean Método que sirve para obtener un boolean, se envía la llave
	 * que se usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Boolean}.
	 * @return - {@link Boolean}.
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mSharedPreferences.getBoolean(key, defValue);
	}

	/**
	 * putArrayString Método que sirve para guardar un Set<String>, se envía la
	 * llave que se usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Set} de {@link String}.
	 */
	public void putArrayString(String key, Set<String> defValue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putStringSet(key, defValue);
		mEditor.commit();
	}

	/**
	 * getArrayString Método que sirve para obtener un Set<String>, se envía la
	 * llave que se usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Set} de {@link String}.
	 * @return - {@link Set} de {@link String}.
	 */
	public Set<String> getArrayString(String key, Set<String> defValue) {
		return mSharedPreferences.getStringSet(key, defValue);
	}

	/**
	 * putLong Método que sirve para guardar un Long, se envía la llave que se
	 * usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Long}.
	 */
	public void putLong(String key, Long defValue) {
		mEditor = mSharedPreferences.edit();
		mEditor.putLong(key, defValue);
		mEditor.commit();
	}

	/**
	 * getLong Método que sirve para obtener un Long, se envía la llave que se
	 * usará para guardarlo, y el valor
	 * 
	 * @param key
	 *            - {@link String}.
	 * @param defValue
	 *            - {@link Long}.
	 * @return - {@link Long}.
	 */
	public Long getLong(String key, Long defValue) {
		return mSharedPreferences.getLong(key, defValue);
	}
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
