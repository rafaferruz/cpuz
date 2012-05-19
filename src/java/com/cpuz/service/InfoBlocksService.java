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
package com.cpuz.service;

import com.cpuz.DAO.DAOFactory;
import com.cpuz.domain.InfoBlock;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class InfoBlocksService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control=new ControlParams();

    public InfoBlocksService() {
    }
    public boolean keyIdExists(int infoId) throws SQLException {
            InfoBlock infoBlock = new DAOFactory().getInfoBlockDAO().read(infoId);
		return (infoBlock != null);
    }


	public List<InfoBlock> getInfoBlockList(ControlParams control) throws SQLException  {

		List<InfoBlock> infoBlocks = new ArrayList<>();
		infoBlocks = new DAOFactory().getInfoBlockDAO().getInfoBlockList(control);
		return infoBlocks;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getInfoBlockDAO().getCountRows();
	}

	public InfoBlock getById(int infoBlockId) throws SQLException{
		return new DAOFactory().getInfoBlockDAO().read(infoBlockId);
	}

	public int insertInfoBlock(InfoBlock infoBlock) throws SQLException {
		return new DAOFactory().getInfoBlockDAO().create(infoBlock);
	}

	public int updateInfoBlock(InfoBlock infoBlock) throws SQLException{
		return new DAOFactory().getInfoBlockDAO().update(infoBlock);
	}

	public int deleteInfoBlock(InfoBlock infoBlock) throws SQLException {
		return new DAOFactory().getInfoBlockDAO().delete(infoBlock.getId());
	}

	public int deleteInfoBlockIds(List<Integer> ids) throws SQLException {
		return new DAOFactory().getInfoBlockDAO().deleteIds(ids);
	}


}
