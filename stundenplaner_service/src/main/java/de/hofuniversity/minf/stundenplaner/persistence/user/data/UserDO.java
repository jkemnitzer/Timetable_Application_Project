package de.hofuniversity.minf.stundenplaner.persistence.user.data;

import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleDO;
import de.hofuniversity.minf.stundenplaner.service.to.UserTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mbaecker
 * <p>
 * Basic POJO class for table representation of user
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class UserDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_user_seq")
    @SequenceGenerator(name = "t_user_seq", sequenceName = "t_user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "password_salt")
    private String passwordSalt;

    @Column(name = "email")
    private String email;

    @Column(name = "created", columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    @Column(name = "last_updated", columnDefinition = "TIMESTAMP")
    private LocalDateTime lastUpdated;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_user_role_map", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleDO> roleDOs;

    public void updateFromTO(UserTO userTO) {
        this.setUsername(userTO.getUsername());
        this.setEmail(userTO.getEmail());
        this.setStatus(StatusEnum.valueOf(userTO.getStatus()));
        this.setLastUpdated(LocalDateTime.now());
    }

    public static UserDO fromTO(UserTO userTO) {
        return new UserDO(
                userTO.getId(),
                userTO.getUsername(),
                null,
                null,
                userTO.getEmail(),
                null,
                null,
                StatusEnum.valueOf(userTO.getStatus()),
                userTO.getRoles().stream()
                        .map(RoleDO::fromTO)
                        .collect(Collectors.toSet())

        );
    }
}
