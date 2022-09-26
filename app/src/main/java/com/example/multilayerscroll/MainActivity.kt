package com.example.multilayerscroll

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import com.example.multilayerscroll.ui.theme.MultilayerscrollTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.util.jar.Manifest

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultilayerscrollTheme {
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        RECORD_AUDIO,
                        CAMERA
                    )
                )

                val lifecycleOwner = LocalLifecycleOwner.current
//                var observer = LifecycleObserver(lifecycleOwner)
                DisposableEffect(key1 = lifecycleOwner, effect ={
                    val observer = LifecycleEventObserver{
                        _,event ->
                        if(event == Lifecycle.Event.ON_START){
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                        lifecycleOwner.lifecycle.addObserver(observer)
                        onDispose {
                            lifecycleOwner.lifecycle.removeObserver(observer)
                        }
                } )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    permissionsState.permissions.forEach{
                        perm ->
                        when(perm.permission){
                            CAMERA->{
                                when{
                                    perm.hasPermission -> {
                                        Text(text="Camera Accepted")
                                    }
                                    perm.shouldShowRationale->{
                                        Text(text="Camera Needed")
                                    }
                                    perm.isPermanentlyDenied() -> {
                                        Text(text="Camera Denied")
                                    }
                                }
                            }
                            RECORD_AUDIO->{
                                when{
                                    perm.hasPermission ->{
                                        Text(text="Record Audio Accepted")
                                    }
                                    perm.shouldShowRationale->{
                                        Text(text="Record Audio Needed")
                                    }
                                    perm.isPermanentlyDenied()->{
                                        Text(text="Record Audio Denied")
                                    }
                                }
                            }
                        }
                    }
                    Text(text = "Camera permission accepted")
                    Text(text = "Record audio permission accepted")
                }

            }
//                var items by remember{
//                    mutableStateOf(
//                        (1..20).map{
//                            ListItem(
//                                title = "Item $it",
//                                isSelected = false
//                            )
//                        }
//                    )
//                }
//
////                items.filter{it.selected}
//
//                LazyColumn(modifier = Modifier.fillMaxSize()){
//                    items(items.size){
//                        i ->
//                        Row(
//                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.1f)
//                                .clickable {
//                                    items = items.mapIndexed {j,item->
//                                        if(i==j){
//                                            item.copy(isSelected = !(item.isSelected))
//                                        } else item
//                                    }
//                                }.padding(16.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ){
//                            Text(text = items[i].title)
//                            if(items[i].isSelected){
//                                Icon(imageVector = Icons.Default.CheckCircle,
//                                    contentDescription = "Item Checked",
//                                    tint = Color.Green,
//                                    modifier = Modifier.size(20.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MultilayerscrollTheme {
        Greeting("Android")
    }
}