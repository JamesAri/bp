package cz.zcu.students.lostandfound.common.extensions

fun Any?.isNull() = this == null
fun Any?.isNotNull() = this != isNull()