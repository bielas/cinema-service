package com.fourthwall.cinemaservice

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures
import org.junit.jupiter.api.Test

class HexagonalArchitectureTest {

    private val BASE = "com.fourthwall.cinemaservice"
    private val ADAPTER_IN = "adapter.input"
    private val ADAPTER_OUT = "adapter.output"

    private val importedClasses: JavaClasses = ClassFileImporter()
        .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
        .importPackages(BASE)

    @Test
    fun `ensure Hexagonal Architecture`() {
        val ensureHexagonalArchitecture: ArchRule = Architectures.onionArchitecture()
            .domainModels("$BASE.domain..")
            .domainServices("$BASE.domain..")
            .applicationServices("$BASE.configuration..")
            .adapter("input.api", "$BASE.$ADAPTER_IN.api..")
            .adapter("output.db", "$BASE.$ADAPTER_OUT.db..")
            .adapter("output.imdb", "$BASE.$ADAPTER_OUT.imdb..")

        ensureHexagonalArchitecture.check(importedClasses)
    }
}
