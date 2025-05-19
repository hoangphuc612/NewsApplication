package com.example.newsapp.data.repository.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.data.model.ArticleData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey
    @Expose
    @SerializedName("uuid")
    val uuid: String,
    @Expose
    @SerializedName("title")
    val title: String? = null,
    @Expose
    @SerializedName("description")
    val description: String? = null,
    @Expose
    @SerializedName("description")
    val imageUrl: String? = null,
) {

    companion object {
        fun fromArticleData(article: ArticleData): ArticleEntity? {
            return if (article.uuid == null) null else ArticleEntity(
                uuid = article.uuid,
                title = article.title,
                description = article.description,
                imageUrl = article.imageUrl,
            )
        }
    }
}
