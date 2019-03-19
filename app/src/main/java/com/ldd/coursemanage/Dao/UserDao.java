package com.ldd.coursemanage.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ldd.coursemanage.Entity.User;
import com.ldd.coursemanage.util.MyDatabaseHelper;

/**
 * Edit by ldd 2019.3.16
 * 对log表进行数据库操作
 */

public class UserDao {
    private Context context;
    private MyDatabaseHelper dbHelper;
    private final String Tag = "UserDao.java";

    public UserDao(Context context) {
        this.context = context;
        dbHelper = new MyDatabaseHelper(context);
    }

    /**
     * 检查登录表中是否有对应的账号
     */
    public boolean check(int id, String password) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select password from log where student_id = ?";
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    User user = new User();
                    String passwd = cursor.getString(cursor.getColumnIndex("password"));
                    if (passwd.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Log.i(Tag,"检查登录表中是否有对应的账号 error");
            Log.i(Tag,e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    /**
     * 向表中插入数据
     */
    public boolean insertLog(int studentId, String password) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("student_id", studentId);
            values.put("password", password);
            db.insertOrThrow("log", null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Log.i(Tag, "主键重复"+ e.getMessage());
            return false;
        } catch (Exception e) {
            Log.i(Tag, "插入id和密码出错"+e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 修改密码
     */
    public boolean modifyPasswd(User user, String newPasswd) {
        SQLiteDatabase db = null;
        String sql = "update log set password = ? where student_id = ?";
        try {
            db = dbHelper.getWritableDatabase();
            db.beginTransaction();
            if (check(user.getId(), user.getPassword())) {
                db.execSQL(sql, new Object[]{newPasswd, user.getId()});
                db.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        } catch (SQLiteConstraintException e) {
            Log.i(Tag, "修改密码时主键重复"+ e.getMessage());
            return false;
        } catch (Exception e) {
            Log.i(Tag, "修改密码出错"+e.getMessage());
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * @function list all the student_id and password
     */
    public void listAllIdAndPassword() {
        SQLiteDatabase db = null;
        String sql = "select * from log";
        Cursor cursor = null;
        StringBuffer stringBuffer = new StringBuffer("studentid_password");
        try {
            Log.i(Tag,"读取管理员id和密码");
            db = dbHelper.getReadableDatabase();
            db.rawQuery(sql, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    stringBuffer.append(cursor.getInt(cursor.getColumnIndex("student_id")))
                            .append(cursor.getString(cursor.getColumnIndex("password")));
                }
                Log.i(Tag,stringBuffer.toString());
            }
        }catch (Exception e) {
            Log.i(Tag,"读取所有管理员id和密码失败"+e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}