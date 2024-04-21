package com.dicoding.github.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.getOrAwaitValue(): T {
    var data: T? = null
    val latch = java.util.concurrent.CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(2, java.util.concurrent.TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data as T
}
