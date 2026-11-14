package id.ac.itera.pesanantar.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        val dbFile = File("pesanantar.db")
        val isNewDb = !dbFile.exists()
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.absolutePath}")
        if (isNewDb) {
            PesanAntarDb.Schema.create(driver)
        }
        return driver
    }
}

