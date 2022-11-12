package com.example.dicodingintermediatesubmission1.data.network.model.response.stories.getstories


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "storyTable")
@Parcelize
data class Story(
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("description")
    var description: String?,
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @SerializedName("lat")
    var lat: Double?,
    @SerializedName("lon")
    var lon: Double?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("photoUrl")
    var photoUrl: String?
): Parcelable