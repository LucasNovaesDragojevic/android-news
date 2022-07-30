package com.news.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class News
(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @SerializedName("titulo")
    var title: String = "",
    @SerializedName("texto")
    var text: String = ""
)
