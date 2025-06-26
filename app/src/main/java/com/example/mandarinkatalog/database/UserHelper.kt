package com.example.mandarinkatalog.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.mandarinkatalog.api.Owner
import com.example.mandarinkatalog.api.ResponseItem

class UserHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_NAME = "User.db"
        const val DATABASE_VERSION = 2

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
            "${RepoContract.RepoData.COLUMN_NAME_PROFILEPICTURE} TEXT, " +
            "starred INTEGER DEFAULT 0)"

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
            for (repo in repos) {
                // 🔍 Check if repo already exists and get its current starred status
                val cursor = db.query(
                    "repos",
                    arrayOf("starred"),
                    "id = ? AND username = ?",
                    arrayOf(repo.id.toString(), username),
                    null, null, null
                )

                val existingStarred = if (cursor.moveToFirst()) {
                    cursor.getInt(cursor.getColumnIndexOrThrow("starred"))
                } else {
                    0
                }
                cursor.close()

                // 📦 Prepare values including preserved starred flag
                val values = ContentValues().apply {
                    put("id", repo.id ?: 0)
                    put("username", username)
                    put("name", repo.name ?: "No Name")
                    put("description", repo.description ?: "No Description")
                    put("profile_picture", repo.owner?.avatarUrl ?: "")
                    put("starred", existingStarred) // ✅ keep previous starred status
                }

                // 🧠 Insert or replace while preserving starred status
                db.insertWithOnConflict("repos", null, values, SQLiteDatabase.CONFLICT_REPLACE)
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

    fun toggleStarredStatus(repoId: Int, username: String) {
        val db = writableDatabase

        val cursor = db.query(
            "repos",
            arrayOf("starred"),
            "id = ? AND username = ?",
            arrayOf(repoId.toString(), username),
            null, null, null
        )

        var newStarredValue = 1 // default to true

        if (cursor.moveToFirst()) {
            val currentStarred = cursor.getInt(cursor.getColumnIndexOrThrow("starred"))
            newStarredValue = if (currentStarred == 1) 0 else 1
        }
        cursor.close()

        val values = ContentValues().apply {
            put("starred", newStarredValue)
        }

        db.update(
            "repos",
            values,
            "id = ? AND username = ?",
            arrayOf(repoId.toString(), username)
        )
    }

    fun updateRepoStarred(repoId: Int, isStarred: Boolean) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("starred", if (isStarred) 1 else 0)
        }
        db.update("repos", values, "id = ?", arrayOf(repoId.toString()))
        Log.d("DB", "Updated repo $repoId to starred=$isStarred")
    }

    fun getStarredRepos(username: String): List<ResponseItem> {
        val repos = mutableListOf<ResponseItem>()
        val db = readableDatabase
        val cursor = db.query(
            "repos",
            null,
            "username = ? AND starred = 1",
            arrayOf(username),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val repo = ResponseItem(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    owner = Owner(
                        avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow("profile_picture"))
                    ),
                    starred = 1 // mark as starred
                )
                repos.add(repo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        Log.d("DB", "Loaded ${repos.size} starred repos")

        return repos

    }




}