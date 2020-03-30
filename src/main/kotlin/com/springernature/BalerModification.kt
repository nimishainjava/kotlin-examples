package com.springernature


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