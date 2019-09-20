import scala.collection.mutable.ListBuffer

object Main extends App {

  trait PermissionTree {

    abstract class Module(val id: Int, name: String) { main =>
      private val PERMISSION_SEPARATOR = "."
      protected val root: Option[Module]
      private val subModules: ListBuffer[Module] = ListBuffer()

      private def addModule(module: Module): Unit = {
        subModules.find(_.id == module.id).map { m =>
          throw new Exception(s"Can't add $module, because exists one module with same id: $m")
        }.getOrElse {
          subModules += module
        }
      }

      override def toString: String = s"${root.map(_.toString + PERMISSION_SEPARATOR).getOrElse("")}$id-$name"

      def modules: Seq[Module] = subModules.toList

      class SubModule(id: Int, name: String) extends Module(id, name) { self =>
        addModule(self)

        override protected val root: Option[Module] = Some(main)
      }

      class Permission(id: Int, name: String) extends Module(id, name) { self =>
        addModule(self)

        override def modules: Seq[Module] = Seq.empty

        override protected val root: Option[Module] = Some(main)
      }

      def permissions: Seq[Module] = this +: modules.flatMap(_.permissions)
    }

    class MainModule(name: String) extends Module(1, name) {
      protected val root = None
    }

    val APP: MainModule

    def permissions: Seq[Module] = APP.permissions

  }

  object Permissions extends PermissionTree {
    val APP = new MainModule("APP") {
     val IMPORTACAO = new SubModule(1,"IMPORTACAO") {
       val LIST = new Permission(1, "LIST")
       val UPDATE = new Permission(2, "UPDATE")
       val CREATE = new Permission(3, "CREATE")
       val DELETE = new Permission(4, "DELETE")
     }
     val CONSULTAS = new SubModule(2, "CONSULTAS") {
       val LIST = new Permission(1, "LIST")
       val UPDATE = new Permission(2, "UPDATE")
       val CREATE = new Permission(3, "CREATE")
       val DELETE = new Permission(4, "DELETE")
     }
    }
  }

  println(Permissions.permissions)


}
