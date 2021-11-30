/**
 * Author(s): Jan Gaida
 * Package(s): #6145
 */

import {UserRoleType} from "./user-role-type";
import {Permission} from "./permission";

/**
 * A specific user-role
 */
export interface UserRole {
  id: bigint;
  type: UserRoleType;
  permissions: Array<Permission>;
}
