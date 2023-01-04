package com.example.notesrealm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.RealmField
import java.util.*

@RealmClass
open class Notes(
    @PrimaryKey@RealmField("_id")
    var id:String= UUID.randomUUID().toString(),
    var title:String="",
    var description:String=""
): RealmObject()