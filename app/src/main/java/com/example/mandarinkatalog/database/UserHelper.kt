package com.example.mandarinkatalog.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.mandarinkatalog.api.ResponseItem

class UserHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_NAME = "User.db"
        const val DATABASE_VERSION = 1

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${UserContract.UserData.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${UserContract.UserData.COLUMN_NAME_USERNAME} TEXT," +
            "${UserContract.UserData.COLUMN_NAME_PASSWORD} TEXT," +
            "${UserContract.UserData.COLUMN_NAME_GITHUB} TEXT)"

        private const val SQL_CREATE_REPOS =
            "CREATE TABLE ${RepoContract.RepoData.TABLE_NAME} (" +
            "${RepoContract.RepoData.COLUMN_NAME_ID} INTEGER PRIMARY KEY, " +
            "${RepoContract.RepoData.COLUMN_NAME_USERNAME} TEXT, " +
            "${RepoContract.RepoData.COLUMN_NAME_NAME} TEXT, " +
            "${RepoContract.RepoData.COLUMN_NAME_DESCRIPTION} TEXT," +
            "${RepoContract.RepoData.COLUMN_NAME_PROFILEPICTURE} TEXT)"

        private const val SQL_DELETE_REPOS =
            "DROP TABLE IF EXISTS ${RepoContract.RepoData.TABLE_NAME}"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${UserContract.UserData.TABLE_NAME}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
        db?.execSQL(SQL_CREATE_REPOS)
    }



    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        db?.execSQL(SQL_DELETE_REPOS)
        onCreate(db)
    }

    fun saveReposForUser(username: String, repos: List<ResponseItem>) {
        val dbHelper = UserHelper(context)
        val db = dbHelper.writableDatabase

        db.beginTransaction()
        try {
            // Clear existing repos for this user
            db.delete("repos", "username = ?", arrayOf(username))

            for (repo in repos) {
                val values = ContentValues().apply {
                    put("id", repo.id ?: 0)
                    put("username", username)
                    put("name", repo.name ?: "No Name")
                    put("description", repo.description ?: "No Description")
                    put("profile_picture", repo.owner?.avatarUrl ?: "")
                }
                db.insert("repos", null, values)
            }

            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun getReposForUser(username: String): List<ResponseItem> {
        val repos = mutableListOf<ResponseItem>()
        val db = readableDatabase
        val cursor = db.query("repos", null, "username = ?", arrayOf(username), null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val repo = ResponseItem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                )
                repos.add(repo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return repos
    }

}