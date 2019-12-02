package permissiontree

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PermissionMatchersSpec extends AnyWordSpec with Matchers {

  object Permission extends PermissionTreeFixture with PermissionMatchers {
    override val APP = new MainModule {
      val SUBMODULE1 = new SubModule("SUBMODULE1") {
        val SUBMODULE1 = new SubModule("SUBMODULE1") with CrudPermissions
        val SUBMODULE2 = new SubModule("SUBMODULE2") with ReadPermission
        with CreatePermission
      }
      val SUBMODULE2 = new SubModule("SUBMODULE2") with ReadPermission
    }
  }

  "A permission" should {
    "match" when {
      "a string ends with a permission" in {
        Permission.matchPermission(
          "APP.SUBMODULE1.SUBMODULE1.READ",
          Permission.APP.SUBMODULE1.SUBMODULE1.READ
        ) should be (true)
      }
      "a string ends with a module" in {
        Permission.matchPermission(
          "APP.SUBMODULE1",
          Permission.APP.SUBMODULE1.SUBMODULE1.READ
        ) should be (true)
      }
      "a string has the WILDCARD in the middle" in {
        Permission.matchPermission(
          "APP.SUBMODULE1.**.READ",
          Permission.APP.SUBMODULE1.SUBMODULE2.READ
        ) should be (true)
      }
    }
    "not match" when {
      "a string ends with the WILDCARD" in {
        Permission.matchPermission(
          "APP.SUBMODULE1.**",
          Permission.APP.SUBMODULE1.SUBMODULE1.READ
        ) should be (false)
      }
      "a string ends with a submodule and has the WILDCARD" in {
        Permission.matchPermission(
          "APP.SUBMODULE1.**.SUBMODULE1",
          Permission.APP.SUBMODULE1.SUBMODULE1.READ
        ) should be (false)
      }
      "a permission does not exists" in {
        Permission.matchPermission(
          "APP.SUBMODULE3",
          Permission.APP.SUBMODULE2.READ
        ) should be (false)
      }
    }
  }

}
