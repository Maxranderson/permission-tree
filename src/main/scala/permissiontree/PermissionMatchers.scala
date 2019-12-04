package permissiontree

trait PermissionMatchers { self: PermissionTree =>

  private val WILDCARD = "**"
  private val WILDCARD_REGEX = "\\*\\*"
  private val BEGIN_LINE_ANCHOR = "^"
  private val END_LINE_ANCHOR = "$"
  private val MATCH_ANY = ".*"

  /**
    * Check if a permissionString match a permission.
    * There are two possibilities of permissionString.
    * If you want to match a permission or all permissions in a submodule,
    * you should use 'MainModule.SubModule.SubModule.Permission' or 'MainModule.SubModule.SubModule'.
    * If you want to match all one permission in all submodules,
    * you should use 'MainModule.**.Permission'.
    * @param permissionString
    * @param permission
    * @return True if a string matchs the permission
    */
  def matchPermission(permissionString: String,
                      permission: Module#Permission): Boolean = {
    val escapedPermissionSeparator = permissionString.toLowerCase
      .replace(PERMISSION_SEPARATOR, s"\\$PERMISSION_SEPARATOR")
    val regex =
      escapedPermissionSeparator match {
        case permString if permString.contains(WILDCARD) =>
          s"$BEGIN_LINE_ANCHOR${permString
            .split(WILDCARD_REGEX)
            .mkString(MATCH_ANY)}$END_LINE_ANCHOR".r
        case permString => s"$BEGIN_LINE_ANCHOR$permString".r
      }

    regex.findFirstMatchIn(permission.toString).isDefined
  }

}
