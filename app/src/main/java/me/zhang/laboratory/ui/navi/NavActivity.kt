package me.zhang.laboratory.ui.navi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.zhang.laboratory.R

class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navi)
//        setContent {
//            MyAppNavHost()
//        }
    }

    @Composable
    private fun MyAppNavHost(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
        startDestination: String = "destination_a"
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable("destination_a") {
                DestinationA(
                    onNavigateToB = {
                        // Navigate to the "destination_b" destination
                        navController.navigate("destination_b")
                    },
                )
            }
            composable("destination_b") {
                DestinationB(onNavigateToC = {
                    // Navigate to the "destination_c" destination
                    navController.navigate("destination_c")
                })
            }
            composable("destination_c") {
                DestinationC(onNavigateToD = {
                    // Pop everything up to the "destination_a" destination off the back stack before
                    // navigating to the "destination_d" destination
                    navController.navigate("destination_d") {
                        popUpTo("destination_a") {
                            inclusive = true
                            saveState = true
                        }
                    }
                }, onNavigateBackToA = {
                    // Navigate back to the "destination_a" destination
                    navController.popBackStack("destination_a", false)
//                    navController.popBackStack(R.id.AFragment, false) // Not work!
                })
            }
            composable("destination_d") { DestinationD(/* ... */) }
        }
    }

    @Composable
    private fun DestinationA(onNavigateToB: () -> Unit) {
        Column {
            Text("Current: A")
            Button(onClick = onNavigateToB) {
                Text("Jump to B")
            }
        }
    }

    @Composable
    private fun DestinationB(onNavigateToC: () -> Unit) {
        Column {
            Text("Current: B")
            Button(onClick = onNavigateToC) {
                Text("Jump to C")
            }
        }
    }

    @Composable
    private fun DestinationC(onNavigateToD: () -> Unit, onNavigateBackToA: () -> Unit) {
        Column {
            Text("Current: C")
            Button(onClick = onNavigateBackToA) {
                Text(text = "Back to A")
            }
            Button(onClick = onNavigateToD) {
                Text("Jump to D")
            }
        }
    }

    @Composable
    private fun DestinationD() {
        Text("D")
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMyAppNavHost() {
        MyAppNavHost()
    }
}