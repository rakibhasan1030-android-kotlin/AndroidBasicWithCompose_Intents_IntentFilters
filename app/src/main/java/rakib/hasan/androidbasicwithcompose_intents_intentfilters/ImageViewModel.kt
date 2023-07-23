package rakib.hasan.androidbasicwithcompose_intents_intentfilters

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.net.URI

class ImageViewModel: ViewModel() {
    var uri: Uri? by mutableStateOf(null)
    fun updateUri(uri: Uri?) {
        this.uri = uri
    }
}