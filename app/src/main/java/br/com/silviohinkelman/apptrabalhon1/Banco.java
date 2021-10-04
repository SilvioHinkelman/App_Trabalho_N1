package br.com.silviohinkelman.apptrabalhon1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "AppTrabalhoN1";
    private static final int VERSAO = 1;

    public Banco(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS cars ( " +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "marca TEXT NOT NULL," +
                "modelo TEXT NOT NULL," +
                "color TEXT ) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
