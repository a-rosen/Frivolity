package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.frivolity.ui.models.SortMethods
import com.example.frivolity.ui.screens.ItemDetailScreenState

@Composable
fun StatsRow(
    state: ItemDetailScreenState,
    iLevel: Int,
    levelToEquip: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "iLEVEL",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = iLevel.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "EQUIP LEVEL",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = levelToEquip.toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }

        if (state.sortMethod == SortMethods.TOTAL) {
            state.cheapestTotalPrice?.let {
                CheapestColumn(
                    prices = it,
                    state = state
                )
            }
        } else {
            state.cheapestUnitPrice?.let {
                CheapestColumn(
                    prices = it,
                    state = state
                )
            }
        }
    }
}


@Preview
@Composable
fun StatsRowPreview() {
    StatsRow(
        ItemDetailScreenState.EMPTY,
        625,
        80,
    )
}