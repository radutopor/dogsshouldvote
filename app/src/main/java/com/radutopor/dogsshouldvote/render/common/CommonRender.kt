package com.radutopor.dogsshouldvote.render.common

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.radutopor.dogsshouldvote.R
import com.radutopor.dogsshouldvote.ui.common.models.ErrorViewState
import java.util.*

@Composable
fun ScreenRender(
    loading: Boolean,
    error: ErrorViewState,
    title: String,
    content: @Composable (InnerPadding) -> Unit,
) = MaterialTheme {
    Scaffold(
        topBar = { Title(title) },
        bodyContent = content
    )
    Loading(loading)
    Error(error)
}

@Composable
fun Title(title: String) = Text(
    text = title,
    color = MaterialTheme.colors.onPrimary,
    style = MaterialTheme.typography.h4,
    modifier = Modifier.fillMaxWidth()
        .background(MaterialTheme.colors.primary)
        .padding(padding())
)

@Composable
fun Loading(loading: Boolean) {
    if (loading) Stack(Modifier.fillMaxSize()) {
        CircularProgressIndicator(Modifier.gravity(Alignment.Center))
    }
}

@Composable
fun Error(error: ErrorViewState) {
    if (error.visibility) Stack(Modifier.fillMaxSize()) {
        Snackbar(
            text = { Text(error.text) },
            action = {
                Text(
                    text = stringResource(R.string.retry),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.secondary
                    ),
                    modifier = Modifier.clickable(onClick = error.onRetryClick)
                )
            },
            modifier = Modifier.gravity(Alignment.BottomCenter)
        )
    }
}

@Composable
fun padding() = dimensionResource(id = R.dimen.padding)

fun randomColour() = Random().run { (1..3).map { nextInt(256) } }.let { Color(it[0], it[1], it[2]) }