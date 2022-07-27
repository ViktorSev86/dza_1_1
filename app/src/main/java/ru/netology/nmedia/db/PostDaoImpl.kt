package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(
    private val db: SQLiteDatabase
) : PostDao {
    override fun getAll() =
        db.query(
            PostTable.NAME,
            PostTable.ALL_COLUMN_NAMES,
            null, null, null, null,
            "${PostTable.Column.ID.columnName} DESC"
        ).use { cursor ->
            List(cursor.count) {
                cursor.toPost()
            }
        }

    override fun save(post: Post): Post? {
        val values = ContentValues().apply {

        }
        return null
    }

    override fun lickeById(id: Long) {
        db.execSQL(
            """
                UPDATE ${PostTable.NAME} SET
                    ${PostTable.Column.LICKES.columnName} = ${PostTable.Column.LICKES.columnName} + CASE WHEN ${PostTable.Column.LICKED_BY_ME.columnName} THEN -1 ELSE 1 END,
                    ${PostTable.Column.LICKED_BY_ME.columnName} = CASE WHEN ${PostTable.Column.LICKED_BY_ME.columnName} THEN 0 ELSE 1 END
                WHERE ${PostTable.Column.ID.columnName} = ?
            """.trimIndent(),
            arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostTable.NAME,
            "${PostTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }

}