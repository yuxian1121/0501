package com.example.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.animation.ui.theme.AnimationTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    Animation()
                }
            }
        }
    }
}

@Composable
fun Animation(){
    var appear by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(true) }
    var fly by remember{mutableStateOf(false) }

    //角度動畫
    val buttonAngle by animateFloatAsState(
        if (appear) 360f else 0f,
        animationSpec = tween(durationMillis = 2500)
    )
    //顏色動畫
    val backgroundColor by animateColorAsState(
        if (appear) Color.Transparent else Color.White,
        animationSpec = tween(2000, 500)
    )
    //大小動畫
    val rocketSize by animateDpAsState(
        if (fly) 75.dp else 150.dp,
        animationSpec = tween(2000)
    )
    //位移動畫
    val rocketOffset by animateOffsetAsState(
        if (fly) Offset(200f, -50f) else Offset(200f, 400f),
        animationSpec = tween(2000)
    )





    Column (Modifier.background(backgroundColor)){
        Button(onClick = {
            appear = !appear
        },
            modifier = Modifier.rotate(buttonAngle)
        )
        {
            if (appear) Text(text = "星空背景圖消失")
            else Text(text = "星空背景圖出現")

        }
        AnimatedVisibility(visible = appear,
            enter = fadeIn(
                initialAlpha = 0.1f,
                animationSpec = tween(durationMillis = 5000))
                    +slideInHorizontally(
                animationSpec = tween(durationMillis = 5000)) { fullWidth ->
                fullWidth / 3
            },

            exit = fadeOut(
                animationSpec = tween(durationMillis = 5000))
                    + slideOutHorizontally(
                animationSpec = tween(durationMillis = 5000)) { fullWidth ->
                fullWidth / 3
            }

        ) {
            Image(
                painter = painterResource(id = R.drawable.sky),
                contentDescription = "星空背景圖",
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .height(if (expanded) 600.dp else 400.dp)
                    .clickable(
                    ) {
                        expanded = !expanded
                    }

            )
            Image(
                painter = painterResource(id = R.drawable.rocket),
                contentDescription = "火箭",
                modifier = Modifier
                    .size(rocketSize)
                    .offset(rocketOffset.x.dp, rocketOffset.y.dp)
                    .clickable(
                    ) {
                        fly = !fly
                    }
            )


        }


    }

}
