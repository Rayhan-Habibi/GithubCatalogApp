package com.example.mandarinkatalog.database

import android.provider.BaseColumns

object RepoContract {
    object RepoData : BaseColumns {
        const val TABLE_NAME = "repos"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_DESCRIPTION = "description"
        const val COLUMN_NAME_PROFILEPICTURE = "profile_picture"
    }
}