package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.dto.Post

fun Cursor.toPost() = Post(
    id = getLong(getColumnIndexOrThrow(PostTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostTable.Column.AUTHOR.columnName)),
    content = getString(getColumnIndexOrThrow(PostTable.Column.CONTENT.columnName)),
    published = getString(getColumnIndexOrThrow(PostTable.Column.PUBLISHED.columnName)),
    likes = getInt(getColumnIndexOrThrow(PostTable.Column.LICKES.columnName)),
    likedByMe = getInt(getColumnIndexOrThrow(PostTable.Column.LICKED_BY_ME.columnName)) != 0,
    video = getString(getColumnIndexOrThrow(PostTable.Column.VIDEO.columnName)),
)