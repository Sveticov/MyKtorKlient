package com.svetikov.myktorklient.dto

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*


interface PostsService {

    suspend fun getPosts():List<PostResponse>

    suspend fun createPost(postRequest: PostRequest):PostResponse?

    suspend fun getPostsId(id:Int):PostResponse?

    companion object{
        fun create():PostsService {
            return PostsServiceImpl(
                client = HttpClient(Android){
                    install(Logging){
                        level = LogLevel.ALL
                    }
                    install(JsonFeature){
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}


