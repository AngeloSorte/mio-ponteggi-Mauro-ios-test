package com.example.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.platform.DefaultPlatformActions
import com.example.platform.PlatformActions
import com.example.ui.components.AppDrawerContent
import com.example.ui.screens.BookingsScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ConfiguratorScreen
import com.example.ui.screens.DroneScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.InfoLocationScreen
import com.example.ui.screens.RoofingScreen
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate600
import com.example.ui.theme.UnipointAmber
import com.example.ui.theme.UnipointNavy
import com.example.ui.theme.UnipointNavyDark
import kotlinx.coroutines.launch

/**
 * Pure Compose Multiplatform root layout for Unipoint Ponteggi.
 * Can run unchanged across Android, iOS (iPhone/iPad), Desktop, and Web.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnipointAppContent(
    viewModel: UnipointViewModel,
    platformActions: PlatformActions = DefaultPlatformActions()
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }

    val currentScreen by viewModel.currentScreen.collectAsState()
    val bookingSuccessMsg by viewModel.bookingSuccessMessage.collectAsState()

    var prefilledItemTitle by remember { mutableStateOf<String?>(null) }
    var prefilledDailyRate by remember { mutableStateOf<Double?>(null) }

    val callPhone = { platformActions.dialPhone("3451183711") }
    val openMap = { platformActions.openMap(45.6631, 9.2084, "Unipoint Ponteggi Sede") }

    LaunchedEffect(bookingSuccessMsg) {
        bookingSuccessMsg?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearBookingSuccess()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                currentScreen = currentScreen,
                onSelectScreen = { screen ->
                    viewModel.navigateTo(screen)
                    coroutineScope.launch { drawerState.close() }
                },
                onCallPhone = {
                    callPhone()
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "UNIPOINT",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 0.8.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = UnipointAmber,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "PRO",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = currentScreen.title,
                                fontSize = 11.sp,
                                color = UnipointAmber.copy(alpha = 0.95f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { coroutineScope.launch { drawerState.open() } },
                            modifier = Modifier.testTag("menu_drawer_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Apri menu di navigazione",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.CHAT) },
                            modifier = Modifier.testTag("top_action_chat")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = "Chat diretta",
                                tint = if (currentScreen == AppScreen.CHAT) UnipointAmber else Color.White
                            )
                        }
                        IconButton(
                            onClick = { callPhone() },
                            modifier = Modifier.testTag("top_action_phone")
                        ) {
                            Surface(
                                color = UnipointAmber,
                                shape = CircleShape,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = "Chiama Geometra",
                                        tint = Color.Black,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = UnipointNavyDark,
                        titleContentColor = Color.White
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    val bottomItems = listOf(
                        Triple(AppScreen.HOME, Icons.Default.Home, "Home"),
                        Triple(AppScreen.CATALOG, Icons.Default.Build, "Catalogo"),
                        Triple(AppScreen.CUSTOMIZER, Icons.Default.ViewInAr, "3D Live"),
                        Triple(AppScreen.BOOKINGS, Icons.Default.DateRange, "Prenotazioni"),
                        Triple(AppScreen.CHAT, Icons.Default.Chat, "Assistenza")
                    )

                    bottomItems.forEach { (screen, icon, label) ->
                        val isSelected = currentScreen == screen
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(screen) },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = UnipointNavy,
                                selectedTextColor = UnipointNavy,
                                indicatorColor = UnipointAmber.copy(alpha = 0.25f),
                                unselectedIconColor = Slate600,
                                unselectedTextColor = Slate600
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Slate100)
            ) {
                Crossfade(targetState = currentScreen, label = "screen_transition") { screen ->
                    when (screen) {
                        AppScreen.HOME -> HomeScreen(
                            onNavigate = { target -> viewModel.navigateTo(target) },
                            onCallPhone = { callPhone() }
                        )
                        AppScreen.CATALOG -> CatalogScreen(
                            viewModel = viewModel,
                            onBookItem = { item ->
                                prefilledItemTitle = item.title
                                prefilledDailyRate = item.dailyRate
                                viewModel.navigateTo(AppScreen.BOOKINGS)
                            }
                        )
                        AppScreen.CUSTOMIZER -> ConfiguratorScreen(
                            viewModel = viewModel,
                            onProceedToBooking = { title, rate ->
                                prefilledItemTitle = title
                                prefilledDailyRate = rate
                                viewModel.navigateTo(AppScreen.BOOKINGS)
                            }
                        )
                        AppScreen.BOOKINGS -> BookingsScreen(
                            viewModel = viewModel,
                            prefilledItemTitle = prefilledItemTitle,
                            prefilledRate = prefilledDailyRate
                        )
                        AppScreen.ROOFING -> RoofingScreen(
                            viewModel = viewModel,
                            onNavigate = { target -> viewModel.navigateTo(target) },
                            onCallPhone = { callPhone() }
                        )
                        AppScreen.DRONE -> DroneScreen(
                            viewModel = viewModel,
                            onCallPhone = { callPhone() }
                        )
                        AppScreen.CHAT -> ChatScreen(
                            viewModel = viewModel,
                            onCallPhone = { callPhone() }
                        )
                        AppScreen.INFO_LOCATION -> InfoLocationScreen(
                            onCallPhone = { callPhone() },
                            onOpenMap = { openMap() }
                        )
                    }
                }
            }
        }
    }
}
