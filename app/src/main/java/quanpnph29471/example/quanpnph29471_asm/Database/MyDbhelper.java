package quanpnph29471.example.quanpnph29471_asm.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDbhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "data";
    private static final int DB_VERSION = 1;

    public MyDbhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String TABLE_USER_CREATE ="CREATE TABLE IF NOT EXISTS " +
            "tb_user (" +
            "    id       INTEGER   PRIMARY KEY AUTOINCREMENT," +
            "    username TEXT (50) NOT NULL" +
            "                       UNIQUE," +
            "    password TEXT (50) NOT NULL," +
            "    email TEXT (50) NOT NULL," +
            "    fullname    TEXT (50) NOT NULL" +
            ")";

    public static final String TABLE_TASK_CREATE ="CREATE TABLE " +
            "tb_task (" +
            "    id      INTEGER PRIMARY KEY AUTOINCREMENT," +
            "    name    TEXT    NOT NULL" +
            "                    UNIQUE," +
            "    content TEXT    NOT NULL," +
            "    status  INTEGER NOT NULL," +
            "    start   TEXT    NOT NULL," +
            "    end_date     TEXT    NOT NULL " +
            ")";
    public static final String insert_task = "INSERT INTO tb_task (name,content,status,start,end_date) VALUES " +
            "('Da bong','ngay mai da bong nhe','0','22/11/2023','24/11/2023'), " +
            "('Hoc tiep','Hoc , hoc nua , hoc mai','0','22/11/2023','24/11/2023') " ;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER_CREATE);
        db.execSQL(TABLE_TASK_CREATE);
        db.execSQL(insert_task);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("DROP TABLE IF EXISTS tbl_user");
            db.execSQL("DROP TABLE IF EXISTS tb_task");
            onCreate(db);
        }
    }
}
