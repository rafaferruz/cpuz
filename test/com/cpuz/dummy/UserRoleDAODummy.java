/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * CPUZ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CPUZ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.dummy;

import com.cpuz.DAO.UserRoleDAO;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase dummy para la ejecución tests de operaciones CRUD sobre la tabla 'userroles'.
 * 
 */
public class UserRoleDAODummy extends UserRoleDAO {

	public Map<String, List<UserRole>> getUserRoleMap(List<String> userCodes) throws SQLException {
		Map<String, List<UserRole>> userRoles = new HashMap<>();
		return userRoles;
	}
	public int deleteAllOfUser(User user) throws SQLException {
		return 0;
	}

}
