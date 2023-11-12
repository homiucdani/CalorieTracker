package com.example.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.core_ui.LocalSpacing
import com.example.domain.model.TrackedFood
import core.R

@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val spacing = LocalSpacing.current

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(1.dp, RoundedCornerShape(5.dp))
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(end = spacing.spaceMedium)
            .height(100.dp)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = trackedFood.imageUrl,
            contentDescription = trackedFood.foodName,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_burger),
            fallback = painterResource(id = R.drawable.ic_burger),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .padding(spacing.spaceSmall)
        )
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = trackedFood.foodName,
                style = MaterialTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = stringResource(
                    id = R.string.nutrient_info,
                    trackedFood.amount,
                    trackedFood.calories
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDeleteClick() }
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = trackedFood.carbs,
                    unit = stringResource(id = R.string.grams),
                    modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                )

                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = trackedFood.protein,
                    unit = stringResource(id = R.string.grams),
                    modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                )

                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = trackedFood.fat,
                    unit = stringResource(id = R.string.grams),
                    modifier = Modifier.padding(horizontal = spacing.spaceSmall)
                )
            }
        }
    }
}
