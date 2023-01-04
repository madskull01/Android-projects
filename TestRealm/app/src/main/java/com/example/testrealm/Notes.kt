package com.example.testrealm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField
import org.bson.types.ObjectId
import java.util.*


open class Notes(
    @PrimaryKey @RealmField("_id")
    var id:String=UUID.randomUUID().toString(),
    var title:String="",
    var description:String=""
): RealmObject()