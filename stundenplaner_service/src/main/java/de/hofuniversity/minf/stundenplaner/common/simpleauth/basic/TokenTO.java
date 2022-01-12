package de.hofuniversity.minf.stundenplaner.common.simpleauth.basic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenTO {

    private String token;
    private LocalDateTime validTo;

    public static TokenTO fromDO(TokenDO tokenDO) {
        return new TokenTO(
                tokenDO.getToken(), tokenDO.getValidTo()
        );
    }

}
