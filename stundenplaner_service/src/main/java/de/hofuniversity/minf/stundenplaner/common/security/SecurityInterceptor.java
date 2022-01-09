package de.hofuniversity.minf.stundenplaner.common.security;

import de.hofuniversity.minf.stundenplaner.common.exception.SimpleAuthException;
import de.hofuniversity.minf.stundenplaner.common.simpleauth.basic.BasicLoginService;
import de.hofuniversity.minf.stundenplaner.persistence.permission.data.PermissionTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.role.data.RoleTypeEnum;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.RoleService;
import de.hofuniversity.minf.stundenplaner.service.boundary.UserService;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

public class SecurityInterceptor implements HandlerInterceptor {

    @Autowired
    private BasicLoginService basicLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        UserDO userDO;
        RoleTO roleTO;
        Set<PermissionTypeEnum> permissions;
        try {
            userDO = basicLoginService.validateToken(request.getHeader("Authorization"));
            permissions = getPermissionsFromUserDO(userDO);
        } catch (SimpleAuthException ex) {
            roleTO = roleService.findByType(RoleTypeEnum.GUEST);
            permissions = roleService.getPermissionsFromRoleTO(roleTO);
        }

        // Verification permission
        if (this.hasPermission(handler, permissions)) {
            return true;
        }
        //  if there is no permissions, 403 exception SpringBoot will process, jump to / error / 403
        else {
            response.sendError(HttpStatus.FORBIDDEN.value(), "no permission");
            return false;
        }
    }

    private Set<PermissionTypeEnum> getPermissionsFromUserDO(UserDO userDO) {
        Set<PermissionTypeEnum> permissions;
        if (userDO != null) {
            permissions = userService.getPermissionsFromUserDO(userDO);
        } else {
            permissions = new HashSet<>();
        }

        return permissions;
    }

    private boolean hasPermission(Object handler, Set<PermissionTypeEnum> permissions) {
        if (handler instanceof HandlerMethod handlerMethod) {

            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);

            if (CollectionUtils.isEmpty(permissions)) {
                return false;
            }

            return permissions.contains(requiredPermission.value());
        }
        return true;
    }
}
