package permissiontree

class PermissionTreeException(msg: String) extends Exception(msg)

case class ModuleAlreadyExistsException(name: String)
    extends PermissionTreeException(s"Module with name $name already exists")

case class ModuleAlreadyHasPermissionsException(mainModule: String)
    extends PermissionTreeException(
      s"Module $mainModule already has permissions"
    )

case class ModuleAlreadyHasSubModulesException(mainModule: String)
  extends PermissionTreeException(
    s"Module $mainModule already has submodules"
  )

case class ModuleIsPermissionException(mainModule: String)
  extends PermissionTreeException(
    s"Module $mainModule already has submodules"
  )
