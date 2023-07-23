@file:OptIn(ExperimentalMaterial3Api::class)

package rakib.hasan.androidbasicwithcompose_intents_intentfilters

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import rakib.hasan.androidbasicwithcompose_intents_intentfilters.ui.theme.AndroidBasicWithCompose_Intents_IntentFiltersTheme

class MainActivity : ComponentActivity() {

     private val viewModel by viewModels<ImageViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
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
                        ImageContainer(viewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                        ExplicitBox(this@MainActivity)
                        Spacer(modifier = Modifier.height(16.dp))
                        ImplicitBox(this@MainActivity)
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }
        Log.e("URI", "URI = $uri")
        viewModel.updateUri(uri)
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
fun ImageContainer(viewModel: ImageViewModel) {
    viewModel.uri?.let {
        AsyncImage(
            model = viewModel.uri,
            contentDescription = null
        )
    }
}

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



















