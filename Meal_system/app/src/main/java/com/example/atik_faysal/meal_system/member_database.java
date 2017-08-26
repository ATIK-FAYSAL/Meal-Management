package com.example.atik_faysal.meal_system;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class member_database extends SQLiteOpenHelper
{
    private static final String database_name = "member_database";
    private static final int database_version = 1;
    private static final String m_name = "m_name";
    private static final String m_email = "m_email";
    private static final String m_phone = "m_phone";
    private static final String m_address = "m_address";
    private static final String m_taka = "m_taka";
    private static final String m_parent = "m_parent";
    private static final String table_name = "member_info";
    public SQLiteDatabase db = this.getWritableDatabase();

    public member_database(Context context)
    {
        super(context,database_name,null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "create table "+table_name+" ("+m_name+" Varchar(25),"+m_email+" Varchar(35),"+m_phone+" Varchar(14),"+m_address+" Varchar(40),"+m_taka+" Integer,"+m_parent+" Varchar(14))";
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String add_member_information(String name,String email,String phone,String address,String taka,String parent_phone)
    {
        ContentValues values = new ContentValues();
        try
        {
            values.put(m_name,name);
            values.put(m_email,email);
            values.put(m_phone,phone);
            values.put(m_address,address);
            values.put(m_taka,taka);
            values.put(m_parent,parent_phone);
            db.insert(table_name,null,values);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return "value successfully insert";
    }
    public Cursor GetAllMemberName()
    {
        String sql = "SELECT `m_name` FROM "+table_name;
        Cursor value ;
        value = db.rawQuery(sql,null);
        return value;
    }

    public Cursor GetPersonInformation(String name)
    {

        Cursor value;
        String sql = "SELECT * FROM "+table_name+" where "+m_name+"='"+name+"'";
        value = db.rawQuery(sql,null);
        return value;
    }

    public String delete_member(String name)
    {
        db.delete(table_name,m_name+" = ?",new String[] {name});
        return "Member deleted successfully";
    }
    public int sum_of_member_taka()
    {
        Cursor value = db.rawQuery("SELECT SUM(m_taka) FROM " + table_name, null);
        value.moveToFirst();
        int sum = value.getInt(0);
        value.close();
        return sum;
    }

    public String update_member_data(String get_name,String email,String phone,String address,int taka,String parent_phone)
    {
        SQLiteDatabase Db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(m_email,email);
        values.put(m_phone,phone);
        values.put(m_address,address);
        values.put(m_taka,taka);
        values.put(m_parent,parent_phone);
        Db.update(table_name, values, m_name+" = ?",new String[] {get_name});
        return "Update Successful";
    }

    public Cursor GetNameTaka()
    {
        String sql = "SELECT `m_name`,`m_taka` FROM "+table_name;
        Cursor value ;
        value = db.rawQuery(sql,null);
        return value;
    }

    public void deleteMemberTaka(String name,int x)
    {
        ContentValues values = new ContentValues();
        values.put(m_taka,x);
        db.update(table_name,values,m_name+"=?",new String[]{name});
    }
    public void deleteAll()
    {
        db.execSQL("delete from "+ table_name);
    }
}
