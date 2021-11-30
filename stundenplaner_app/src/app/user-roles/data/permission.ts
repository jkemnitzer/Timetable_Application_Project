/**
 * Author(s): Jan Gaida
 * Package(s): #6145
 */

import {PermissionTypes} from "./permission-types";

/**
 * A specific permission
 */
export interface Permission{
  id: bigint;
  type: PermissionTypes
}
