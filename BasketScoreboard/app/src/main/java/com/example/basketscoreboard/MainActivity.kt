package com.example.basketscoreboard

import android.os.Bundle
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
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
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(background=Color.Black,surface=Color.Black,primary=Color(0xFFFFC107),onBackground=Color.White,onSurface=Color.White)) {
                Scoreboard()
            }
        }
        window.decorView.post { hideSystemUI() }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
        }
    }
}

data class Quarter(val n:Int,val low:Int,val high:Int)
data class Stats(val lowTotal:Int=0,val highTotal:Int=0,val quarters:List<Quarter> = emptyList())
private fun pairKey(a:Int,b:Int)=minOf(a,b).toString()+"-"+maxOf(a,b)

@Composable
fun Scoreboard(){
    var teamCount by remember { mutableIntStateOf(3) }
    var gameIndex by remember { mutableIntStateOf(0) }
    var left by remember { mutableIntStateOf(0) }
    var right by remember { mutableIntStateOf(0) }
    var baseSeconds by remember { mutableIntStateOf(600) }
    var seconds by remember { mutableIntStateOf(600) }
    var running by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }
    var timeDialog by remember { mutableStateOf(false) }
    var recordsDialog by remember { mutableStateOf(false) }
    var pendingMatch by remember { mutableStateOf<Pair<Int,Int>?>(null) }
    val stats = remember { mutableStateMapOf<String,Stats>() }

    fun schedule()=if(teamCount==2) listOf(1 to 2,2 to 1) else listOf(1 to 2,1 to 3,2 to 3,2 to 1,3 to 1,3 to 2)
    val current=schedule()[gameIndex%schedule().size]

    fun loadMatch(a:Int,b:Int){
        val idx=schedule().indexOfFirst{it.first==a && it.second==b}
        if(idx>=0) gameIndex=idx
        val lo=minOf(a,b)
        val s=stats[pairKey(a,b)]?:Stats()
        left=if(a==lo) s.lowTotal else s.highTotal
        right=if(a==lo) s.highTotal else s.lowTotal
        seconds=baseSeconds
        running=false
        gameOver=false
        recordsDialog=false
    }

    LaunchedEffect(running,seconds){
        if(running && seconds>0){
            delay(1000)
            seconds--
            if(seconds<=0){ seconds=0; running=false; gameOver=true }
        }
    }

    fun resetAll(){
        running=false; gameOver=false; gameIndex=0; left=0; right=0; seconds=baseSeconds; stats.clear()
    }

    fun finishGame(){
        running=false
        val a=current.first
        val b=current.second
        val lo=minOf(a,b)
        val k=pairKey(a,b)
        val old=stats[k]?:Stats()
        val currentLow=if(a==lo) left else right
        val currentHigh=if(a==lo) right else left
        val qLow=maxOf(0,currentLow-old.lowTotal)
        val qHigh=maxOf(0,currentHigh-old.highTotal)
        stats[k]=old.copy(lowTotal=currentLow,highTotal=currentHigh,quarters=old.quarters+Quarter(old.quarters.size+1,qLow,qHigh))
        gameIndex++
        val next=schedule()[gameIndex%schedule().size]
        val nextLo=minOf(next.first,next.second)
        val nextStats=stats[pairKey(next.first,next.second)]?:Stats()
        left=if(next.first==nextLo) nextStats.lowTotal else nextStats.highTotal
        right=if(next.first==nextLo) nextStats.highTotal else nextStats.lowTotal
        seconds=baseSeconds
        running=false
        gameOver=false
    }

    if(timeDialog) TimeDialog(seconds,{timeDialog=false}) {
        running=false; seconds=it; baseSeconds=it; timeDialog=false
    }
    if(recordsDialog) RecordsDialog(
        teamCount=teamCount,
        stats=stats,
        onDismiss={recordsDialog=false},
        onLoad={a,b->
            pendingMatch=a to b
            recordsDialog=false
        }
    )

    pendingMatch?.let { match ->
        AlertDialog(
            onDismissRequest={pendingMatch=null},
            title={Text("대진 변경")},
            text={Text(match.first.toString()+"팀 vs "+match.second+"팀 누적 점수를 불러와 현재 경기로 전환할까요?")},
            confirmButton={
                Button(onClick={loadMatch(match.first,match.second);pendingMatch=null}){Text("불러오기")}
            },
            dismissButton={TextButton(onClick={pendingMatch=null}){Text("취소")}}
        )
    }

    Box(Modifier.fillMaxSize().background(Color.Black)){
        Column(Modifier.fillMaxSize().padding(horizontal=14.dp,vertical=8.dp)){
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween,verticalAlignment=Alignment.CenterVertically){
                Text("GAME "+(gameIndex+1)+"   "+current.first+"팀 VS "+current.second+"팀",color=Color(0xFFBDBDBD),fontSize=15.sp,fontWeight=FontWeight.Bold)
                Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){
                    OutlinedButton(onClick={recordsDialog=true},colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)){ Text("기록") }
                    OutlinedButton(onClick={teamCount=if(teamCount==3)2 else 3; resetAll()},colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)){ Text(if(teamCount==3)"3팀" else "2팀") }
                }
            }

            Row(Modifier.fillMaxWidth().weight(1f),verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(10.dp)){
                ScorePanel(current.first,left,!gameOver,{left=maxOf(0,left+it)},Modifier.weight(1f))

                Column(Modifier.width(260.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){
                    Text("%02d:%02d".format(seconds/60,seconds%60),color=Color(0xFFFFC107),fontSize=76.sp,fontWeight=FontWeight.Black,maxLines=1,modifier=Modifier.clickable(enabled=!gameOver){timeDialog=true})
                    Row(horizontalArrangement=Arrangement.spacedBy(8.dp)){
                        OutlinedButton(onClick={seconds=maxOf(0,seconds-30);if(seconds==0){running=false;gameOver=true}},enabled=!gameOver,colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)){Text("-30초")}
                        OutlinedButton(onClick={seconds+=30},enabled=!gameOver,colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)){Text("+30초")}
                    }
                    Spacer(Modifier.height(8.dp))
                    Button(onClick={running=!running},enabled=!gameOver,modifier=Modifier.fillMaxWidth().height(56.dp),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFFFC107),contentColor=Color.Black)){
                        Text(if(running)"일시정지" else "시작",fontSize=22.sp,fontWeight=FontWeight.Black)
                    }
                    TextButton(onClick={running=false;seconds=baseSeconds},enabled=!gameOver,colors=ButtonDefaults.textButtonColors(contentColor=Color.Gray)){Text("시간 초기화")}
                }

                ScorePanel(current.second,right,!gameOver,{right=maxOf(0,right+it)},Modifier.weight(1f))
            }
        }

        if(gameOver){
            Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha=0.84f)).clickable{finishGame()},contentAlignment=Alignment.Center){
                Column(horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(8.dp)){
                    Text("경기 종료",color=Color.White,fontSize=48.sp,fontWeight=FontWeight.Black)
                    Text(current.first.toString()+"팀  "+left+" : "+right+"  "+current.second+"팀",color=Color(0xFFFFC107),fontSize=40.sp,fontWeight=FontWeight.Black)
                    Text("터치하면 다음 경기",color=Color.White,fontSize=20.sp)
                    Text("다음 대진의 누적 점수에서 이어집니다",color=Color.LightGray,fontSize=14.sp)
                }
            }
        }
    }
}

@Composable
private fun ScorePanel(team:Int,score:Int,enabled:Boolean,onChange:(Int)->Unit,modifier:Modifier){
    Column(modifier.fillMaxHeight().padding(horizontal=18.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.Center){
        Text(team.toString()+"팀",color=Color(0xFFEEEEEE),fontSize=32.sp,fontWeight=FontWeight.Bold)
        Text(score.toString(),color=Color.White,fontSize=132.sp,fontWeight=FontWeight.Black,textAlign=TextAlign.Center,maxLines=1)
        Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){
            listOf(1,2,3).forEach{n->
                Button(onClick={onChange(n)},enabled=enabled,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF1C1C1C),contentColor=Color.White),contentPadding=PaddingValues(horizontal=16.dp,vertical=10.dp)){Text("+"+n,fontSize=18.sp,fontWeight=FontWeight.Bold)}
            }
            OutlinedButton(onClick={onChange(-1)},enabled=enabled,modifier=Modifier.widthIn(min=72.dp),colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White),contentPadding=PaddingValues(horizontal=8.dp,vertical=10.dp)){Text("-1",fontSize=16.sp,fontWeight=FontWeight.Bold,maxLines=1)}
        }
    }
}

@Composable
private fun RecordsDialog(teamCount:Int,stats:Map<String,Stats>,onDismiss:()->Unit,onLoad:(Int,Int)->Unit){
    AlertDialog(
        onDismissRequest=onDismiss,
        containerColor=Color(0xFF111111),
        title={Text("누적 기록",color=Color.White,fontWeight=FontWeight.Black)},
        text={
            Column(Modifier.fillMaxWidth().heightIn(max=430.dp).verticalScroll(rememberScrollState()),verticalArrangement=Arrangement.spacedBy(10.dp)){
                val keys=if(teamCount==2) listOf("1-2") else listOf("1-2","1-3","2-3")
                keys.forEach{k->
                    val p=k.split("-"); val lo=p[0].toInt(); val hi=p[1].toInt(); val s=stats[k]?:Stats()
                    Surface(color=Color(0xFF1E1E1E),shape=MaterialTheme.shapes.medium){
                        Column(Modifier.fillMaxWidth().padding(12.dp)){
                            Text(lo.toString()+"팀 vs "+hi+"팀",color=Color.White,fontWeight=FontWeight.Bold)
                            Text(s.lowTotal.toString()+" : "+s.highTotal,color=Color(0xFFFFC107),fontSize=30.sp,fontWeight=FontWeight.Black,modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center)
                            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.spacedBy(8.dp)){
                                Button(onClick={onLoad(lo,hi)},modifier=Modifier.weight(1f),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFFFC107),contentColor=Color.Black)){
                                    Text(lo.toString()+"팀 → "+hi+"팀",fontSize=12.sp,fontWeight=FontWeight.Bold)
                                }
                                OutlinedButton(onClick={onLoad(hi,lo)},modifier=Modifier.weight(1f),colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.White)){
                                    Text(hi.toString()+"팀 → "+lo+"팀",fontSize=12.sp,fontWeight=FontWeight.Bold)
                                }
                            }
                            if(s.quarters.isNotEmpty()){
                                HorizontalDivider(color=Color.DarkGray,modifier=Modifier.padding(vertical=6.dp))
                                s.quarters.forEach{q->
                                    Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
                                        Text(q.n.toString()+"쿼터",color=Color.LightGray)
                                        Text(lo.toString()+"팀 "+q.low+" : "+q.high+" "+hi+"팀",color=Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton={Button(onClick=onDismiss,colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFFFC107),contentColor=Color.Black)){Text("닫기")}}
    )
}

@Composable
private fun TimeDialog(now:Int,dismiss:()->Unit,confirm:(Int)->Unit){
    var m by remember { mutableStateOf((now/60).toString()) }
    var s by remember { mutableStateOf((now%60).toString()) }
    AlertDialog(
        onDismissRequest=dismiss,
        title={Text("시간 설정")},
        text={
            Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)){
                OutlinedTextField(value=m,onValueChange={m=it.filter(Char::isDigit).take(3)},label={Text("분")},singleLine=true,keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number),modifier=Modifier.weight(1f))
                Text(":")
                OutlinedTextField(value=s,onValueChange={s=it.filter(Char::isDigit).take(2)},label={Text("초")},singleLine=true,keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number),modifier=Modifier.weight(1f))
            }
        },
        confirmButton={Button(onClick={val mm=m.toIntOrNull()?.coerceIn(0,999)?:0;val ss=s.toIntOrNull()?.coerceIn(0,59)?:0;confirm(mm*60+ss)}){Text("적용")}},
        dismissButton={TextButton(onClick=dismiss){Text("취소")}}
    )
}