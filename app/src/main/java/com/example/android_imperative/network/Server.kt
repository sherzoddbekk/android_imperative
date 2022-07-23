package com.example.android_imperative.network

object Server {

    val IS_TESTER = true

    init {
        System.loadLibrary("keys")
    }

    //    val SERVER_DEVELOPMENT = "https://www.episodate.com/"
//    val SERVER_PRODUCTION = "https://www.episodate.com/"
    external fun getDevelopment(): String
    external fun getProduction(): String
}