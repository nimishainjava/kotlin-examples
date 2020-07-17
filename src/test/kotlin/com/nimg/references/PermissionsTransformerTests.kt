package com.nimg.references

import com.natpryce.hamkrest.assertion.assertThat
import org.junit.Test


class PermissionsTransformerTests {

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

        val expected =
            listOf(
                InstitutionPermission(
                    institution = BPID("1234"),
                    readers = listOf(librarianEmail),
                    maintainers = emptyList(),
                    forwarders = emptyList()
                ),
                InstitutionPermission(
                    institution = BPID("5555"),
                    readers = emptyList(),
                    maintainers = emptyList(),
                    forwarders = listOf(librarianEmail)
                ),
                InstitutionPermission(
                    institution = BPID("7890"),
                    readers = emptyList(),
                    maintainers = listOf(librarianEmail),
                    forwarders = emptyList()
                ),
                InstitutionPermission(
                    institution = BPID("9999"),
                    readers = emptyList(),
                    maintainers = emptyList(),
                    forwarders = listOf(librarianEmail)
                )
            )


        val actual = userPermissions.toInstitutionPermissions(userPermissions.librarianEmail)
        assertThat(actual.toList(), containsAllElementsOf(expected))

    }
}