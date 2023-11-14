package me.zhang.laboratory.ui.navi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import me.zhang.sample.databinding.FragmentFBinding

class FFragment : Fragment() {
    private var _binding: FragmentFBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentFBinding.inflate(inflater, container, false)
        val view = binding?.root
        binding?.composeView?.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Ff()
            }
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @Composable
    private fun Ff() {
        MaterialTheme {
            Column {
                Text(text = "Current: F")
                Button(onClick = {
                    // findNavController().popBackStack(R.id.AFragment, false) // how to go back to AFragment?
                    val request = NavDeepLinkRequest.Builder
                        .fromUri("android-lab://zhang.me/a/1?name=zhang&age=31".toUri())
                        .setAction("android.intent.action.A_ACTION")
                        .setMimeType("a/a")
                        .build()
//                    val request = NavDeepLinkRequest.Builder
//                        .fromAction("android.intent.action.A_ACTION")
////                        .setMimeType("a/aa")
//                        .build()
//                    val request = NavDeepLinkRequest.Builder
//                        .fromMimeType("a/aa")
//                        .build()
                    // TODO: how to pop back stack to AFragment?
                    findNavController().navigate(
                        request,
                        navOptions(optionsBuilder = { launchSingleTop = true })
                    )
                }) {
                    Text(text = "Back to A")
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewAf() {
        Ff()
    }
}