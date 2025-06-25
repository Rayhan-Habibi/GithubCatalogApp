package com.example.mandarinkatalog.database

import android.provider.BaseColumns

object UserContract {
    object UserData: BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_USERNAME = "username"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_NAME_GITHUB = "github"
    }

}
