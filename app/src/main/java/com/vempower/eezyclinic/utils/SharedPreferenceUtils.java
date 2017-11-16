package com.vempower.eezyclinic.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.vempower.eezyclinic.application.MyApplication;

import java.util.Set;

public class SharedPreferenceUtils {
	private static final Context context = MyApplication.getInstance();
	private static String SHARED_PREFERENCES_FILE_NAME = "myapplicationpref";
	private static SharedPreferences preference;
	
	public static SharedPreferences getSharedPrefrence() {
		if (preference == null) {
			preference = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME,
					Context.MODE_PRIVATE);
		}
		return preference;

	}
	
	
	public static String getStringValueFromSharedPrefarence(String key, String defaultValue) {
		return getSharedPrefrence().getString(key, defaultValue);

	}
	public static boolean getBooleanValueFromSharedPrefarence(String key, boolean defaultValue) {
		return getSharedPrefrence().getBoolean(key, defaultValue);
	}
	
	public static boolean setBooleanValueToSharedPrefarence(String key, boolean value) {
		return getSharedPrefrence().edit().putBoolean(key, value).commit();

	}
	public static boolean setStringValueToSharedPrefarence(String key, String value) {
		return getSharedPrefrence().edit().putString(key, value).commit();

	}
	public static boolean setStringSetToSharedPrefarence(String key, Set<String> value) {
		return getSharedPrefrence().edit().putStringSet(key, value).commit();

	}
	public static boolean setIntValueToSharedPrefarence(String key, int value) {
		return getSharedPrefrence().edit().putInt(key, value).commit();

	}
	public static int getIntValueToSharedPrefarence(String key,int defaultValue) {
		return getSharedPrefrence().getInt(key,defaultValue);

	}

}
