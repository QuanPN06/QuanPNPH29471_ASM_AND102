package quanpnph29471.example.quanpnph29471_asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.Database.MyDbhelper;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;

public class TaskDAO {
    MyDbhelper dbHelper ;
    SQLiteDatabase db;

    public TaskDAO(Context context) {
        dbHelper = new MyDbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Task> getList(){
        String sql = "SELECT * FROM tb_task";
        return getData(sql);
    }
    public ArrayList<Task> getData (String sql,String...SelectAvgs){
        ArrayList<Task> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectAvgs);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                Task obj = new Task();
                obj.setId(c.getInt(0));
                obj.setName(c.getString(1));
                obj.setContent(c.getString(2));
                obj.setStatus(c.getInt(3));
                obj.setStart(c.getString(4));
                obj.setEnd(c.getString(5));

                //bo cho doi tuong vao danh sach
                list.add(obj);
            }while (c.moveToNext());
        }
        return list ;
    }

    public long insert (Task obj){
        ContentValues values = new ContentValues();
        values.put("name",obj.getName());
        values.put("content",obj.getContent());
        values.put("status",obj.getStatus());
        values.put("start",obj.getStart());
        values.put("end_date",obj.getEnd());
        return db.insert("tb_task",null,values);
    }

    public int update (Task obj){
        ContentValues values = new ContentValues();
        values.put("name",obj.getName());
        values.put("content",obj.getContent());
        values.put("status",obj.getStatus());
        values.put("start",obj.getStart());
        values.put("end_date",obj.getEnd());
        return db.update("tb_task",values,"id=?",new String[]{String.valueOf(obj.getId())});
    }

    public int delete (Task obj){
        return db.delete("tb_task","id=?",new String[]{String.valueOf(obj.getId())});
    }

    public ArrayList<Task> Search(String name){
        String sql = "SELECT * FROM tb_task WHERE name LIKE '%"+ name +"%' ";
        return getData(sql);
    }
}
