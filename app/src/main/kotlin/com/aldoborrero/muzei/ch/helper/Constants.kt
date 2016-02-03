package com.aldoborrero.muzei.ch.helper

object Constants {

    // OkHttp configuration
    val OKHTTP_CONNECT_TIMEOUT: Long = 15000
    val OKHTTP_READ_TIMEOUT: Long = 20000
    val OKHTTP_WRITE_TIMEOUT: Long = 20000

    // Jitter configuration
    val ROTATE_TIME_MILLIS = 3 * 60 * 60 * 1000 // rotate every 3 hours
    val MAX_JITTER_MILLIS = 5 * 60 * 1000 // Jitter from 5 minutes
}
