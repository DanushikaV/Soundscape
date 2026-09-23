package com.example.soundscape

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundscape.ui.theme.SoundscapeTheme

data class Song(
    val title: String,
    val artist: String,
    val album: String,
    var favorite: Boolean = false
)

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

@Composable
fun SoundscapeApp() {

    var currentScreen by remember { mutableStateOf("Home") }

    var songs by remember {
        mutableStateOf(
            listOf(
                Song("Afterglow", "Taylor Swift", "Midnight Dreams", true),
                Song("Golden Hour", "JVKE", "This Is What ____ Feels Like"),
                Song("One Thing", "One Direction", "Up All Night"),
                Song("Super Shy", "NewJeans", "Get Up", true),
                Song("As It Was", "Harry Styles", "Harry's House"),
                Song("Perfect Night", "LE SSERAFIM", "Perfect Night")
            )
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = currentScreen == "Home",
                    onClick = { currentScreen = "Home" },
                    icon = {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = currentScreen == "Library",
                    onClick = { currentScreen = "Library" },
                    icon = {
                        Icon(
                            Icons.Default.LibraryMusic,
                            contentDescription = "Library"
                        )
                    },
                    label = { Text("Library") }
                )

                NavigationBarItem(
                    selected = currentScreen == "Favorites",
                    onClick = { currentScreen = "Favorites" },
                    icon = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorites"
                        )
                    },
                    label = { Text("Favorites") }
                )

                NavigationBarItem(
                    selected = currentScreen == "Add",
                    onClick = { currentScreen = "Add" },
                    icon = {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                    },
                    label = { Text("Add Song") }
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
                            song.copy(favorite = !song.favorite)
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
                            song.copy(favorite = !song.favorite)
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
                            song.copy(favorite = !song.favorite)
                        } else {
                            song
                        }
                    }
                },
                modifier = Modifier.padding(innerPadding)
            )

            "Add" -> AddSongScreen(
                onAddSong = { newSong ->
                    songs = songs + newSong
                    currentScreen = "Library"
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HomeScreen(
    songs: List<Song>,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Soundscape",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Your personal music library",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search songs...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Recently Added",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        songs.take(3).forEachIndexed { index, song ->

            SongRow(
                song = song,
                onFavoriteClick = {
                    onFavoriteClick(index)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun LibraryScreen(
    songs: List<Song>,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "My Library",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search your library...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${songs.size} Songs",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(songs.indices.toList()) { index ->

                SongRow(
                    song = songs[index],
                    onFavoriteClick = {
                        onFavoriteClick(index)
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun FavoritesScreen(
    songs: List<Song>,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val favorites = songs.filter { it.favorite }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Favorites",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (favorites.isEmpty()) {

            Text(
                text = "You haven't added any favorites yet."
            )

        } else {

            LazyColumn {

                items(favorites) { song ->

                    val originalIndex = songs.indexOf(song)

                    SongRow(
                        song = song,
                        onFavoriteClick = {
                            onFavoriteClick(originalIndex)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun AddSongScreen(
    onAddSong: (Song) -> Unit,
    modifier: Modifier = Modifier
) {

    var title by remember { mutableStateOf("") }
    var artist by remember { mutableStateOf("") }
    var album by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Add a Song",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Song Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = artist,
            onValueChange = { artist = it },
            label = { Text("Artist") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = album,
            onValueChange = { album = it },
            label = { Text("Album") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && artist.isNotBlank()) {

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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Song")
        }
    }
}

@Composable
fun SongRow(
    song: Song,
    onFavoriteClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "ART",
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = song.title,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = song.artist
            )

            Text(
                text = song.album,
                fontSize = 12.sp
            )
        }

        IconButton(
            onClick = onFavoriteClick
        ) {

            if (song.favorite) {

                Icon(
                    Icons.Default.Favorite,
                    contentDescription = "Remove favorite"
                )

            } else {

                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = "Add favorite"
                )
            }
        }
    }
}