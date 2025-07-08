package com.skoolroom.skoolr.ai.tutor.util

fun String.cleanMarkdown(): String {
    return this
        .replace(Regex("""\*\*(.*?)\*\*"""), "$1")
        .replace(Regex("""\*(.*?)\*"""), "$1")
        .replace(Regex("""`(.*?)`"""), "$1")
        .replace(Regex("""_(.*?)_"""), "$1")
}