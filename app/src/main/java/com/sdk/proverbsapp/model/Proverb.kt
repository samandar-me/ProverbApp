package com.sdk.proverbsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdk.proverbsapp.util.Utils.TABLE_NAME
import java.io.Serializable

@Entity(tableName = TABLE_NAME)
data class Proverb(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String
): Serializable
