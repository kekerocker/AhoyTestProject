package com.dsoft.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favourites_table")
@Parcelize
data class Favourite(
    @PrimaryKey val cityName: String
) : Parcelable
