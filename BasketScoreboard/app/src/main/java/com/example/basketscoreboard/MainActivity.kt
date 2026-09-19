package com.example.basketscoreboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BasketScoreboardApp()
                }
            }
        }
    }
}

data class PairStats(
    var teamLowPoints: Int = 0,
    var teamHighPoints: Int = 0,
    var games: Int = 0,
    var lowWins: Int = 0,
    var highWins: Int = 0,
    var draws: Int = 0
)

private fun pairKey(a: Int, b: Int): String = if (a < b) "$a-$b" else "$b-$a"

@Composable
fun BasketScoreboardApp() {
    var teamCount by remember { mutableIntStateOf(3) }
    var gameMinutes by remember { mutableIntStateOf(10) }
    var gameIndex by remember { mutableIntStateOf(0) }
    var leftScore by remember { mutableIntStateOf(0) }
    var rightScore by remember { mutableIntStateOf(0) }
    var remainingSeconds by remember { mutableIntStateOf(gameMinutes * 60) }
    var running by remember { mutableStateOf(false) }

    val pairStats = remember { mutableStateMapOf<String, PairStats>() }

    fun schedule(): List<Pair<Int, Int>> = if (teamCount == 2) {
        listOf(1 to 2, 2 to 1)
    } else {
        listOf(
            1 to 2,
            1 to 3,
            2 to 3,
            2 to 1,
            3 to 1,
            3 to 2
        )
    }

    val currentMatch = schedule()[gameIndex % schedule().size]

    LaunchedEffect(running, remainingSeconds) {
        if (running && remainingSeconds > 0) {
            delay(1000)
            remainingSeconds -= 1
            if (remainingSeconds <= 0) running = false
        }
    }

    fun resetAll() {
        running = false
        gameIndex = 0
        leftScore = 0
        rightScore = 0
        remainingSeconds = gameMinutes * 60
        pairStats.clear()
    }

    fun finishCurrentGame() {
        running = false
        val a = currentMatch.first
        val b = currentMatch.second
        val low = minOf(a, b)
        val high = maxOf(a, b)
        val key = pairKey(a, b)
        val stats = pairStats.getOrPut(key) { PairStats() }

        val lowScore = if (a == low) leftScore else rightScore
        val highScore = if (a == low) rightScore else leftScore

        stats.teamLowPoints += lowScore
        stats.teamHighPoints += highScore
        stats.games += 1
        when {
            lowScore > highScore -> stats.lowWins += 1
            highScore > lowScore -> stats.highWins += 1
            else -> stats.draws += 1
        }

        pairStats[key] = stats.copy()
        gameIndex += 1
        leftScore = 0
        rightScore = 0
        remainingSeconds = gameMinutes * 60
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("농구 스코어보드", fontSize = 28.sp, fontWeight = FontWeight.Black)

        Card {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("게임 설정", fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = teamCount == 2,
                        onClick = { teamCount = 2; resetAll() },
                        label = { Text("2팀") }
                    )
                    FilterChip(
                        selected = teamCount == 3,
                        onClick = { teamCount = 3; resetAll() },
                        label = { Text("3팀") }
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(5, 7, 10, 12).forEach { minute ->
                        FilterChip(
                            selected = gameMinutes == minute,
                            onClick = {
                                gameMinutes = minute
                                remainingSeconds = minute * 60
                                running = false
                            },
                            label = { Text("${minute}분") }
                        )
                    }
                }
            }
        }

        Card {
            Column(
                Modifier.fillMaxWidth().padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("현재 경기 ${gameIndex + 1}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    "${currentMatch.first}팀  VS  ${currentMatch.second}팀",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "%02d:%02d".format(remainingSeconds / 60, remainingSeconds % 60),
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .clickable { remainingSeconds += 30 }
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                )
                Text("시계를 누르면 +30초", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { running = !running }) {
                        Text(if (running) "일시정지" else "시작")
                    }
                    OutlinedButton(onClick = {
                        running = false
                        remainingSeconds = gameMinutes * 60
                    }) {
                        Text("시간 초기화")
                    }
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TeamScoreCard(
                team = currentMatch.first,
                score = leftScore,
                onChange = { delta -> leftScore = maxOf(0, leftScore + delta) },
                modifier = Modifier.weight(1f)
            )
            TeamScoreCard(
                team = currentMatch.second,
                score = rightScore,
                onChange = { delta -> rightScore = maxOf(0, rightScore + delta) },
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = { finishCurrentGame() },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("현재 경기 종료 · 기록 누적 · 다음 경기", fontWeight = FontWeight.Bold)
        }

        Text("대진별 누적", fontSize = 20.sp, fontWeight = FontWeight.Black)

        val keys = if (teamCount == 2) listOf("1-2") else listOf("1-2", "1-3", "2-3")
        keys.forEach { key ->
            val (lowText, highText) = key.split("-")
            val low = lowText.toInt()
            val high = highText.toInt()
            val s = pairStats[key] ?: PairStats()
            PairStatsCard(low, high, s)
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TeamScoreCard(
    team: Int,
    score: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("${team}팀", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text("$score", fontSize = 54.sp, fontWeight = FontWeight.Black)
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf(1, 2, 3).forEach { add ->
                    FilledTonalButton(
                        onClick = { onChange(add) },
                        contentPadding = PaddingValues(horizontal = 10.dp)
                    ) { Text("+$add") }
                }
            }
            OutlinedButton(onClick = { onChange(-1) }) { Text("-1") }
        }
    }
}

@Composable
private fun PairStatsCard(low: Int, high: Int, s: PairStats) {
    Card(shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text("${low}팀 vs ${high}팀", fontSize = 19.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(
                "누적 ${s.teamLowPoints} : ${s.teamHighPoints}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "${s.games}경기 · 승 ${s.lowWins} : ${s.highWins}" + if (s.draws > 0) " · 무 ${s.draws}" else "",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
