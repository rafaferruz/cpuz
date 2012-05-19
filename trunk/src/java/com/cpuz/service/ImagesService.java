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
import com.cpuz.domain.Image;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImagesService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control=new ControlParams();

    public ImagesService() {
    }
    public boolean keyIdExists(int imageId) throws SQLException {
            Image image = new DAOFactory().getImageDAO().read(imageId);
		return (image != null);
    }


	public List<Image> getImageList(ControlParams control) throws SQLException  {

		List<Image> images = new ArrayList<>();
		images = new DAOFactory().getImageDAO().getImageList(control);
		return images;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getImageDAO().getCountRows();
	}

	public Image getById(int imageId) throws SQLException{
		return new DAOFactory().getImageDAO().read(imageId);
	}

	public int insertImage(Image image) throws SQLException {
		return new DAOFactory().getImageDAO().create(image);
	}

	public int updateImage(Image image) throws SQLException{
		return new DAOFactory().getImageDAO().update(image);
	}

	public int deleteImage(Image image) throws SQLException {
		return new DAOFactory().getImageDAO().delete(image.getId());
	}

	public int deleteImageIds(List<Integer> ids) throws SQLException {
		return new DAOFactory().getImageDAO().deleteIds(ids);
	}
}





