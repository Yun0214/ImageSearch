package com.example.imagesearch

import android.app.Application
import com.example.imagesearch.data.ServerAPI
import com.example.imagesearch.ui.base.BaseActivity
import com.example.imagesearch.util.diModule
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class App : Application() {

    companion object Instance {
        lateinit var INSTANCE : App
    }

    init {
        INSTANCE = this
    }

    //retrofit 기본 셋팅_builder
    object RetrofitBuilder{
        private const val CONNECT_TIMEOUT = 15
        private const val WRITE_TIMEOUT = 15
        private const val READ_TIMEOUT = 15
        private var Interface: ServerAPI? = null

        @Synchronized
        fun retrofitBuilder(): ServerAPI {
            return Interface?.let {
                return@let it
            } ?: run {
                //통신로그
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                //쿠키 메니저의 cookie policy 변경
                val cookieManager = CookieManager()
                cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

                //OkHttpClient
                val client = configureClient(OkHttpClient().newBuilder())!!
                    .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                    .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                    .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                    .cookieJar(JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                    .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                    .build()

                //Retrofit 설정
                Interface = Retrofit.Builder()
                    .baseUrl(Constants.KAKAO_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServerAPI::class.java) //인터페이스 연결

                return@run Interface!!
            }
        }

        /**
         * UnCertificated 허용
         */
        fun configureClient(builder: OkHttpClient.Builder): OkHttpClient.Builder? {
            val certs = arrayOf<TrustManager>(object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate>? {
                    return null
                }
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit
            })

            var ctx: SSLContext? = null
            try {
                ctx = SSLContext.getInstance("TLS")
                ctx!!.init(null, certs, SecureRandom())
            } catch (ex: java.security.GeneralSecurityException) {
                ex.printStackTrace()
            }

            try {
                val hostnameVerifier = HostnameVerifier { _, _ -> true }

                builder.sslSocketFactory(ctx!!.socketFactory).hostnameVerifier(hostnameVerifier)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return builder
        }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(INSTANCE, diModule)
    }
}