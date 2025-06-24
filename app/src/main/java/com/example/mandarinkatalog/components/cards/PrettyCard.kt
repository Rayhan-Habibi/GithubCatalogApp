package com.example.mandarinkatalog.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mandarinkatalog.ui.theme.Black
import com.example.mandarinkatalog.ui.theme.Container
import com.example.mandarinkatalog.ui.theme.ContainerSecondary
import com.example.mandarinkatalog.ui.theme.Primary

@Composable
fun PrettyCard(name: String, desc: String) {

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val cardWidth = screenWidth * 0.90f
    val cardHeight = screenHeight * 0.15f

    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(cardHeight)
            .width(cardWidth)
            .clickable(enabled = true, onClick = {})
    ) {
        // Bottom "shadow" card layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(12.dp, 12.dp)
                .background(Container, shape = RoundedCornerShape(8.dp))
        )

        // Main card
        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(2.dp, Primary), // orange border
            colors = CardDefaults.cardColors(containerColor = Primary)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    color = Color(0xFFFFFFFF),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )
                Text(
                    text = desc,
                    color = Color.White
                )

                // Android icon in bottom-right
            }
        }
    }
}