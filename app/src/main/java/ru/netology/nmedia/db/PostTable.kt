package ru.netology.nmedia.db

object PostTable {

    const val NAME = "posts"

    val DDL = """
        CREATE TABLE  $NAME (
            ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
	        ${Column.AUTHOR.columnName} TEXT NOT NULL,
	        ${Column.CONTENT.columnName} TEXT NOT NULL,
	        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
	        ${Column.LICKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT false,
	        ${Column.LICKES.columnName} INTEGER NOT NULL DEFAULT 0,
	        ${Column.VIDEO.columnName} TEXT
        );
    """.trimIndent()

    val ALL_COLUMN_NAMES = Column.values().map {
        it.columnName
    }.toTypedArray()

    enum class Column(val columnName: String) {
        ID("id"),
        AUTHOR("author"),
        CONTENT("content"),
        PUBLISHED("published"),
        LICKED_BY_ME("lickedByMe"),
        LICKES("lickes"),
        VIDEO("video")
    }
}