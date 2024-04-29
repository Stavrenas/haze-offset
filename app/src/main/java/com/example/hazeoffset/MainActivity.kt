package com.example.hazeoffset
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.hazeoffset.ui.theme.HazeOffsetTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HazeOffsetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
@Preview
fun SampleDialog(){
    DialogSample()
}

@Composable
@Preview
fun SampleDialogFullScreen(){
    val activity = (LocalContext.current as? ComponentActivity) ?: return
    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    DialogSample()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HazeOffsetTheme {
        Greeting("Android")
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSample() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Haze Dialog sample") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        @Suppress("DEPRECATION")
                        Icon(Icons.Default.ArrowBack, null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { innerPadding ->
        val hazeState = remember { HazeState() }
        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = .5f)
                        .hazeChild(
                            state = hazeState,
                            shape = MaterialTheme.shapes.extraLarge,
                        ),
                    shape = MaterialTheme.shapes.extraLarge,
                    // We can't use Haze tint with dialogs, as the tint will display a scrim over the
                    // background content. Instead we need to set a translucent background on the
                    // dialog content.
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                    contentColor = MaterialTheme.colorScheme.onSurface,
                ) {
                    // empty
                }
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.haze(hazeState),
            columns = GridCells.Fixed(4),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(40) {
                Card(
                    modifier = Modifier.height(100.dp),
                    onClick = { showDialog = true },
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(text = "Card $it")
                    }
                }
            }
        }
    }
}