package de.hofuniversity.minf.stundenplaner.common.simpleauth.basic;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_token")
public class TokenDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "t_token_seq")
    @SequenceGenerator(name = "t_token_seq", sequenceName = "t_token_seq", allocationSize = 1)
    private Long id;
    @Column(name = "token")
    private String token;
    @Column(name = "valid_to")
    private LocalDateTime validTo;

    @OneToOne(optional = false)
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private UserDO userDO;

}
