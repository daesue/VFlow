package com.celcom.vflow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// MainActivity의 Scaffold가 상태 표시줄/시스템 버튼 영역의 여백을 전달합니다.
// 여기서 시스템 여백을 또 추가하면 하단 공간이 두 배가 될 수 있습니다.
@Composable
fun VFlowMainScreen(modifier: Modifier = Modifier) {
    // 0=홈, 1=스캐닝, 2=기록, 3=설정. 화면 회전 시에도 선택을 복원합니다.
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    // 아래 세 목록은 같은 순서입니다. 파일 확장자 .png는 R.drawable에 쓰지 않습니다.
    val icons = listOf(
        R.drawable.ic_nav_home,
        R.drawable.ic_nav_scanning,
        R.drawable.ic_nav_list,
        R.drawable.ic_nav_setting
    )
    val descriptions = listOf("홈", "스캐닝", "측정 기록", "설정")
    val messages = listOf(
        "VFlow 홈",
        "스캐닝 화면 — 장비 연결 기능 준비 중",
        "측정 기록 화면 — 저장 기능 준비 중",
        "설정 화면 — 설정 기능 준비 중"
    )

    // Column: 위에서 아래로 배치. 위쪽 내용과 아래쪽 메뉴를 쌓습니다.
    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        Box(
            // weight(1f): 하단 메뉴를 제외한 남은 높이를 사용합니다.
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(text = messages[selectedTab], color = Color(0xFF243746))
        }
        HorizontalDivider(color = Color(0xFFE7EEF5))
        NavigationBar(
            containerColor = Color.White,
            tonalElevation = 0.dp,
            windowInsets = WindowInsets(0, 0, 0, 0)
        ) {
            icons.forEachIndexed { index, icon ->
                NavigationBarItem(
                    selected = selectedTab == index,
                    // 상태가 바뀌면 Compose가 화면과 선택 표시를 다시 그립니다.
                    onClick = { selectedTab = index },
                    icon = {
                        // Image는 원본 PNG의 파랑/청록 그라데이션을 그대로 표시합니다.
                        Image(
                            painter = painterResource(icon),
                            contentDescription = descriptions[index],
                            modifier = Modifier.size(36.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color(0xFFDDF4FF)
                    )
                )
            }
        }
    }
}
