package com.example.athira.sahapaadi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

  public DBHelper(Context context) {
    super(context, "sahapaadi.db", null, 1);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
  }

  public void initRegistration() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("create table UNIVERSITY ( uid integer primary key, uname text )");
    database.execSQL("create table PROGRAM ( pid integer primary key, pname text, year integer )");
    database.execSQL("create table SCHEME ( sid integer primary key, sname text )");
    database.execSQL(
        "create table UNIVERSITYPROGRAM(upid integer primary key, uid integer , pid integer )");
    database.execSQL("create table SYLLABUS ( syid integer, upid integer , sid integer)");
  }

  public void initUser(){
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("create table user ( var integer primary key, value text )");
  }

  public void updateUser(String sem){
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("update user set value='"+sem+"' where var=4");
  }

  public void putUser(String uid, String name, String syid, String sem) {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("insert into user values(1,'" + uid + "')");
    database.execSQL("insert into user values(2,'" + name + "')");
    database.execSQL("insert into user values(3,'" + syid + "')");
    database.execSQL("insert into user values(4,'" + sem + "')");
  }

  public Cursor getUser(){
    return this.getReadableDatabase().rawQuery("select var,value from user order by var",null);
  }

  public void revUser() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("drop table if exists user");
  }

  public void putUniversity(String uid, String uname) {
    this.getWritableDatabase()
        .execSQL("insert into UNIVERSITY values(" + uid + ",'" + uname + "')");
  }

  public void putScheme(String sid, String sname) {
    this.getWritableDatabase().execSQL("insert into SCHEME values(" + sid + ",'" + sname + "')");
  }

  public void putProgram(String pid, String pname, String year) {
    this.getWritableDatabase()
        .execSQL("insert into PROGRAM values(" + pid + ",'" + pname + "', " + year + ")");
  }

  public void putUniversityProgram(String upid, String uid, String pid) {
    this.getWritableDatabase()
        .execSQL("insert into UNIVERSITYPROGRAM values(" + upid + "," + uid + "," + pid + ")");
  }

  public void putSyllabus(String syid, String upid, String sid) {
    this.getWritableDatabase()
        .execSQL("insert into SYLLABUS values(" + syid + "," + upid + "," + sid + ")");
  }

  public Cursor getUniversity() {
    return (this.getReadableDatabase().rawQuery("select uid,uname from UNIVERSITY ", null));
  }

  public Cursor getProgram(String uid) {
    return (this.getReadableDatabase()
        .rawQuery("select upid,pname,year from PROGRAM p,UNIVERSITYPROGRAM up where uid="
            + uid
            + " and up.pid=p.pid", null));
  }

  public Cursor getScheme(String upid) {
    return (this.getReadableDatabase()
        .rawQuery(
            "select syid,sname from SCHEME s,SYLLABUS sb where upid=" + upid + " and s.sid=sb.sid ",
            null));
  }

  public void revRegistration() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("drop table if exists UNIVERSITY");
    database.execSQL("drop table if exists PROGRAM");
    database.execSQL("drop table if exists SCHEME");
    database.execSQL("drop table if exists UNIVERSITYPROGRAM");
    database.execSQL("drop table if exists SYLLABUS");
  }

  public void initfav(){
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("create table fav ( rid integer primary key, rname text, descr text, sub integer, isd integer, type integer )");
  }

  public void putfav(String rid, String rname, String descr, String sub, int isd, int type){
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("insert into fav values('"+rid+"','" + rname + "','" + descr + "','" + sub + "','" + isd + "','" + type + "')");
  }

  public Cursor getFav(String sub){
    return this.getReadableDatabase().rawQuery("select rid,rname,descr,sub,isd,type from fav  order by rid desc",null);
  }

  public void initDown(){
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("create table down ( rid integer primary key, rname text, descr text, sub integer, isd integer, type integer )");
  }

  public void putDown(String rid, String rname, String descr, String sub, int isd, int type) {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("insert into down values('"+rid+"','" + rname + "','" + descr + "','" + sub + "','" + isd + "','" + type + "')");
  }

  public Cursor getDown(String sub){
    return this.getReadableDatabase().rawQuery("select rid,rname,descr,sub,isd,type from down order by rid desc",null);
  }

  public void initsub(){
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("create table subject ( sid integer primary key, sname text, pdf integer, ppt integer, notes integer, qps integer, books integer )");
  }

  public void putSub(String sid, String name, String pdf, String ppt,String notes, String qps, String books) {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("insert into subject values('"+sid+"','" + name + "','" + pdf + "','" + ppt + "','" + notes + "','" + qps + "','" + books + "')");
  }

  public Cursor getSub(){
    return this.getReadableDatabase().rawQuery("select sid,sname,pdf,ppt,notes,qps,books from subject order by sid",null);
  }

  public void revSub() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("drop table if exists subject");
  }

  public void revFav() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("drop table if exists fav");
  }

  public void revDown() {
    SQLiteDatabase database = this.getWritableDatabase();
    database.execSQL("drop table if exists down");
  }

}
