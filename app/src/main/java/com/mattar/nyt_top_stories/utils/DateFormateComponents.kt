package com.mattar.nyt_top_stories.utils

import android.text.format.DateUtils
import java.util.Calendar
import java.util.Date

fun dateString(date: Date): String {
    return DateUtils.getRelativeTimeSpanString(
        date.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.DAY_IN_MILLIS
    ).toString()
}