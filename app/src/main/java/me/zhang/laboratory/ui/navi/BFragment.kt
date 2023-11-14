package me.zhang.laboratory.ui.navi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import me.zhang.laboratory.databinding.FragmentBBinding
import me.zhang.laboratory.ui.compose.DarkLightMaterialTheme
import me.zhang.laboratory.ui.compose.PreviewDarkLight
import me.zhang.laboratory.ui.navi.BFragmentDirections.Companion.actionBFragmentToCFragment

class BFragment : Fragment() {
    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBBinding.inflate(inflater, container, false)
        val view = binding?.root
        binding?.composeView?.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Bf()
            }
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @Composable
    private fun Bf() {
        DarkLightMaterialTheme {
            Column {
                Text(text = "Current: B")

                val id =
                    findNavController().currentBackStackEntry?.arguments?.getInt(NavArguments.ID)
                if (id?.compareTo(0) != 0) {
                    Text(text = "ID: $id")
                }
                val name = arguments?.getString(NavArguments.NAME)
                if (name != null) {
                    Text(text = "NAME: $name")
                }
                val age =
                    findNavController().currentBackStackEntry?.arguments?.getInt(NavArguments.AGE)
                if (age?.compareTo(0) != 0) {
                    Text(text = "AGE: $age")
                }

                Button(onClick = {
                    // findNavController().navigate(R.id.action_BFragment_to_CFragment)
                    findNavController().navigate(actionBFragmentToCFragment())
                }) {
                    Text(text = "Jump to C")
                }

                Button(onClick = {
                    findNavController().navigate(NavRoutes.C)
                }) {
                    Text(text = "Jump to C (Dsl)")
                }

                Button(onClick = {
                    // findNavController().navigate(R.id.action_global_AFragment)
                    findNavController().navigate(BFragmentDirections.actionGlobalAFragment())
                }) {
                    Text(text = "Back to A")
                }

                Button(onClick = {
                    findNavController().navigate(NavRoutes.A)
                }) {
                    Text(text = "Back to A (Dsl)")
                }
            }
        }
    }

    @PreviewDarkLight
    @Composable
    fun PreviewAf() {
        Bf()
    }
}