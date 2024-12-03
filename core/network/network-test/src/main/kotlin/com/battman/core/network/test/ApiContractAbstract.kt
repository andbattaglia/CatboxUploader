package com.battman.core.network.test

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.battman.core.test.jvm.MainDispatcherRule
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
abstract class ApiContractAbstract<T> {

    @get:Rule
    val coroutinesRule = MainDispatcherRule()

    lateinit var mockWebServer: MockWebServer

    private val json by lazy {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    fun enqueueResponse(fileName: String, code: Int = 200) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api/$fileName")
        val source = inputStream!!.source().buffer()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8)),
        )
    }

    fun createService(clazz: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .build()
            .create(clazz)
    }
}
