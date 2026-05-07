package com.example.login2;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class IdiomaUtils extends Application {

    private static final String PREFS      = "optichelp_prefs";
    private static final String KEY_IDIOMA = "idioma";

    @Override
    public void onCreate() {
        super.onCreate();
        aplicarIdioma(this);
    }

    public static void aplicarIdioma(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String idiomaGuardado   = prefs.getString(KEY_IDIOMA, null);

        String idioma;
        if (idiomaGuardado != null) {
            idioma = idiomaGuardado;
        } else {
            String idiomaDispositivo = Locale.getDefault().getLanguage();
            if (idiomaDispositivo.equals("es")) {
                idioma = "es";
            } else {
                idioma = "en";
            }
        }

        Locale locale = new Locale(idioma);
        Locale.setDefault(locale);

        Configuration config = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
        }
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }

    public static void guardarIdioma(Context context, String codigo) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(KEY_IDIOMA, codigo)
                .apply();
    }

    public static String getIdiomaActual(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_IDIOMA, Locale.getDefault().getLanguage());
    }
}