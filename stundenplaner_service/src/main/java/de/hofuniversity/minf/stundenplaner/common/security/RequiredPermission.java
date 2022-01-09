package de.hofuniversity.minf.stundenplaner.common.security;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
    PermissionTypeEnum value();
}
