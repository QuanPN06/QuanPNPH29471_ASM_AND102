package quanpnph29471.example.quanpnph29471_asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.Database.MyDbhelper;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;

public class TaskDAO {
    MyDbhelper dbHelper;
    SQLiteDatabase db;

    public TaskDAO(Context context) {
        dbHelper = new MyDbhelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Task> getData(String sql, String... SelectAvgs) {
        ArrayList<Task> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, SelectAvgs);
        Log.d("zzzzzzz", "getData: "+sql);
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                Task obj = new Task();
                obj.setId(c.getInt(0));
                obj.setId_user(c.getInt(1));
                obj.setName(c.getString(2));
                obj.setContent(c.getString(3));
                obj.setStatus(c.getInt(4));
                obj.setStart(c.getString(5));
                obj.setEnd(c.getString(6));

                //bo cho doi tuong vao danh sach
                list.add(obj);
            } while (c.moveToNext());
        }
        return list;
    }

    public ArrayList<Task> getList() {
        String sql = "SELECT * FROM tb_task WHERE status <> -1 ";
        return getData(sql);
    }

//        public ArrayList<Task> getListWithIdUser(){
//        String sql = "SELECT tb_task.* FROM tb_task " +
//                "INNER JOIN tb_user ON tb_task.id_user = tb_user.id" +
//                "WHERE status <> -1";
//        return getData(sql);
//    }

    public long insert(Task obj) {
        ContentValues values = new ContentValues();
        values.put("id_user", obj.getId_user());
        values.put("name", obj.getName());
        values.put("content", obj.getContent());
        values.put("status", obj.getStatus());
        values.put("start", obj.getStart());
        values.put("end_date", obj.getEnd());
        return db.insert("tb_task", null, values);
    }

    public int update(Task obj) {
        ContentValues values = new ContentValues();
        values.put("id_user", obj.getId_user());
        values.put("name", obj.getName());
        values.put("content", obj.getContent());
        values.put("status", obj.getStatus());
        values.put("start", obj.getStart());
        values.put("end_date", obj.getEnd());
        return db.update("tb_task", values, "id=?", new String[]{String.valueOf(obj.getId())});
    }

    public int delete(Task obj) {
        return db.delete("tb_task", "id=?", new String[]{String.valueOf(obj.getId())});
    }

    public ArrayList<Task> Search(String name) {
        String sql = "SELECT * FROM tb_task WHERE name LIKE '%" + name + "%' AND status <> -1 ";
        return getData(sql);
    }

    public ArrayList<Task> getListCancel() {
        String sql = "SELECT * FROM tb_task WHERE status LIKE -1  ORDER BY start ASC";
        return getData(sql);
    }

    public ArrayList<Task> SearchCancelTask(String name) {
        String sql = "SELECT * FROM tb_task WHERE name LIKE '%" + name + "%' AND status LIKE -1";
        return getData(sql);
    }

    public ArrayList<Task> getListCreate(int id) {
        String sql = "SELECT * FROM tb_task WHERE status LIKE 0 AND id_user LIKE "+ id +" ORDER BY start ASC";
        return getData(sql);
    }

    public ArrayList<Task> getListDoing() {
        String sql = "SELECT * FROM tb_task WHERE status LIKE 1 ORDER BY start ASC";
        return getData(sql);
    }

    public ArrayList<Task> getListCompleted() {
        String sql = "SELECT * FROM tb_task WHERE status LIKE 2 ORDER BY start ASC";
        return getData(sql);
    }

    public ArrayList<Task> searchByDate(String start, String end) {
        String sql = "SELECT * FROM tb_task " +
                "WHERE start >= '" + start + "' AND end_date <= '" + end + "'  AND status <> -1  ";
        //OR status <> 2 : de loai bo gia tri status = 2
        return getData(sql);
    }

    public ArrayList<Task> getTasksForDay(String date) {
        String sql = "SELECT * FROM tb_task " +
                "WHERE start <= '" + date + "' AND end_date >= '" + date + "' " +
                "AND status <> -1";

        return getData(sql);
    }

}
