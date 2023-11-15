package me.zhang.laboratory.ui.navi

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.NavType
import androidx.navigation.activity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import me.zhang.laboratory.R
import java.io.Serializable

class NavActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()

//        initNavGraph()

//        setContent { MyAppNavHost() }
    }

    private fun setContentView() {
        setContentView(R.layout.activity_navi)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        val startDestinationArgs = bundleOf("id" to 333, "name" to "xCrash", "age" to 6)
        navController.setGraph(R.navigation.nav_graph, startDestinationArgs)
    }

    private fun initNavGraph() {
        val viewModel by viewModels<NavViewModel>()
        viewModel.setDslEnabled(true)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        navController.graph =
            navController.createGraph(startDestination = NavRoutes.A) {
                fragment<AFragment>(NavRoutes.A) {
                    label = resources.getString(R.string.a)
                }
                fragment<BFragment>("${NavRoutes.B}/{${NavArguments.ID}}?${NavArguments.NAME}={${NavArguments.NAME}}&${NavArguments.AGE}={${NavArguments.AGE}}") {
                    label = resources.getString(R.string.b)
                    deepLink {
                        uriPattern =
                            "${NavRoutes.URI}/${NavRoutes.B}/{${NavArguments.ID}}?${NavArguments.NAME}={${NavArguments.NAME}}&${NavArguments.AGE}={${NavArguments.AGE}}"
                        action = "android.intent.action.B_ACTION"
                        mimeType = "b/b"
                    }
                    argument(NavArguments.ID) {
                        type = NavType.IntType
                    }
                    argument(NavArguments.NAME) {
                        type = NavType.StringType
                    }
                    argument(NavArguments.AGE) {
                        type = NavType.IntType
                    }
                }
                fragment<CFragment>(NavRoutes.C) {
                    label = resources.getString(R.string.c)
                }
                fragment<DFragment>(NavRoutes.D) {
                    label = resources.getString(R.string.d)
                }
                activity(NavRoutes.X) {
                    label = getString(R.string.x)
                    // arguments, deepLinks...

                    activityClass = XActivity::class
                }
            }
    }
}

object NavRoutes {
    const val URI = "android-lab://zhang.me"

    const val A = "a"
    const val B = "b"
    const val C = "c"
    const val D = "d"
    const val E = "e"
    const val X = "x"
}

object NavArguments {
    const val ID = "id"
    const val NAME = "name"
    const val AGE = "age"
}

@Keep
interface ParcelableArg : Parcelable {}

@Keep
class SerializableArg : Serializable {}

@Keep
enum class EnumArg {}