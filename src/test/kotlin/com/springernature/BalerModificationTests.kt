package com.springernature

import com.natpryce.hamkrest.assertion.assertThat
import helpers.containsAllElementsOf
import org.junit.Test


class BalerModificationTests {

    @Test
    fun `return institution permissions from user permissions`() {
        val librarianEmail = EmailAddress("librarian@casper-institution.com")
        val userPermissions = UserPermissions(
            librarianEmail = librarianEmail,
            readers = listOf(BPID("1234")),
            maintainers = listOf(BPID("7890")),
            forwarders = listOf(
                BPID("5555"),
                BPID("9999")
            )
        )

        val actual = userPermissions.toInstitutionPermissions(userPermissions.librarianEmail)
        assertThat(
            actual, containsAllElementsOf(
                listOf(
                    InstitutionPermissions(
                        institution = BPID("1234"),
                        readers = listOf(librarianEmail),
                        maintainers = emptyList(),
                        forwarders = emptyList()
                    ),
                    InstitutionPermissions(
                        institution = BPID("5555"),
                        readers = emptyList(),
                        maintainers = emptyList(),
                        forwarders = listOf(librarianEmail)
                    ),
                    InstitutionPermissions(
                        institution = BPID("7890"),
                        readers = emptyList(),
                        maintainers = listOf(librarianEmail),
                        forwarders = emptyList()
                    ),
                    InstitutionPermissions(
                        institution = BPID("9999"),
                        readers = emptyList(),
                        maintainers = emptyList(),
                        forwarders = listOf(librarianEmail)
                    )
                )
            )
        )

    }
}