package com.example.basketscoreboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Scoreboard()
            }
        }
    }
}

data class Quarter(val n: Int, val low: Int, val high: Int)

data class Stats(
    val lowTotal: Int = 0,
    val highTotal: Int = 0,
    val lowWins: Int = 0,
    val highWins: Int = 0,
    val draws: Int = 0,
    val quarters: List<Quarter> = emptyList()
)

private fun key(a: Int, b: Int) = minOf(a, b).toString() + "-" + maxOf(a, b)

@Composable
fun Scoreboard() {
    var teamCount by remember { mutableIntStateOf(3) }
    var gameIndex by remember { mutableIntStateOf(0) }
    var left by remember { mutableIntStateOf(0) }
    var right by remember { mutableIntStateOf(0) }
    var baseSeconds by remember { mutableIntStateOf(600) }
    var seconds by remember { mutableIntStateOf(600) }
    var running by remember { mutableStateOf(false) }
    var timeDialog by remember { mutableStateOf(false) }
    var recordsDialog by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }

    val stats = remember { mutableStateMapOf<String, Stats>() }

    fun schedule() = if (teamCount == 2) {
        listOf(1 to 2, 2 to 1)
    } else {
        listOf(1 to 2, 1 to 3, 2 to 3, 2 to 1, 3 to 1, 3 to 2)
    }

    val current = schedule()[gameIndex % schedule().size]

    LaunchedEffect(running, seconds) {
        if (running && seconds > 0) {
            delay(1000)
            seconds--
            if (seconds <= 0) {
                seconds = 0
                running = false
                gameOver = true
            }
        }
    }

    fun resetAll() {
        running = false
        gameOver = false
        gameIndex = 0
        left = 0
        right = 0
        seconds = baseSeconds
        stats.clear()
    }

    fun finish() {
        running = false

        val a = current.first
        val b = current.second
        val lo = minOf(a, b)
        val k = key(a, b)
        val old = stats[k] ?: Stats()

        val currentLow = if (a == lo) left else right
        val currentHigh = if (a == lo) right else left
        val quarterLow = maxOf(0, currentLow - old.lowTotal)
        val quarterHigh = maxOf(0, currentHigh - old.highTotal)

        stats[k] = old.copy(
            lowTotal = currentLow,
            highTotal = currentHigh,
            lowWins = old.lowWins + if (quarterLow > quarterHigh) 1 else 0,
            highWins = old.highWins + if (quarterHigh > quarterLow) 1 else 0,
            draws = old.draws + if (quarterLow == quarterHigh) 1 else 0,
            quarters = old.quarters + Quarter(old.quarters.size + 1, quarterLow, quarterHigh)
        )

        gameIndex++

        val next = schedule()[gameIndex % schedule().size]
        val nextLo = minOf(next.first, next.second)
        val nextStats = stats[key(next.first, next.second)] ?: Stats()
        left = if (next.first == nextLo) nextStats.lowTotal else nextStats.highTotal
        right = if (next.first == nextLo) nextStats.highTotal else nextStats.lowTotal

        seconds = baseSeconds
        running = false
        gameOver = false
    }

    if (timeDialog) {
        TimeDialog(
            now = seconds,
            dismiss = { timeDialog = false },
            confirm = {
                running = false
                seconds = it
                baseSeconds = it
                timeDialog = false
            }
        )
    }

    if (recordsDialog) {
        RecordsDialog(
            teamCount = teamCount,
            stats = stats,
            onDismiss = { recordsDialog = false }
        )
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    current.first.toString() + "팀  VS  " + current.second + "팀",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { recordsDialog = true },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("기록 보기")
                    }
                    OutlinedButton(
                        onClick = {
                            teamCount = if (teamCount == 3) 2 else 3
                            resetAll()
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text(if (teamCount == 3) "3팀" else "2팀")
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScoreSide(
                    team = current.first,
                    score = left,
                    enabled = !gameOver,
                    onChange = { left = maxOf(0, left + it) },
                    modifier = Modifier.weight(1f)
                )

                Column(
                    Modifier.widthIn(min = 220.dp, max = 300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "%02d:%02d".format(seconds / 60, seconds % 60),
                        color = Color(0xFFFFD54F),
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier
                            .clickable(enabled = !gameOver) { timeDialog = true }
                            .padding(horizontal = 8.dp)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = {
                                seconds = maxOf(0, seconds - 30)
                                if (seconds == 0) {
                                    running = false
                                    gameOver = true
                                }
                            },
                            enabled = !gameOver,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Text("-30초")
                        }
                        OutlinedButton(
                            onClick = { seconds += 30 },
                            enabled = !gameOver,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                        ) {
                            Text("+30초")
                        }
                    }

                    Button(
                        onClick = { if (!gameOver) running = !running },
                        enabled = !gameOver,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(if (running) "일시정지" else "시작", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }

                    TextButton(
                        onClick = {
                            running = false
                            seconds = baseSeconds
                        },
                        enabled = !gameOver,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.LightGray)
                    ) {
                        Text("시간 초기화")
                    }
                }

                ScoreSide(
                    team = current.second,
                    score = right,
                    enabled = !gameOver,
                    onChange = { right = maxOf(0, right + it) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (gameOver) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.76f))
                    .clickable { finish() },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "경기 종료",
                        color = Color.White,
                        fontSize = 46.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        current.first.toString() + "팀  " + left + " : " + right + "  " + current.second + "팀",
                        color = Color(0xFFFFD54F),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        "화면을 터치하면 다음 경기",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        "다음 대진은 기존 누적 점수에서 정지 상태로 대기합니다",
                        color = Color.LightGray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ScoreSide(
    team: Int,
    score: Int,
    enabled: Boolean,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            team.toString() + "팀",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            score.toString(),
            color = Color.White,
            fontSize = 110.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(1, 2, 3).forEach { add ->
                Button(
                    onClick = { onChange(add) },
                    enabled = enabled,
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp)
                ) {
                    Text("+$add", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
            OutlinedButton(
                onClick = { onChange(-1) },
                enabled = enabled,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text("-1", fontSize = 20.sp)
            }
        }
    }
}

@Composable
private fun RecordsDialog(
    teamCount: Int,
    stats: Map<String, Stats>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("대진별 누적 기록") },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val keys = if (teamCount == 2) listOf("1-2") else listOf("1-2", "1-3", "2-3")
                keys.forEach { k ->
                    val p = k.split("-")
                    val lo = p[0].toInt()
                    val hi = p[1].toInt()
                    val s = stats[k] ?: Stats()

                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(
                                lo.toString() + "팀 vs " + hi + "팀",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text(
                                "누적 " + s.lowTotal + " : " + s.highTotal,
                                fontWeight = FontWeight.Black,
                                fontSize = 26.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                s.quarters.size.toString() + "쿼터 · 승 " + s.lowWins + " : " + s.highWins +
                                    if (s.draws > 0) " · 무 " + s.draws else "",
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )

                            if (s.quarters.isNotEmpty()) {
                                HorizontalDivider(Modifier.padding(vertical = 6.dp))
                                s.quarters.forEach { q ->
                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(q.n.toString() + "쿼터")
                                        Text(
                                            lo.toString() + "팀 " + q.low + " : " + q.high + " " + hi + "팀",
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("닫기")
            }
        }
    )
}

@Composable
private fun TimeDialog(
    now: Int,
    dismiss: () -> Unit,
    confirm: (Int) -> Unit
) {
    var m by remember { mutableStateOf((now / 60).toString()) }
    var s by remember { mutableStateOf((now % 60).toString()) }

    AlertDialog(
        onDismissRequest = dismiss,
        title = { Text("남은 시간 설정") },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = m,
                    onValueChange = { m = it.filter(Char::isDigit).take(3) },
                    label = { Text("분") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Text(":")
                OutlinedTextField(
                    value = s,
                    onValueChange = { s = it.filter(Char::isDigit).take(2) },
                    label = { Text("초") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val mm = m.toIntOrNull()?.coerceIn(0, 999) ?: 0
                    val ss = s.toIntOrNull()?.coerceIn(0, 59) ?: 0
                    confirm(mm * 60 + ss)
                }
            ) {
                Text("적용")
            }
        },
        dismissButton = {
            TextButton(onClick = dismiss) {
                Text("취소")
            }
        }
    )
}
