/**
 * Author(s): Jan Gaida
 * Package(s): #6234
 */

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap} from "@angular/router";
import {GuardReason, GuardReasonKey} from "../data/guard-reason";

@Component({
  selector: 'app-guard-view',
  templateUrl: './guard-view.component.html',
  styleUrls: ['./guard-view.component.css']
})
export class GuardViewComponent implements OnInit {

  // the reason why this view is/will be shown
  reason: GuardReason = GuardReason.UNKNOWN
  // a reference to the GuardReason-enum to access in the html-file
  GuardReason = GuardReason;

  /**
   * Constructor
   *
   * @param route a reference to the activated route
   */
  constructor(
    private route: ActivatedRoute
  ) { }

  /** ALC **/

  ngOnInit(): void {
    this.reason = this.getGuardReason(this.route.snapshot.paramMap);
  }

  /** Functions **/

  /**
   * Reads the reason from the passed Route-Snapshot-ParamMap
   *
   * @param paramMap the snapshot-paramMap to parse from
   */
  getGuardReason(paramMap: ParamMap): GuardReason {
    if (paramMap.has(GuardReasonKey.key)) {
      try {
        // @ts-ignore; we will catch it if this doesn't work:
        return GuardReason[paramMap.get(GuardReasonKey.key)]
      }
      catch (error) {
        console.log('Failed parsing the GuardReason-Enum from the given ParamMap.')
      }
    }
    // by-default
    return GuardReason.UNKNOWN
  }

  /**
   * Checks if given reason matches with the reason set to this
   *
   * @param reason the reason to check against
   *
   * @return boolean whether the given reason matches with this
   */
  isGuardReasonType(reason: any): boolean {
    return GuardReason[this.reason] == reason;
  }
}
