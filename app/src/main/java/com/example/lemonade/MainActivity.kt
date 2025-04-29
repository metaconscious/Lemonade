package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

enum class LemonadeAppState {
    Select,
    Squeeze,
    Drink,
    Restart
}

data class StateResource(
    @DrawableRes val imageRes: Int,
    val textRes: Int,
    val descriptionRes: Int
)

val stateResources = mapOf(
    LemonadeAppState.Select to StateResource(
        R.drawable.lemon_tree,
        R.string.lemonade_select_lemon,
        R.string.lemon_tree_content_description
    ),
    LemonadeAppState.Squeeze to StateResource(
        R.drawable.lemon_squeeze,
        R.string.lemonade_squeeze_lemon,
        R.string.lemon_content_description
    ),
    LemonadeAppState.Drink to StateResource(
        R.drawable.lemon_drink,
        R.string.lemonade_drink_lemonade,
        R.string.glass_of_lemonade_content_description
    ),
    LemonadeAppState.Restart to StateResource(
        R.drawable.lemon_restart,
        R.string.lemonade_restart,
        R.string.empty_glass_content_description
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@Composable
fun LemonadeMaker(modifier: Modifier = Modifier) {
    var state by remember { mutableStateOf(LemonadeAppState.Select) }
    var squeezeTimes by remember { mutableIntStateOf((1..3).random()) }
    val resource = stateResources[state]!!
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                when (state) {
                    LemonadeAppState.Select -> {
                        squeezeTimes = (1..3).random()
                        state = LemonadeAppState.Squeeze
                    }

                    LemonadeAppState.Squeeze -> {
                        --squeezeTimes
                        if (squeezeTimes < 1) {
                            state = LemonadeAppState.Drink
                        } else {
                            state = LemonadeAppState.Squeeze
                        }
                    }

                    LemonadeAppState.Drink -> {
                        state = LemonadeAppState.Restart
                    }

                    LemonadeAppState.Restart -> {
                        state = LemonadeAppState.Select
                    }
                }
            }
        ) {
            Image(
                painter = painterResource(resource.imageRes),
                contentDescription = stringResource(resource.descriptionRes),
                modifier = Modifier.background(color = Color.Unspecified)
            )
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Text(text = stringResource(resource.textRes))
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeMaker(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}