# VFlow 하단 메뉴 적용 및 기초 설명

## 1. 이번 문제와 수정 내용

아이콘 PNG 4개와 VFlowMainScreen.kt는 이미 프로젝트에 있었습니다.
하지만 MainActivity.kt의 MainScreen 함수는 여전히 'VFlow 준비 완료'라는 Text만 표시했습니다.
즉, 메뉴 화면을 만들어 두었지만 호출하지 않은 상태였습니다.

이제 MainScreen에서 VFlowMainScreen을 호출합니다.

```kotlin
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    VFlowMainScreen(modifier = modifier)
}
```

MainActivity의 바깥 Scaffold에도 흰색 배경을 지정했습니다.
VFlowMainScreen.kt에는 이해를 돕는 한글 주석을 추가했습니다.
기존 GIF, 앱 아이콘, 테마 XML, Gradle 버전 및 라이브러리는 변경하지 않았습니다.
기존 아이콘 4개는 256×256 RGBA PNG이며 투명 영역이 확인되어 그대로 사용했습니다.

## 2. 가장 간단한 적용: 파일 2개 교체

1. 현재 VFlow 프로젝트 폴더를 복사해서 백업하세요.
2. 받은 ZIP을 별도 폴더에 압축 해제하세요.
3. 압축 안 app/src/main/java/com/celcom/vflow 폴더에서 MainActivity.kt와 VFlowMainScreen.kt를 복사하세요.
4. 기존 C:\__Projects\AndroidStudioProjects\VFlow\app\src\main\java\com\celcom\vflow 폴더에 붙여 넣고 같은 이름의 파일을 교체하세요.
5. Android Studio로 돌아가 파일 변경이 반영되었는지 확인하세요.
6. 상단에서 기존 휴대폰 또는 에뮬레이터를 선택하고 ▶ 실행하세요.

최신 업로드 ZIP에 PNG가 이미 있어서 이미지 파일을 다시 복사할 필요는 없습니다.
의존성을 변경하지 않아 Gradle 설정을 수정할 필요도 없습니다.

## 3. 별도 프로젝트로 열고 싶다면

수정본은 소스 프로젝트 전체입니다. ZIP을 새 폴더에 풀고 Android Studio의 열기로
settings.gradle.kts가 들어 있는 폴더를 선택하세요.
자동 생성되는 build/.gradle/.idea 폴더와 PC별 local.properties는 제외했습니다.
SDK 위치를 묻는 경우 현재 Android Studio에서 사용하는 Android SDK 경로를 선택하세요.

## 4. 실행 순서

시스템 앱 시작 아이콘 → 셀콤 GIF → MainScreen → VFlowMainScreen.

앱 실행 중에는 홈/스캐닝/측정 기록/설정 아이콘 4개가 아래에 보입니다.
아이콘을 누르면 연한 하늘색 선택 표시와 가운데 문구가 바뀝니다.
버튼 아래 글자는 없지만 화면 읽기 기능을 위한 설명(contentDescription)은 있습니다.
실제 BLE 연결, 측정 시작, 데이터 저장, 설정 변경 기능은 아직 없습니다.
스캐닝 탭은 화면 이동일 뿐, 장비를 작동시키는 버튼이 아닙니다.

## 5. 파일별 역할

| 파일 | 역할 |
|---|---|
| MainActivity.kt | 앱 화면 시작 지점, GIF 표시와 메인 화면 연결 |
| VFlowMainScreen.kt | 하단 아이콘 메뉴, 선택 상태, 화면 내용 |
| res/drawable-nodpi/ic_nav_*.png | 화면에 그리는 컬러 아이콘 4개 |
| res/raw/logo.gif | 기존 셀콤 시작 로고 |
| AndroidManifest.xml | 앱의 시작 Activity, 앱 이름과 아이콘 등 등록 |
| app/build.gradle.kts | 앱 빌드 설정과 사용하는 라이브러리 |

펌웨어에 비유하면 MainActivity는 화면 시작 진입점, PNG는 리소스 데이터,
selectedTab은 현재 화면 번호입니다. Compose는 그 상태에 맞게 화면을 갱신합니다.

## 6. 자주 보는 코드 읽기

### @Composable

화면을 구성하는 함수라는 표시입니다. 함수 안에서 Text, Image, Column 등을 조합합니다.
새 파일에 함수를 만드는 것만으로 화면에 나타나지는 않습니다. 실행되는 화면에서 호출해야 합니다.

### modifier: Modifier = Modifier

이 화면의 크기, 여백 등을 바깥에서 전달받습니다.
MainActivity의 Scaffold가 제공한 innerPadding을 유지해서 상태 표시줄 및
시스템 버튼과 앱 화면이 겹치지 않도록 합니다.

### selectedTab

```kotlin
var selectedTab by rememberSaveable { mutableIntStateOf(0) }
```

0은 홈, 1은 스캐닝, 2는 기록, 3은 설정입니다.
mutableIntStateOf는 값 변경을 Compose에 알려주는 상태이고,
rememberSaveable은 화면 회전 등의 재생성 때 선택값 복원을 돕습니다.
앱을 삭제해도 유지되는 영구 설정 저장소라는 뜻은 아닙니다.

### 누르는 동작

```kotlin
onClick = { selectedTab = index }
```

선택 번호를 바꾸면 Compose가 선택 표시와 Text를 새 상태에 맞게 갱신합니다.
현재는 messages[selectedTab]으로 선택한 안내 문구를 표시합니다.

### 화면 배치

Column은 세로 배치입니다. 위쪽 Box는 weight(1f)로 남는 공간을 채우고,
아래 NavigationBar는 하단 메뉴를 표시합니다.
NavigationBarItem 하나가 버튼 하나입니다.
이 메뉴는 앱 내부 메뉴이며 안드로이드 시스템의 뒤로/홈 버튼과는 별개입니다.

### 이미지 참조

```kotlin
Image(
    painter = painterResource(R.drawable.ic_nav_home),
    contentDescription = "홈",
    modifier = Modifier.size(36.dp)
)
```

R.drawable.ic_nav_home은 PNG 파일을 가리키는 자동 생성 ID입니다.
확장자 .png는 쓰지 않습니다. 파일 이름은 소문자/숫자/밑줄로 정하세요.
drawable-nodpi는 밀도에 따른 자동 비트맵 확대/축소 없이 리소스를 보관하고,
화면에 표시할 크기는 Compose의 size로 지정합니다.
Image는 컬러 PNG를 원래 색으로 보여줍니다.

## 7. 직접 바꿔 보기

VFlowMainScreen.kt의 아래 값을 하나씩 변경하고 ▶ 실행해 보세요.

| 바꾸고 싶은 것 | 수정할 부분 |
|---|---|
| 아이콘 크기 | Modifier.size(36.dp)의 36을 40으로 |
| 선택 배경색 | indicatorColor = Color(0xFFDDF4FF) |
| 가운데 안내 문구 | messages 목록의 문자열 |
| 메뉴 배경 | NavigationBar의 containerColor |
| 메뉴 순서 | icons, descriptions, messages 세 목록을 같은 순서로 변경 |

dp는 화면 밀도를 고려하는 크기 단위입니다. PNG의 256픽셀과 36.dp는 서로 다른 개념입니다.
색상 값 0xFFDDF4FF의 앞 FF는 불투명도, 뒤 DDF4FF는 RGB 색상입니다.

## 8. 확인 순서와 검증 범위

1. GIF 다음에 하단 아이콘 4개가 나오는지 확인합니다.
2. 각 아이콘을 눌러 가운데 문구와 선택 배경이 바뀌는지 확인합니다.
3. 폰을 회전해 선택한 메뉴가 유지되는지 확인합니다.
4. 하단 메뉴와 시스템 버튼이 겹치지 않는지 확인합니다.

파일 연결, 리소스 존재, PNG 투명도, ZIP 무결성을 확인했습니다.
빌드를 시도했지만 Gradle 9.6.0 다운로드가 네트워크 제한으로 실패했습니다. Android SDK도 설정되지 않아 APK 빌드와 실기기 검증은 완료하지 못했습니다.
Android Studio에서 오류가 나면 Build 창의 첫 번째 오류를 기준으로 확인하세요.
