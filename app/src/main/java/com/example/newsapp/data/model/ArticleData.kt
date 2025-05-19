package com.example.newsapp.data.model

import androidx.annotation.Keep
import com.example.newsapp.data.repository.source.local.database.entity.ArticleEntity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class ArticleData(
    @Expose
    val uuid: String? = null,
    @Expose
    val title: String? = null,
    @Expose
    val description: String? = null,
    @Expose
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @Expose
    @SerializedName("published_at")
    val publishedAt: String? = null,
    @Expose
    val source: String? = null,
    @Expose
    val categories: List<String>? = null,
) {
    companion object {
        fun fromArticleEntity(article: ArticleEntity): ArticleData {
            return ArticleData(
                uuid = article.uuid,
                title = article.title,
                description = article.description,
                imageUrl = article.imageUrl,
            )
        }
    }
}
