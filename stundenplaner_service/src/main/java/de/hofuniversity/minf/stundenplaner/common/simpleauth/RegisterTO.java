package de.hofuniversity.minf.stundenplaner.common.simpleauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTO {

    String username;
    String email;
    String password;

}
