package com.mirodeon.vetapp.utils.extension

fun <T> Iterable<T>.forEachUntil(block: (T) -> Boolean) {
    for (item in this) {
        if (block(item)) {
            break
        }
    }
}