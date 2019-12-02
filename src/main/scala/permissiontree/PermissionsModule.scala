package permissiontree

trait PermissionsModule { self: PermissionTree =>

  trait ReadPermission { self: Module#SubModule =>
    val READ = new Permission("READ")
  }

  trait UpdatePermission { self: Module#SubModule =>
    val UPDATE = new Permission("UPDATE")
  }

  trait CreatePermission { self: Module#SubModule =>
    val CREATE = new Permission("CREATE")
  }

  trait DeletePermission { self: Module#SubModule =>
    val DELETE = new Permission("DELETE")
  }

  trait CrudPermissions
      extends ReadPermission
      with CreatePermission
      with DeletePermission
      with UpdatePermission { self: Module#SubModule =>
  }

}
