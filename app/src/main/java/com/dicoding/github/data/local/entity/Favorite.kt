package com.dicoding.github.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey (autoGenerate = false)
    var username: String = "",
    var avatarUrl: String = ""
) : Parcelable