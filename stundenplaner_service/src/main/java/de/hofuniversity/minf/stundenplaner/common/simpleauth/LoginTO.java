package de.hofuniversity.minf.stundenplaner.common.simpleauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginTO {

    String username;
    String password;

}
