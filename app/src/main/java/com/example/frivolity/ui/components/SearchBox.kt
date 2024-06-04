package com.example.frivolity.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(
    inputTextValue: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(4.dp)
    ) {
        OutlinedTextField(
            value = inputTextValue,
            onValueChange = onValueChange,
            label = { Text("What item are you looking for?") },
            placeholder = { Text("e.g. Allagan Tomestone of Frivolity") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search icon"
                )
            },
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }

}

@Preview
@Composable
fun SearchBoxPreview() {
    SearchBox(inputTextValue = "textfield", onValueChange = {})
}