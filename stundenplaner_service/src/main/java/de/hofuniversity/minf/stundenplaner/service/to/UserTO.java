package de.hofuniversity.minf.stundenplaner.service.to;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTO {

    private Long id;
    private String username;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
    private Set<RoleTO> roles;

    public static UserTO fromDO(UserDO userDO) {
        return new UserTO(
                userDO.getId(),
                userDO.getUsername(),
                userDO.getTitle(),
                userDO.getFirstName(),
                userDO.getLastName(),
                userDO.getEmail(),
                userDO.getStatus().toString(),
                userDO.getRoleDOs().stream()
                        .map(RoleTO::fromDO)
                        .collect(Collectors.toSet()));
    }

}

