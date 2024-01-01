package com.mirodeon.vetapp.utils.extension

fun <K, V> Map<K, V>.forEachUntil(block: (Map.Entry<K, V>) -> Boolean) {
    for (item in this) {
        if (block(item)) {
            break
        }
    }
}