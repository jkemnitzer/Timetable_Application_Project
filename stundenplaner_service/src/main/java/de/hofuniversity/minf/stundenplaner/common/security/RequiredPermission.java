package de.hofuniversity.minf.stundenplaner.common.security;

import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
    PermissionTypeEnum value();
}
