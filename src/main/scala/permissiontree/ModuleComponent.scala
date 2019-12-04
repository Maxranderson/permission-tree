package permissiontree

import scala.collection.mutable.ListBuffer

trait ModuleComponent { self: PermissionTree =>

  abstract class Module(val name: String) { main =>

    protected val root: Option[Module]
    private val subModules: ListBuffer[Module] = ListBuffer()

    private def isPackage(module: Module) =
      module.isInstanceOf[SubModule] || module.isInstanceOf[MainModule]

    private def hasPackage =
      subModules.exists(isPackage)

    protected def addModule(module: Module): Unit = {
      subModules.find(_.name == module.name).map { m =>
        throw ModuleAlreadyExistsException(m.name)
      }.getOrElse {
        if(subModules.nonEmpty) {
          if(isPackage(module)) {
            if(!hasPackage) {
              throw ModuleAlreadyHasPermissionsException(main.name)
            }
          } else if(hasPackage) {
            throw ModuleAlreadyHasSubModulesException(main.name)
          }
        }
        subModules += module
      }
    }

    override def toString: String = s"${root.map(_.toString + PERMISSION_SEPARATOR).getOrElse("")}$name"

    def modules: Seq[Module] = subModules.toList

    class SubModule(name: String) extends Module(name) { self =>
      main.addModule(self)

      override protected val root: Option[Module] = Some(main)
    }

    class Permission(name: String) extends Module(name) { self =>
      main.addModule(self)

      override def modules: Seq[Module] = Seq.empty

      override protected def addModule(module: Module): Unit = {
        throw ModuleIsPermissionException(main.name)
      }

      override protected val root: Option[Module] = Some(main)
    }

    def permissions: Seq[Module] = this +: modules.flatMap(_.permissions)
  }

}
