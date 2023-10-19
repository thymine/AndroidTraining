package me.zhang.laboratory.ui.compose

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.parcelize.Parcelize

class StateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        if (false) {
            var counter by rememberSaveable {
                mutableIntStateOf(0)
            }
            CounterComponent(text = counter, { counter-- }, { counter++ })
        } else if (false) {
            var weight by rememberSaveable {
                mutableFloatStateOf(0f)
            }
            CounterComponent(text = weight, { weight -= 0.1f }, { weight += 0.1f })
        } else if (false) {
            var city by rememberSaveable(stateSaver = CitySaver) {
                mutableStateOf(City(1_000))
            }
            CounterComponent(text = city,
                { city = City(city.scale / 10) },
                { city = City(city.scale * 10) })
        } else if (false) {
            var city by rememberSaveable {
                mutableStateOf(City(1_000))
            }
            CounterComponent(text = city,
                { city = City(city.scale / 10) },
                { city = City(city.scale * 10) })
        } else {
            val cvm: CounterViewModel = viewModel()
            CounterComponent(text = cvm.counter.intValue,
                { cvm.decrement() },
                { cvm.increment() })
        }
    }

    @Composable
    fun CounterComponent(
        text: Any,
        onDecrement: () -> Unit,
        onIncrement: () -> Unit,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Click the buttons:")
                Text(text = "$text", fontSize = 32.sp)
                Row {
                    Button(onClick = { onDecrement() }) {
                        Text(text = "-")
                    }
                    VerticalDivider()
                    Button(onClick = { onIncrement() }) {
                        Text(text = "+")
                    }
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}

@Parcelize
data class City(var scale: Long) : Parcelable {
    override fun toString(): String {
        return scale.toString()
    }
}

object CitySaver : Saver<City, Bundle> {
    override fun restore(value: Bundle): City {
        return City(value.getLong("scale"))
    }

    override fun SaverScope.save(value: City): Bundle {
        return Bundle().apply {
            putLong("scale", value.scale)
        }
    }
}

class CounterViewModel : ViewModel() {
    private val _counter = mutableIntStateOf(0)
    val counter: IntState
        get() = _counter

    fun increment() {
        _counter.intValue++
    }

    fun decrement() {
        _counter.intValue--
    }
}