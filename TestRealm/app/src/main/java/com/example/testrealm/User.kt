package com.example.testrealm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

import io.realm.annotations.RealmField
import java.util.*

open class UserInfo(
    @PrimaryKey @RealmField("_id") var id: String = UUID.randomUUID().toString(),
    var _partition: String = "",
    var name: String = ""
): RealmObject()