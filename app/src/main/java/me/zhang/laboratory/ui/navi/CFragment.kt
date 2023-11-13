package me.zhang.laboratory.ui.navi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import me.zhang.laboratory.R
import me.zhang.laboratory.databinding.FragmentCBinding
import me.zhang.laboratory.ui.compose.DarkLightMaterialTheme
import me.zhang.laboratory.ui.compose.PreviewDarkLight

class CFragment : Fragment() {
    private var _binding: FragmentCBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCBinding.inflate(inflater, container, false)
        val view = binding?.root
        binding?.composeView?.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Cf()
            }
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @Composable
    private fun Cf() {
        DarkLightMaterialTheme {
            Column {
                Text(text = "Current: C")
                Button(onClick = {
                    findNavController().popBackStack(R.id.AFragment, false)
                }) {
                    Text(text = "Back to A")
                }
                Button(onClick = {
                    // findNavController().navigate(R.id.action_CFragment_to_DFragment)
                    findNavController().navigate(CFragmentDirections.actionCFragmentToDFragment())
                }) {
                    Text(text = "Jump to D")
                }
            }
        }
    }

    @PreviewDarkLight
    @Composable
    fun PreviewAf() {
        Cf()
    }
}