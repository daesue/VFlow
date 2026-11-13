package com.celcom.vflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.celcom.vflow.ui.theme.VFlowTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VFlowTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    VFlowApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun VFlowApp(modifier: Modifier = Modifier) {
    // true: 로고 화면 / false: 메인 화면
    var showLogo by rememberSaveable { mutableStateOf(true) }

    // GIF 파일이 정상적으로 로딩되었는지 확인
    var logoLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(showLogo, logoLoaded) {
        if (showLogo && logoLoaded) {
            // 현재 logo.gif의 한 회 재생 길이: 약 2.24초
            delay(2240L)
            showLogo = false
        }
    }

    if (showLogo) {
        LogoScreen(
            modifier = modifier,
            onLoaded = { logoLoaded = true },
            onError = { showLogo = false }
        )
    } else {
        MainScreen(modifier = modifier)
    }
}

@Composable
fun LogoScreen(
    modifier: Modifier = Modifier,
    onLoaded: () -> Unit,
    onError: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = R.raw.logo,
                contentDescription = "VFlow 시작 로고",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f),
                contentScale = ContentScale.Fit,
                onSuccess = { onLoaded() },
                onError = { onError() }
            )
        }
    }
}

// 로고 재생이 끝나면 VFlowApp에서 이 함수를 호출합니다.
// 기존 함수 이름을 유지하고, 실제 화면은 별도 파일에 맡깁니다.
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    VFlowMainScreen(modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    VFlowTheme {
        MainScreen()
    }
}