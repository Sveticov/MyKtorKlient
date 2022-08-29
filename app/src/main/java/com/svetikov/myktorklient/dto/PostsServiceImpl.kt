package com.svetikov.myktorklient.dto

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*


class PostsServiceImpl(private val client: HttpClient) : PostsService {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            client.get { url(HttpRoutes.POSTS) }
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            emptyList()
        }

    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        TODO("Not yet implemented")
    }
}