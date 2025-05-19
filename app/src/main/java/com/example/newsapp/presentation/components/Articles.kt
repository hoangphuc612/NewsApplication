package com.example.newsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticleData

fun LazyListScope.articlesUi(
    articles: LazyPagingItems<ArticleData>,
    onArticleClick: (String) -> Unit,
) {

    val isLoadMore = articles.loadState.append is LoadState.Loading

    items(
        count = articles.itemCount,
        key = { it },
    ) {
        val article = articles[it]
        if (article != null) {
            ArticleCard(
                articleData = article,
                onArticleClick = onArticleClick
            )
        }
    }

    if (isLoadMore) {
        item {
            BottomLoading()
        }
    }
}

@Composable
fun ArticleCard(
    articleData: ArticleData,
    onArticleClick: (String) -> Unit,
) {
    Card(
        onClick = { articleData.uuid?.let { onArticleClick(it) } },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            if (!articleData.imageUrl.isNullOrEmpty()) {
                ArticleHeaderImage(articleData.imageUrl)
            }
            ArticleTitle(
                modifier = Modifier.padding(16.dp),
                title = articleData.title ?: "",
            )
            ArticleDescription(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                description = articleData.description ?: ""
            )
        }
    }
}

@Composable
fun ArticleTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = title,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun ArticleDescription(modifier: Modifier = Modifier, description: String) {
    Text(
        modifier = modifier,
        text = description,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ArticleHeaderImage(imageUrl: String?) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading) {
            NewsProgressIndicator()
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop,
            painter = if (isError.not()) {
                imageLoader
            } else {
                painterResource(R.drawable.ic_launcher_foreground)
            },
            contentDescription = null,
        )
    }
}

@Preview("ArticlesPreview")
@Composable
private fun ArticlePreview() {
    Surface {
        ArticleCard(
            articleData = ArticleData(
                uuid = "39a3f776-3c94-4dfc-8796-0f5cdcb61eeb",
                title = "Wolfe Research Upgrades MoonLake Immunotherapeutics (MLTX), Sets \$61 Target | MLTX Stock News",
                imageUrl = "https://cdn.jbnews.com/news/thumbnail/202505/1476606_1313532_5225_v150.jpg"
            ),
            onArticleClick = {}
        )
    }
}
