package quanpnph29471.example.quanpnph29471_asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import quanpnph29471.example.quanpnph29471_asm.Database.MyDbhelper;
import quanpnph29471.example.quanpnph29471_asm.Model.Task;
import quanpnph29471.example.quanpnph29471_asm.Model.User;

public class UserDAO {
    MyDbhelper dbHelper ;
    SQLiteDatabase db;

    public UserDAO(Context context) {
        dbHelper = new MyDbhelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public ArrayList<User> getList (){
        String sql ="SELECT * FROM tb_user";
        return getData(sql);
    }

    public ArrayList<User> getData (String sql,String...SelectAvgs){
        ArrayList<User> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,SelectAvgs);
        if(c.getCount()>0){
            c.moveToFirst();
            do {
                User obj = new User();
                obj.setId(c.getInt(0));
                obj.setUsername(c.getString(1));
                obj.setPassword(c.getString(2));
                obj.setEmail(c.getString(3));
                obj.setFullname(c.getString(4));

                //bo cho doi tuong vao danh sach
                list.add(obj);
            }while (c.moveToNext());
        }
        return list ;
    }
    public long insert (User obj){
        ContentValues values = new ContentValues();
        values.put("username",obj.getUsername());
        values.put("password",obj.getPassword());
        values.put("email",obj.getEmail());
        values.put("fullname",obj.getFullname());
        return db.insert("tb_user",null,values);
    }

    public int update (User obj){
        ContentValues values = new ContentValues();
        values.put("username",obj.getUsername());
        values.put("password",obj.getPassword());
        values.put("email",obj.getEmail());
        values.put("fullname",obj.getFullname());
        return db.update("tb_user",values,"id=?",new String[]{String.valueOf(obj.getId())});
    }

    public int delete (User obj){
        return db.delete("tb_user","id=?",new String[]{String.valueOf(obj.getId())});
    }

    public int checkLogin(String name, String password) {
        String sql = "SELECT * FROM tb_user WHERE username=? AND password=?";
        ArrayList<User> list = getData(sql, name, password);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }

    public User getUserToChangePass (String username,String email){
//        String sql ="SELECT * FROM tb_user WHERE username WHERE name LIKE '%"+ username +"%' AND email LIKE '%" + email +"%' ";
//        ArrayList<User> list = getData(sql, username, email);
        String sql = "SELECT * FROM tb_user WHERE username=? AND email=?";
        ArrayList<User> list = getData(sql, username, email);
        return list.get(0);
    }

    public int checkChangePass(String name, String email) {
        String sql = "SELECT * FROM tb_user WHERE username=? AND email=?";
        ArrayList<User> list = getData(sql, name, email);
        if (list.size() == 0) {
            return -1;
        }
        return 1;
    }
}
