package me.zhang.laboratory.ui.navi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import me.zhang.laboratory.databinding.FragmentABinding
import me.zhang.laboratory.ui.compose.DarkLightMaterialTheme
import me.zhang.laboratory.ui.compose.PreviewDarkLight

class AFragment : Fragment() {

    private var _binding: FragmentABinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentABinding.inflate(inflater, container, false)
        val view = binding?.root
        binding?.composeView?.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Af()
            }
        }
        return view
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    @Composable
    private fun Af() {
        DarkLightMaterialTheme {
            Column {
                Text(text = "Current: A")
                var text by rememberSaveable {
                    mutableStateOf("")
                }
                OutlinedTextField(label = {
                    Text(text = "测试数据状态")
                }, value = text, onValueChange = { text = it })
                Button(onClick = {
                    // findNavController().navigate(R.id.action_AFragment_to_BFragment)
                    findNavController().navigate(AFragmentDirections.actionAFragmentToBFragment())
                }) {
                    Text(text = "Jump to B")
                }
            }
        }
    }

    @PreviewDarkLight
    @Composable
    fun PreviewAf() {
        Af()
    }

}