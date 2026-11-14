package id.ac.itera.pesanantar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.ac.itera.pesanantar.db.DriverFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(driverFactory = DriverFactory(applicationContext))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}