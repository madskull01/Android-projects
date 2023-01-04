package com.example.notesrealm

import android.app.Application
import android.util.Log
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration


lateinit var taskApp: App

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // init Realm

      Realm.init(this)
//
//        val configuration=RealmConfiguration.Builder()
//            .name("Notes.db")
//            .deleteRealmIfMigrationNeeded()
//            .allowWritesOnUiThread(true)
//            .allowQueriesOnUiThread(true)
//            .schemaVersion(0)
//            .build()
//
//        Realm.setDefaultConfiguration(configuration)
        taskApp = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .build())


    }
}