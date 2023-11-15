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
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import me.zhang.laboratory.databinding.FragmentBBinding
import me.zhang.laboratory.ui.compose.DarkLightMaterialTheme
import me.zhang.laboratory.ui.compose.PreviewDarkLight

class BFragment : Fragment() {
    private var _binding: FragmentBBinding? = null
    private val binding get() = _binding
    private val navViewModel: NavViewModel by activityViewModels()
    private val args: BFragmentArgs by navArgs()

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

                //region Extract arguments
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
                //endregion

                //region Extract arguments
                if (args.id != 0) {
                    Text(text = "ID: ${args.id}")
                }
                if (args.name != null) {
                    Text(text = "NAME: ${args.name}")
                }
                if (args.age != 0) {
                    Text(text = "AGE: ${args.age}")
                }
                //endregion

                Button(onClick = {
                    // findNavController().navigate(R.id.action_BFragment_to_CFragment)
                    findNavController().navigate(BFragmentDirections.actionBFragmentToCFragment())
                }) {
                    Text(text = "Jump to C")
                }

                if (navViewModel.dslEnabled.value == true) {
                    Button(onClick = {
                        findNavController().navigate(NavRoutes.C)
                    }) {
                        Text(text = "Jump to C (Dsl)")
                    }
                }

                Button(onClick = {
                    // findNavController().navigate(R.id.action_global_AFragment)
                    findNavController().navigate(
                        BFragmentDirections.actionGlobalAFragment(
                            120,
                            "Li",
                            32
                        )
                    )
                }) {
                    Text(text = "Back to A (Directions))")
                }

                if (navViewModel.dslEnabled.value == true) {
                    Button(onClick = {
                        findNavController().navigate(NavRoutes.A)
                    }) {
                        Text(text = "Back to A (Dsl)")
                    }
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