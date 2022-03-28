package com.example.checkinternetconnection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*

class MainActivity : ComponentActivity() {

    lateinit var connection: ConnectionInternet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connection = ConnectionInternet(application)

        setContent {
            val checkInternet = remember { mutableStateOf(false) }

            val compositionResult =
                rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
                    if (checkInternet.value){
                        R.raw.okey
                    } else{
                        R.raw.nointernet
                    }
                ))

            val isPlaying by remember { mutableStateOf(true) }

            val progress = animateLottieCompositionAsState(
                composition = compositionResult.value,
                isPlaying = isPlaying,
                iterations = LottieConstants.IterateForever
            )


            connection.observe(this){
                checkInternet.value = it
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    composition = compositionResult.value,
                    progress = progress.progress,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
