package com.example.basketscoreboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { Scoreboard() } }
    }
}

data class Quarter(val n:Int,val low:Int,val high:Int)
data class Stats(
    val lowTotal:Int=0,val highTotal:Int=0,
    val lowWins:Int=0,val highWins:Int=0,val draws:Int=0,
    val quarters:List<Quarter> = emptyList()
)
private fun key(a:Int,b:Int)=minOf(a,b).toString()+"-"+maxOf(a,b)

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
    var expanded by remember { mutableStateOf<String?>(null) }
    val stats = remember { mutableStateMapOf<String,Stats>() }

    fun schedule() = if(teamCount==2) listOf(1 to 2,2 to 1)
        else listOf(1 to 2,1 to 3,2 to 3,2 to 1,3 to 1,3 to 2)
    val current=schedule()[gameIndex%schedule().size]

    LaunchedEffect(running,seconds){
        if(running && seconds>0){
            delay(1000); seconds--
            if(seconds<=0) running=false
        }
    }

    fun reset(){
        running=false; gameIndex=0; left=0; right=0
        seconds=baseSeconds; stats.clear(); expanded=null
    }

    fun finish(){
        running=false
        val a=current.first; val b=current.second
        val lo=minOf(a,b); val hi=maxOf(a,b); val k=key(a,b)
        val ls=if(a==lo) left else right
        val hs=if(a==lo) right else left
        val old=stats[k]?:Stats()
        stats[k]=old.copy(
            lowTotal=old.lowTotal+ls, highTotal=old.highTotal+hs,
            lowWins=old.lowWins+if(ls>hs)1 else 0,
            highWins=old.highWins+if(hs>ls)1 else 0,
            draws=old.draws+if(ls==hs)1 else 0,
            quarters=old.quarters+Quarter(old.quarters.size+1,ls,hs)
        )
        expanded=k; gameIndex++; left=0; right=0; seconds=baseSeconds
    }

    if(timeDialog) TimeDialog(seconds,{timeDialog=false}) {
        running=false; seconds=it; baseSeconds=it; timeDialog=false
    }

    Row(
        Modifier.fillMaxSize().padding(10.dp),
        horizontalArrangement=Arrangement.spacedBy(10.dp)
    ){
        Column(
            Modifier.weight(1.75f).fillMaxHeight(),
            verticalArrangement=Arrangement.spacedBy(8.dp)
        ){
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ){
                Text("농구 스코어보드",fontSize=22.sp,fontWeight=FontWeight.Black)
                Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){
                    FilterChip(teamCount==2,{teamCount=2;reset()},{Text("2팀")})
                    FilterChip(teamCount==3,{teamCount=3;reset()},{Text("3팀")})
                }
            }

            Card(Modifier.fillMaxWidth()){
                Row(
                    Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement=Arrangement.SpaceBetween,
                    verticalAlignment=Alignment.CenterVertically
                ){
                    Column{
                        Text("현재 쿼터 "+(gameIndex+1),fontSize=12.sp)
                        Text(current.first.toString()+"팀  VS  "+current.second+"팀",fontSize=25.sp,fontWeight=FontWeight.Black)
                    }
                    Column(horizontalAlignment=Alignment.CenterHorizontally){
                        Text(
                            "%02d:%02d".format(seconds/60,seconds%60),
                            fontSize=46.sp,fontWeight=FontWeight.Black,
                            modifier=Modifier.clickable{timeDialog=true}.padding(horizontal=12.dp)
                        )
                        Text("시간을 누르면 직접 설정",fontSize=10.sp)
                        Row(horizontalArrangement=Arrangement.spacedBy(6.dp)){
                            OutlinedButton({seconds=maxOf(0,seconds-30)}){Text("-30초")}
                            OutlinedButton({seconds+=30}){Text("+30초")}
                        }
                    }
                    Column(horizontalAlignment=Alignment.CenterHorizontally){
                        Button({running=!running}){Text(if(running)"일시정지" else "시작")}
                        TextButton({running=false;seconds=baseSeconds}){Text("초기화")}
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth().weight(1f),
                horizontalArrangement=Arrangement.spacedBy(10.dp)
            ){
                TeamCard(current.first,left,{left=maxOf(0,left+it)},Modifier.weight(1f).fillMaxHeight())
                TeamCard(current.second,right,{right=maxOf(0,right+it)},Modifier.weight(1f).fillMaxHeight())
            }

            Button({finish()},Modifier.fillMaxWidth().height(50.dp)){
                Text("쿼터 종료 · 대진별 누적 · 다음 대진",fontWeight=FontWeight.Bold)
            }
        }

        Column(
            Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()),
            verticalArrangement=Arrangement.spacedBy(7.dp)
        ){
            Text("대진별 누적",fontSize=20.sp,fontWeight=FontWeight.Black)
            Text("대진을 누르면 쿼터별 기록이 열립니다.",fontSize=11.sp)
            val keys=if(teamCount==2) listOf("1-2") else listOf("1-2","1-3","2-3")
            keys.forEach { k ->
                val p=k.split("-"); val lo=p[0].toInt(); val hi=p[1].toInt()
                StatCard(lo,hi,stats[k]?:Stats(),expanded==k){
                    expanded=if(expanded==k)null else k
                }
            }
        }
    }
}

@Composable
fun TeamCard(team:Int,score:Int,change:(Int)->Unit,modifier:Modifier){
    Card(modifier){
        Column(
            Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment=Alignment.CenterHorizontally,
            verticalArrangement=Arrangement.SpaceEvenly
        ){
            Text(team.toString()+"팀",fontSize=23.sp,fontWeight=FontWeight.Bold)
            Text(score.toString(),fontSize=68.sp,fontWeight=FontWeight.Black)
            Row(horizontalArrangement=Arrangement.spacedBy(7.dp)){
                listOf(1,2,3).forEach{ n ->
                    FilledTonalButton({change(n)}){Text("+"+n,fontSize=17.sp,fontWeight=FontWeight.Bold)}
                }
            }
            OutlinedButton({change(-1)}){Text("-1")}
        }
    }
}

@Composable
fun StatCard(lo:Int,hi:Int,s:Stats,open:Boolean,toggle:()->Unit){
    Card(Modifier.fillMaxWidth().clickable{toggle()}){
        Column(Modifier.fillMaxWidth().padding(12.dp)){
            Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.SpaceBetween){
                Text(lo.toString()+"팀 vs "+hi+"팀",fontWeight=FontWeight.Bold)
                Text(if(open)"▲" else "▼")
            }
            Text(
                "누적 "+s.lowTotal+" : "+s.highTotal,
                fontSize=25.sp,fontWeight=FontWeight.Black,
                modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center
            )
            Text(
                s.quarters.size.toString()+"쿼터 · 승 "+s.lowWins+" : "+s.highWins+
                    if(s.draws>0)" · 무 "+s.draws else "",
                modifier=Modifier.fillMaxWidth(),textAlign=TextAlign.Center
            )
            if(open){
                HorizontalDivider(Modifier.padding(vertical=6.dp))
                if(s.quarters.isEmpty()) Text("아직 기록이 없습니다.",fontSize=12.sp)
                s.quarters.forEach { q ->
                    Row(Modifier.fillMaxWidth().padding(vertical=2.dp),horizontalArrangement=Arrangement.SpaceBetween){
                        Text(q.n.toString()+"쿼터")
                        Text(lo.toString()+"팀 "+q.low+" : "+q.high+" "+hi+"팀",fontWeight=FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun TimeDialog(now:Int,dismiss:()->Unit,confirm:(Int)->Unit){
    var m by remember { mutableStateOf((now/60).toString()) }
    var s by remember { mutableStateOf((now%60).toString()) }
    AlertDialog(
        onDismissRequest=dismiss,
        title={Text("남은 시간 설정")},
        text={
            Row(verticalAlignment=Alignment.CenterVertically,horizontalArrangement=Arrangement.spacedBy(8.dp)){
                OutlinedTextField(
                    m,{m=it.filter(Char::isDigit).take(3)},
                    label={Text("분")},singleLine=true,
                    keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number),
                    modifier=Modifier.weight(1f)
                )
                Text(":")
                OutlinedTextField(
                    s,{s=it.filter(Char::isDigit).take(2)},
                    label={Text("초")},singleLine=true,
                    keyboardOptions=KeyboardOptions(keyboardType=KeyboardType.Number),
                    modifier=Modifier.weight(1f)
                )
            }
        },
        confirmButton={
            Button({
                val mm=m.toIntOrNull()?.coerceIn(0,999)?:0
                val ss=s.toIntOrNull()?.coerceIn(0,59)?:0
                confirm(mm*60+ss)
            }){Text("적용")}
        },
        dismissButton={TextButton(dismiss){Text("취소")}}
    )
}
