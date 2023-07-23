@file:OptIn(ExperimentalMaterial3Api::class)

package rakib.hasan.androidbasicwithcompose_intents_intentfilters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import rakib.hasan.androidbasicwithcompose_intents_intentfilters.ui.theme.AndroidBasicWithCompose_Intents_IntentFiltersTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBasicWithCompose_Intents_IntentFiltersTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppBar(context = this) }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ExplicitBox(this@MainActivity)
                        Spacer(modifier = Modifier.height(16.dp))
                        ImplicitBox(this@MainActivity)
                    }
                }
            }
        }
    }
}


@Composable
fun AppBar(context: Context) = TopAppBar(
    modifier = Modifier.shadow(elevation = 2.dp),
    title = {
        Text(text = "Intents")
    },
    navigationIcon = {
        IconButton(onClick = {
            Toast.makeText(context, "Navigation Icon Click", Toast.LENGTH_SHORT)
                .show()
        }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Navigation icon")
        }
    }
)

@Composable
fun ExplicitBox(context: Context) {
    Column(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                Intent(context, SecondActivity::class.java).also {
                    context.startActivity(it)
                }
            }
        ) {
            Text(text = "Explicit Intent [Own App]")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                Intent(Intent.ACTION_MAIN).also {
                    it.`package` = "com.google.android.youtube"
                    //this check is for - whether the desired app is install on this device or not.
                    try {
                        context.startActivity(it)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        ) {
            Text(text = "Explicit Intent [Another App]")
        }
    }
}

@Composable
fun ImplicitBox(context: Context) {
    Column(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, "test@test.com")
                    putExtra(Intent.EXTRA_SUBJECT, "This is the email subject")
                    putExtra(Intent.EXTRA_TEXT, "This is the content of the email.")
                }
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }
            }
        ) {
            Text(text = "Implicit Intent [Choose an App]")
        }
    }
}



















