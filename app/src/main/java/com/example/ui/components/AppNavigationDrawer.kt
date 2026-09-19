package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Roofing
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.WaterDamage
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.AppScreen
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate600
import com.example.ui.theme.Slate800
import com.example.ui.theme.UnipointAmber
import com.example.ui.theme.UnipointNavy
import com.example.ui.theme.UnipointNavyDark

data class DrawerMenuItem(
    val screen: AppScreen,
    val icon: ImageVector,
    val badge: String? = null
)

@Composable
fun AppDrawerContent(
    currentScreen: AppScreen,
    onSelectScreen: (AppScreen) -> Unit,
    onCallPhone: () -> Unit
) {
    val items = listOf(
        DrawerMenuItem(AppScreen.HOME, Icons.Default.Home),
        DrawerMenuItem(AppScreen.CATALOG, Icons.Default.Build, "Catalogo"),
        DrawerMenuItem(AppScreen.CUSTOMIZER, Icons.Default.ViewInAr, "3D Live"),
        DrawerMenuItem(AppScreen.BOOKINGS, Icons.Default.DateRange),
        DrawerMenuItem(AppScreen.ROOFING, Icons.Default.Roofing, "Emergenza"),
        DrawerMenuItem(AppScreen.DRONE, Icons.Default.Navigation, "Perizie"),
        DrawerMenuItem(AppScreen.CHAT, Icons.Default.Chat),
        DrawerMenuItem(AppScreen.INFO_LOCATION, Icons.Default.Info)
    )

    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        modifier = Modifier
            .width(310.dp)
            .fillMaxHeight()
            .testTag("app_drawer_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {
            // Header with Unipoint Branding
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(UnipointNavyDark, UnipointNavy)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(2.dp, UnipointAmber, RoundedCornerShape(12.dp)),
                            color = UnipointNavyDark
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_app_icon),
                                contentDescription = "Logo Unipoint",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "UNIPOINT",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 1.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(UnipointAmber, RoundedCornerShape(4.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "PRO",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                            Text(
                                text = "Noleggio Ponteggi & Tetti",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = UnipointAmber
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Sicurezza, Qualità e Garanzia nel tempo",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        fontWeight = FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Navigation Items
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                items.forEach { item ->
                    val isSelected = currentScreen == item.screen
                    NavigationDrawerItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.screen.title,
                                tint = if (isSelected) UnipointNavy else Slate600
                            )
                        },
                        label = {
                            Text(
                                text = item.screen.title,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        },
                        badge = {
                            if (item.badge != null) {
                                Surface(
                                    color = if (isSelected) UnipointAmber else Slate100,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = item.badge,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else Slate600,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        },
                        selected = isSelected,
                        onClick = { onSelectScreen(item.screen) },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = UnipointAmber.copy(alpha = 0.15f),
                            selectedTextColor = UnipointNavy,
                            unselectedTextColor = Slate800
                        ),
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .testTag("drawer_item_${item.screen.name.lowercase()}")
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f, fill = false))
            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = Slate200, modifier = Modifier.padding(horizontal = 16.dp))

            // Quick Call Geom. Mauro Fiorenza Box
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .background(Slate100, RoundedCornerShape(12.dp))
                    .clickable { onCallPhone() }
                    .padding(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = UnipointAmber,
                        shape = CircleShape,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Chiama",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Geom. Fiorenza Mauro",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = UnipointNavy
                        )
                        Text(
                            text = "345/1183711 • Chiama ora",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate600
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
