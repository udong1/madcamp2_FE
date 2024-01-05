package com.example.madcamp2_fe.Login

import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType


object LoginRepository {
    suspend fun fetchUser(token:String): LoginResponse {

        val url = ""

        val httpClient = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        return httpClient.use {
            it.post<LoginResponse>(url) {
                contentType(ContentType.Application.Json)
                body = token
            }
        }
    }
}
