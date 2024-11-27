package com.gihan.composetutorial.gyms.prsentation.gymsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import com.gihan.composetutorial.R
import com.gihan.composetutorial.gyms.domain.Gym
import com.gihan.composetutorial.ui.theme.ComposeTutorialTheme


@Composable
fun GymScreen(
    state: GymsScreenState,
    onItemClick: (Int) -> Unit,
    onFavouriteClick: (Int, Boolean) -> Unit
) {
    ComposeTutorialTheme {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(0.dp, 30.dp, 0.dp, 30.dp)) {
                items(state.gyms) { gym ->
                    GymItem(
                        gym = gym,
                        onFavouriteItemClick = { id, oldValue -> onFavouriteClick(id, oldValue) },
                        onItemClick = { onItemClick(it) }
                    )
                }
            }
            if (state.isLoading) androidx.compose.material3.CircularProgressIndicator()
            state.error?.let { error ->
                Text(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center)
                        .matchParentSize(),
                    text = error,
                    textAlign = TextAlign.Center
                )
            }

        }

    }
}

@Composable
fun GymItem(
    gym: Gym,
    onFavouriteItemClick: (itemId: Int, Boolean) -> Unit,
    onItemClick: (Int) -> Unit
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp),

        ) {

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
                .clickable {
                    onItemClick(gym.id)
                },
            verticalAlignment = Alignment.CenterVertically,

            ) {
            var favouriteIcon =
                if (gym.favourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder

            GymIcon(Icons.Filled.Place, Modifier.weight(.15f), "Gym place image")
            GymDetail(Modifier.weight(.70f), gym)
            GymIcon(favouriteIcon, Modifier.weight(.15f), "Favourite Gym icon", onClick = {
                onFavouriteItemClick(gym.id, gym.favourite)

            })
        }

    }


}

@Composable
fun GymIcon(
    imageVector: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {

    Image(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },


        )
}

@Composable
fun GymDetail(
    modifier: Modifier,
    gym: Gym,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.purple_200)
        )
        // to add opacity to text color
        CompositionLocalProvider(
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Text(
                text = gym.desc,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

    }
}


@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun _GymScreenPreview() {
//  GymScreen()
}
