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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import me.zhang.laboratory.R
import me.zhang.laboratory.databinding.FragmentABinding
import me.zhang.laboratory.ui.compose.DarkLightMaterialTheme
import me.zhang.laboratory.ui.compose.PreviewDarkLight

class AFragment : Fragment() {

    private var _binding: FragmentABinding? = null
    private val binding get() = _binding

    private val navViewModel: NavViewModel by activityViewModels()

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

                val id = arguments?.getInt(NavArguments.ID) ?: 0
                if (id != 0) {
                    Text(text = "ID: $id")
                }
                val name = arguments?.getString(NavArguments.NAME)
                if (name != null) {
                    Text(text = "NAME: $name")
                }
                val age = arguments?.getInt(NavArguments.AGE) ?: 0
                if (age != 0) {
                    Text(text = "AGE: $age")
                }

                var text by rememberSaveable {
                    mutableStateOf("")
                }
                OutlinedTextField(label = {
                    Text(text = "测试数据状态")
                }, value = text, onValueChange = { text = it })

                Button(onClick = {
                    val args = bundleOf("id" to 221, "name" to "shi", "age" to 38)
                    findNavController().navigate(
                        R.id.action_AFragment_to_BFragment,
                        args,
                        navOptions {
                            anim {
                                enter = R.anim.nav_enter
                                exit = R.anim.nav_exit
                                popEnter = R.anim.nav_pop_enter
                                popExit = R.anim.nav_pop_exit
                            }
                        })
                }) {
                    Text(text = "Jump to B (id)")
                }

                Button(onClick = {
                    // findNavController().navigate(R.id.action_AFragment_to_BFragment)
                    findNavController().navigate(
                        AFragmentDirections.actionAFragmentToBFragment(
                            119,
                            "Liu",
                            27
                        )
                    )
                }) {
                    Text(text = "Jump to B (Directions)")
                }

                Button(onClick = {
                    findNavController().navigate(getString(R.string.route_fragment_b))
                }) {
                    Text(text = "Jump to B (route)")
                }

                if (navViewModel.dslEnabled.value == true) {
                    Button(onClick = {
                        findNavController().navigate("b/123?name=zhang&age=31")
                    }) {
                        Text(text = "Jump to B (Dsl)")
                    }
                }

                Button(onClick = {
                    findNavController().navigate(NavRoutes.X)
                }) {
                    Text(text = "Jump to X (Activity)")
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