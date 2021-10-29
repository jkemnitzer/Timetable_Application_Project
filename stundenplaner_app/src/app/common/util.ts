export class Util {
  static copyObject(obj: any): any {
    return JSON.parse(JSON.stringify(obj));
  }
}
