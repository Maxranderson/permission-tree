package permissiontree

trait PermissionTree extends ModuleComponent {

  final protected val PERMISSION_SEPARATOR: String = "."

  class MainModule extends Module("APP") {
    protected val root = None
  }

  val APP: MainModule

  def permissions: Seq[Module] = APP.permissions

}
