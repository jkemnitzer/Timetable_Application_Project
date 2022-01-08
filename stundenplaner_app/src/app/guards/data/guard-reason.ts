/**
 * Author(s): Jan Gaida
 * Package(s): #6234
 */

/**
 * The enum defining the reason why the guard-view is shown.
 */
export enum GuardReason {
  UNKNOWN,
  MUST_BE_LOGGED_IN,
  MUST_HAVE_PERMISSION
}

/**
 * Container for the static key-variable of the param-map when the guard view is shown,
 * where the value should be the GuardReason.
 */
export class GuardReasonKey{
  static key: string = 'reason';
}
