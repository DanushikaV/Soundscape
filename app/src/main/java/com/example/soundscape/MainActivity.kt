package com.example.soundscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundscape.ui.theme.SoundscapeTheme


// ==========================
// Colors
// ==========================

val BackgroundColor = Color(0xFFF9F7FF)
val PurpleColor = Color(0xFF8B7FA8)
val LightPurple = Color(0xFFE9E1F5)
val PinkColor = Color(0xFFE88BA5)
val DarkText = Color(0xFF302B3D)
val GrayText = Color(0xFF81798D)


// ==========================
// Song Data
// ==========================

data class Song(
    val title: String,
    val artist: String,
    val album: String,
    val favorite: Boolean = false
)


// ==========================
// Main Activity
// ==========================

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SoundscapeTheme {
                SoundscapeApp()
            }
        }
    }
}


// ==========================
// Main App
// ==========================

@Composable
fun SoundscapeApp() {

    var currentScreen by remember {
        mutableStateOf("Home")
    }

    var songs by remember {
        mutableStateOf(
            listOf(
                Song(
                    title = "Afterglow",
                    artist = "Taylor Swift",
                    album = "Midnight Dreams",
                    favorite = true
                ),
                Song(
                    title = "Golden Hour",
                    artist = "JVKE",
                    album = "This Is What ____ Feels Like"
                ),
                Song(
                    title = "One Thing",
                    artist = "One Direction",
                    album = "Up All Night"
                ),
                Song(
                    title = "Super Shy",
                    artist = "NewJeans",
                    album = "Get Up",
                    favorite = true
                ),
                Song(
                    title = "As It Was",
                    artist = "Harry Styles",
                    album = "Harry's House"
                ),
                Song(
                    title = "Perfect Night",
                    artist = "LE SSERAFIM",
                    album = "Perfect Night"
                )
            )
        )
    }

    Scaffold(
        containerColor = BackgroundColor,

        bottomBar = {

            NavigationBar(
                containerColor = Color.White
            ) {

                NavigationBarItem(
                    selected = currentScreen == "Home",
                    onClick = {
                        currentScreen = "Home"
                    },
                    icon = {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = {
                        Text("Home")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == "Library",
                    onClick = {
                        currentScreen = "Library"
                    },
                    icon = {
                        Icon(
                            Icons.Default.LibraryMusic,
                            contentDescription = "Library"
                        )
                    },
                    label = {
                        Text("Library")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == "Favorites",
                    onClick = {
                        currentScreen = "Favorites"
                    },
                    icon = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorites"
                        )
                    },
                    label = {
                        Text("Favorites")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == "Add",
                    onClick = {
                        currentScreen = "Add"
                    },
                    icon = {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Song"
                        )
                    },
                    label = {
                        Text("Add Song")
                    }
                )

                NavigationBarItem(
                    selected = currentScreen == "Profile" || currentScreen == "Settings" || currentScreen == "Subscription",
                    onClick = {
                        currentScreen = "Profile"
                    },
                    icon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    },
                    label = {
                        Text("Profile")
                    }
                )
            }
        }

    ) { innerPadding ->

        when (currentScreen) {

            "Home" -> HomeScreen(
                songs = songs,
                onFavoriteClick = { index ->

                    songs = songs.mapIndexed { i, song ->

                        if (i == index) {
                            song.copy(
                                favorite = !song.favorite
                            )
                        } else {
                            song
                        }
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            "Library" -> LibraryScreen(
                songs = songs,
                onFavoriteClick = { index ->

                    songs = songs.mapIndexed { i, song ->

                        if (i == index) {
                            song.copy(
                                favorite = !song.favorite
                            )
                        } else {
                            song
                        }
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            "Favorites" -> FavoritesScreen(
                songs = songs,
                onFavoriteClick = { index ->

                    songs = songs.mapIndexed { i, song ->

                        if (i == index) {
                            song.copy(
                                favorite = !song.favorite
                            )
                        } else {
                            song
                        }
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            "Add" -> AddScreen(
                onAddSong = { newSong ->

                    songs = songs + newSong

                    currentScreen = "Library"
                },
                modifier = Modifier.padding(innerPadding)
            )

            "Profile" -> ProfileScreen(
                onNavigateToSettings = { currentScreen = "Settings" },
                modifier = Modifier.padding(innerPadding)
            )

            "Settings" -> SettingsScreen(
                onNavigateBack = { currentScreen = "Profile" },
                onNavigateToSubscription = { currentScreen = "Subscription" },
                modifier = Modifier.padding(innerPadding)
            )

            "Subscription" -> SubscriptionScreen(
                onNavigateBack = { currentScreen = "Settings" },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


// ==========================
// Home Screen
// ==========================

@Composable
fun HomeScreen(
    songs: List<Song>,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Good afternoon ♡",
                fontSize = 16.sp,
                color = PurpleColor
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Soundscape",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Your little music collection",
                fontSize = 15.sp,
                color = GrayText
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text("Search your music...")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "Recently Added",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        itemsIndexed(
            songs.take(3)
        ) { index, song ->

            SongItem(
                song = song,
                onFavoriteClick = {
                    onFavoriteClick(index)
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }
    }
}


// ==========================
// Library Screen
// ==========================

@Composable
fun LibraryScreen(
    songs: List<Song>,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "My Library",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "All your music in one place ♡",
                fontSize = 15.sp,
                color = GrayText
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text("Search your library...")
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = "${songs.size} Songs",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }

        itemsIndexed(songs) { index, song ->

            SongItem(
                song = song,
                onFavoriteClick = {
                    onFavoriteClick(index)
                }
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }
    }
}


// ==========================
// Favorites Screen
// ==========================

@Composable
fun FavoritesScreen(
    songs: List<Song>,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val favoriteSongs =
        songs.withIndex().filter {
            it.value.favorite
        }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        item {

            Text(
                text = "Favorites ♡",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Text(
                text = "Songs you've fallen in love with",
                fontSize = 15.sp,
                color = GrayText
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }

        if (favoriteSongs.isEmpty()) {

            item {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No favorites yet ♡",
                        fontSize = 16.sp,
                        color = PurpleColor
                    )
                }
            }

        } else {

            itemsIndexed(favoriteSongs) { _, indexedSong ->

                val originalIndex = indexedSong.index
                val song = indexedSong.value

                SongItem(
                    song = song,
                    onFavoriteClick = {
                        onFavoriteClick(originalIndex)
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )
            }
        }
    }
}


// ==========================
// Add Song Screen
// ==========================

@Composable
fun AddScreen(
    onAddSong: (Song) -> Unit,
    modifier: Modifier = Modifier
) {

    var title by remember {
        mutableStateOf("")
    }

    var artist by remember {
        mutableStateOf("")
    }

    var album by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Add a Song",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        Text(
            text = "Add something new to your collection ♫",
            fontSize = 15.sp,
            color = GrayText
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Song Title")
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = artist,
            onValueChange = {
                artist = it
            },
            label = {
                Text("Artist")
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = album,
            onValueChange = {
                album = it
            },
            label = {
                Text("Album")
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Button(
            onClick = {

                if (
                    title.isNotBlank() &&
                    artist.isNotBlank()
                ) {

                    onAddSong(
                        Song(
                            title = title,
                            artist = artist,
                            album = album
                        )
                    )

                    title = ""
                    artist = ""
                    album = ""
                }
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PurpleColor
            )
        ) {

            Text(
                text = "Add Song",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// ==========================
// Song Card
// ==========================

@Composable
fun SongItem(
    song: Song,
    onFavoriteClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(Color.White)
            .padding(12.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        // Album artwork placeholder
        Box(
            modifier = Modifier
                .size(65.dp)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(LightPurple),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "♫",
                fontSize = 28.sp,
                color = PurpleColor
            )
        }

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = song.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Text(
                text = song.artist,
                fontSize = 14.sp,
                color = Color(0xFF6F6878)
            )

            Text(
                text = song.album,
                fontSize = 12.sp,
                color = Color(0xFF9A929F)
            )
        }

        IconButton(
            onClick = onFavoriteClick
        ) {

            if (song.favorite) {

                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove favorite",
                    tint = PinkColor
                )

            } else {

                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add favorite",
                    tint = Color(0xFF9A929F)
                )
            }
        }
    }
}

// ==========================
// Profile Screen
// ==========================

@Composable
fun ProfileScreen(
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("CSC 438") }
    var username by remember { mutableStateOf("@CSC438") }
    var email by remember { mutableStateOf("csc438@cuny.edu") }
    var isEditing by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Profile",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )
                IconButton(onClick = onNavigateToSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = DarkText)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(LightPurple),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.size(50.dp),
                        tint = PurpleColor
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isEditing) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { isEditing = false },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PurpleColor)
                    ) {
                        Text("Save Profile", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text(text = name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkText)
                    Text(text = username, fontSize = 16.sp, color = GrayText)
                    Text(text = email, fontSize = 14.sp, color = GrayText)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = { isEditing = true },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Edit Profile", color = PurpleColor, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "My Activity",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item {
            ProfileListItem(icon = Icons.Default.Favorite, title = "Favorite Songs & Artists")
            ProfileListItem(icon = Icons.Default.History, title = "Listening History")
            ProfileListItem(icon = Icons.Default.PlaylistPlay, title = "My Playlists")
        }
    }
}

@Composable
fun ProfileListItem(icon: ImageVector, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable { /* Mock */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = PurpleColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = DarkText)
    }
}

// ==========================
// Settings Screen
// ==========================

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSubscription: () -> Unit,
    modifier: Modifier = Modifier
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var highQualityAudio by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkText)
                }
                Text(
                    text = "Settings",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        item {
            Text("Preferences", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PurpleColor, modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsSwitchItem(icon = Icons.Default.Notifications, title = "Notifications", checked = notificationsEnabled, onCheckedChange = { notificationsEnabled = it })
            SettingsSwitchItem(icon = Icons.Default.PlayArrow, title = "High Quality Audio", checked = highQualityAudio, onCheckedChange = { highQualityAudio = it })
            SettingsSwitchItem(icon = Icons.Default.ColorLens, title = "Dark Mode", checked = darkMode, onCheckedChange = { darkMode = it })
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Account", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PurpleColor, modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsButtonItem(icon = Icons.Default.Star, title = "Subscription Plan", onClick = onNavigateToSubscription)
            SettingsButtonItem(icon = Icons.Default.Security, title = "Privacy Settings", onClick = { /* Mock */ })
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Support & About", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PurpleColor, modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsButtonItem(icon = Icons.Default.Info, title = "Help & Support", onClick = { /* Mock */ })
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { /* Mock logout */ },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LightPurple, contentColor = PurpleColor)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Log Out")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedButton(
                onClick = { /* Mock delete */ },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Account")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete Account", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SettingsSwitchItem(icon: ImageVector, title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = PurpleColor)
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontSize = 16.sp, color = DarkText)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = PurpleColor, checkedTrackColor = LightPurple)
        )
    }
}

@Composable
fun SettingsButtonItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = PurpleColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, fontSize = 16.sp, color = DarkText)
    }
}

// ==========================
// Subscription Screen
// ==========================

@Composable
fun SubscriptionScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPremium by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkText)
                }
                Text(
                    text = "Subscription",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkText,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isPremium) PurpleColor else Color.White)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isPremium) "Premium Plan" else "Free Plan",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPremium) Color.White else DarkText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (isPremium) "You have access to all features!" else "Upgrade to Premium for the best experience.",
                        fontSize = 14.sp,
                        color = if (isPremium) LightPurple else GrayText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text("Premium Features", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkText)
            Spacer(modifier = Modifier.height(12.dp))
            
            FeatureItem("Ad-free listening")
            FeatureItem("High quality audio")
            FeatureItem("Offline downloads")
            FeatureItem("Unlimited skips")
            
            Spacer(modifier = Modifier.height(32.dp))
            
            if (!isPremium) {
                Button(
                    onClick = { isPremium = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PurpleColor)
                ) {
                    Text("Upgrade to Premium", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                OutlinedButton(
                    onClick = { isPremium = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PinkColor)
                ) {
                    Text("Cancel Subscription", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PinkColor)
                }
            }
        }
    }
}

@Composable
fun FeatureItem(feature: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(Icons.Default.Star, contentDescription = null, tint = PinkColor, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = feature, fontSize = 16.sp, color = DarkText)
    }
}
