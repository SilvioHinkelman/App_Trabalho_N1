package br.com.silviohinkelman.apptrabalhon1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CarDAO {

    public static void inserir(Context context, Cars car){
        ContentValues valores = new ContentValues();
        valores.put("marca", car.getMarca());
        valores.put("modelo", car.getModelo());
        valores.put("color", car.getColor());

        Banco conect = new Banco(context);
        SQLiteDatabase db = conect.getWritableDatabase();

        db.insert("cars", null, valores);
    }

    public static void editar(Context context, Cars car){
        ContentValues valores = new ContentValues();
        valores.put("marca", car.getMarca());
        valores.put("modelo", car.getModelo());
        valores.put("color", car.getColor());

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.update("cars", valores, "id = " + car.getId(), null);
    }

    public static void excluir(Context context, int idCar){

        Banco conect = new Banco(context);
        SQLiteDatabase db = conect.getWritableDatabase();

        db.delete("cars","id = " + idCar, null);
    }

    public static List<Cars> getCars(Context context) {
        List<Cars> lista = new ArrayList<>();

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM cars ORDER BY marca",null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Cars c = new Cars();
                c.setId(cursor.getInt(0));
                c.setMarca(cursor.getString(1));
                c.setModelo(cursor.getString(2));
                c.setColor(cursor.getString(3));
                lista.add(c);
            }while (cursor.moveToNext());
        }
        return lista;
    }

    public static Cars getCarsById(Context context, int idCars) {

        Banco conn = new Banco(context);
        SQLiteDatabase db = conn.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM cars WHERE id= " + idCars, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Cars c = new Cars();
            c.setId(cursor.getInt(0));
            c.setMarca(cursor.getString(1));
            c.setModelo(cursor.getString(2));
            c.setColor(cursor.getString(3));
            return c;
        } else {
            return null;
        }
    }
}
