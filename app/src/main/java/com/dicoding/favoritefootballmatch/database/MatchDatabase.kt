package com.dicoding.favoritefootballmatch.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dicoding.favoritefootballmatch.favorites.MatchTable
import org.jetbrains.anko.db.*

class MatchDatabase(ctx: Context) : ManagedSQLiteOpenHelper(ctx, MainDatabase.DATABASE_EVENT_NAME, null, 1) {
    companion object {
        private var instance: MatchDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): MatchDatabase {
            if (instance == null) {
                instance = MatchDatabase(ctx.applicationContext)
            }
            return instance as MatchDatabase
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(MatchTable.TABLE_NAME, true,
                MatchTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                MatchTable.ID_EVENT to TEXT,
                MatchTable.EVENT_DATE to TEXT,
                MatchTable.HOME_TEAM to TEXT,
                MatchTable.HOME_SCORE to TEXT,
                MatchTable.AWAY_TEAM to TEXT,
                MatchTable.AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(MatchTable.TABLE_NAME, true)
    }

    val Context.eventDB: MatchDatabase
        get() = getInstance(applicationContext)

}