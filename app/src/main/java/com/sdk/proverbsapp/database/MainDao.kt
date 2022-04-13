package com.sdk.proverbsapp.database

import androidx.room.*
import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.Utils

@Dao
interface MainDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveProverb(proverb: Proverb)

    @Query("SELECT * FROM ${Utils.TABLE_NAME} ORDER BY id DESC")
    fun getAllProverbs(): List<Proverb>

    @Delete
    fun deleteProverb(proverb: Proverb)

    @Query("DELETE FROM ${Utils.TABLE_NAME}")
    fun deleteAllProverbs()
}