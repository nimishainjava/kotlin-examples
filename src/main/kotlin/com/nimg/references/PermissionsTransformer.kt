package com.nimg.references

import java.util.Collections.emptyMap


fun UserPermissions.toInstitutionPermissions(librarianEmail: EmailAddress): Set<InstitutionPermission> {
    val seed = emptyMap<BPID, InstitutionPermission>()
    val res1 = readers.fold(seed)
    { map: Map<BPID, InstitutionPermission>, institution: BPID ->
        constituteInstitutionBasedPermissions(map, institution) { permissions ->
            permissions.copy(readers = permissions.readers + librarianEmail)
        }
    }
    val res2 = forwarders.fold(res1)
    { accumulator: Map<BPID, InstitutionPermission>, institution ->
        constituteInstitutionBasedPermissions(
            accumulator,
            institution
        ) { permissions ->
            permissions.copy(forwarders = permissions.forwarders + librarianEmail)
        }
    }
    val res3 = maintainers.fold(res2)
    { map: Map<BPID, InstitutionPermission>, institution: BPID ->
        constituteInstitutionBasedPermissions(map, institution) { permissions ->
            permissions.copy(maintainers = permissions.maintainers + librarianEmail)
        }
    }
    return res3.values.toSet()
}

private fun constituteInstitutionBasedPermissions(
    map: Map<BPID, InstitutionPermission>,
    institution: BPID,
    institutionPermissions: (InstitutionPermission) -> InstitutionPermission
): Map<BPID, InstitutionPermission> {
    val permissions = map[institution] ?: InstitutionPermission(institution = institution)
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

data class InstitutionPermission(
    val institution: BPID,
    val readers: List<EmailAddress> = emptyList(),
    val maintainers: List<EmailAddress> = emptyList(),
    val forwarders: List<EmailAddress> = emptyList()
)