package permissiontree

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ModuleSpec extends AnyWordSpec with Matchers {

  "A Module" when {
    "already has submodules" should {
      "not accept permissions" in {
        assertThrows[ModuleAlreadyHasSubModulesException] {
          new PermissionTreeFixture with PermissionsModule {
            val APP = new MainModule {
              val SUBMODULE = new SubModule("SUBMODULE") {
                val ANOTHER_SUBMODULE = new SubModule("ANOTHER_SUBMODULE")
                val READ = new Permission("READ")
              }
            }
          }
        }
      }
    }
    "already has permissions" should {
      "not accept submodules" in {
        assertThrows[ModuleAlreadyHasPermissionsException] {
          new PermissionTreeFixture with PermissionsModule {
            val APP = new MainModule {
              val SUBMODULE = new SubModule("SUBMODULE") with CrudPermissions {
                val ANOTHER_SUBMODULE = new SubModule("ANOTHER_SUBMODULE")
              }
            }
          }
        }
      }
    }
    "is a permission" should {
      "not accept submodules" in {
        assertThrows[ModuleIsPermissionException] {
          new PermissionTreeFixture with PermissionsModule {
            val APP = new MainModule {
              val SUBMODULE = new SubModule("SUBMODULE") {
                val READ = new Permission("READ") {
                  val ANOTHER_SUBMODULE = new SubModule("ANOTHER_SUBMODULE")
                }
              }
            }
          }
        }
      }
    }
    "has modules" should {
      "not accept modules with same name" in {
        assertThrows[ModuleAlreadyExistsException]{
          new PermissionTreeFixture with PermissionsModule {
            val APP = new MainModule {
              val SUBMODULE = new SubModule("SUBMODULE")
              val SUBMODULE2 = new SubModule("SUBMODULE")
            }
          }
        }
      }
    }
  }

  "A module" can {
    "be created" in {
      noException should be thrownBy {
        new PermissionTreeFixture {
          override val APP = new MainModule {
            val SUBMODULE1 = new SubModule("SUBMODULE1") {
              val SUBMODULE1 = new SubModule("SUBMODULE1") with CrudPermissions
              val SUBMODULE2 = new SubModule("SUBMODULE2") with ReadPermission
                with CreatePermission
            }
          }
        }
      }
    }
  }

}
