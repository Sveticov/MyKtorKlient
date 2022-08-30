package com.svetikov.myktorklient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.svetikov.myktorklient.dto.PostResponse
import com.svetikov.myktorklient.dto.PostsService
import com.svetikov.myktorklient.ui.theme.ColorText
import com.svetikov.myktorklient.ui.theme.MyKtorKlientTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val service = PostsService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var idPosts by remember {
                mutableStateOf(1)
            }
            val scope = rememberCoroutineScope()

            var postsData by remember {
                mutableStateOf(PostResponse("", "", 0, 0))
            }
            val posts = produceState<List<PostResponse>>(
                initialValue = emptyList(),
                producer = {
                    value = service.getPosts()
                })
            val postsId =
                produceState<PostResponse>(initialValue = PostResponse("", "", 0, 0), producer = {
                    value = service.getPostsId(idPosts)!!
                })

            MyKtorKlientTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {


                    Log.d("POST ID", "${postsId.value.title}")
                    Image(
                        painter = painterResource(id = R.drawable.img),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(0.5f),
                        contentScale = ContentScale.FillBounds
                    )

                    Column {
                        Card(
                            modifier = Modifier
                                .padding(20.dp)
                                .border(2.dp, Color.Blue)
                                .alpha(0.7f)
                                .clip(RoundedCornerShape(5.dp))
                        ) {
                            Column(modifier = Modifier.alpha(0.7f)) {
                                Text(text = "${postsId.value.id}")
                                Text(
                                    text = "${postsId.value.title}",
                                    fontSize = 22.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = "${postsId.value.body}",
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Button(onClick = {
                                    idPosts += 1


                                    Log.d("POST++", "$idPosts")
                                    Log.d("POST1", "scope 0 $postsData")
                                    scope.launch {
                                        var p = service.getPostsId(idPosts)
                                        postsData = p!!
                                        Log.d("POST1", "scope")
                                        Log.d("POST1", "$p")
                                    }


                                }) {
                                    Text("Next ID")
                                }

                                Text("$postsData")
                            }
                        }
                    }
//
//                    LazyColumn {
//                        items(posts.value) {
//                            Card(
//                                modifier = Modifier
//                                    .padding(2.dp)
//                                    .clip(shape = RoundedCornerShape(15.dp))
//                                    .border(2.dp, ColorText)
//                                    .alpha(0.6f)
//
//                            ) {
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(16.dp)
//                                ) {
//
//                                    Text(
//                                        text = it.title, fontSize = 20.sp, color = ColorText,
//                                        modifier = Modifier.alpha(1.0f)
//                                    )
//                                    Spacer(modifier = Modifier.height(4.dp))
//                                    Text(text = it.body, fontSize = 14.sp, color = ColorText)
//
//
//                                }
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

