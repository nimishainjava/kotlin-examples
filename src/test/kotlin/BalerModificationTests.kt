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
            forwarders = listOf(BPID("5555"), BPID("9999"))
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

fun UserPermissions.toInstitutionPermissions(librarianEmail: EmailAddress): List<InstitutionPermissions> {
    val seed = emptyMap<BPID, InstitutionPermissions>()
    val res1 = readers.fold(seed)
    { map: Map<BPID, InstitutionPermissions>, institution: BPID ->
        constituteInstitutionBasedPermissions(map, institution) { permissions ->
            permissions.copy(readers = permissions.readers + librarianEmail)
        }
    }
    val res2 = forwarders.fold(res1)
    { map: Map<BPID, InstitutionPermissions>, institution ->
        constituteInstitutionBasedPermissions(map, institution) { permissions ->
            permissions.copy(forwarders = permissions.forwarders + librarianEmail)
        }
    }
    val res3 = maintainers.fold(res2)
    { map: Map<BPID, InstitutionPermissions>, institution: BPID ->
        constituteInstitutionBasedPermissions(map, institution) { permissions ->
            permissions.copy(maintainers = permissions.maintainers + librarianEmail)
        }
    }
    return res3.values.toList()
}

private fun constituteInstitutionBasedPermissions(
    map: Map<BPID, InstitutionPermissions>,
    institution: BPID,
    institutionPermissions: (InstitutionPermissions) -> InstitutionPermissions
): Map<BPID, InstitutionPermissions> {
    val permissions = map[institution] ?: InstitutionPermissions(institution = institution)
    return map + (institution to institutionPermissions(permissions))
}

data class EmailAddress(val raw: String)
data class BPID(val raw: String)

data class UserPermissions(
    val librarianEmail: EmailAddress,
    val readers: List<BPID>,
    val maintainers: List<BPID>,
    val forwarders: List<BPID>
)

data class InstitutionPermissions(
    val institution: BPID,
    val readers: List<EmailAddress> = emptyList(),
    val maintainers: List<EmailAddress> = emptyList(),
    val forwarders: List<EmailAddress> = emptyList()
)