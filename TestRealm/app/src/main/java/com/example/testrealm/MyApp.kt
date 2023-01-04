package com.example.testrealm

import android.app.Application

import io.realm.Realm
import io.realm.log.LogLevel
import io.realm.log.RealmLog
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration

lateinit var taskApp: App

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()


        Realm.init(this)

        taskApp = App(AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID).build())

        if (BuildConfig.DEBUG) {
            RealmLog.setLevel(LogLevel.ALL)
        }
    }
}