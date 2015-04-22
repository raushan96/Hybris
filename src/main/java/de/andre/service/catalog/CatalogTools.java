package de.andre.service.catalog;

import de.andre.entity.catalog.DcsCategory;
import de.andre.entity.catalog.DcsProduct;

import java.util.List;

/**
 * Created by andreika on 4/20/2015.
 */
public interface CatalogTools {
	List<DcsProduct> getCatalogProducts(String catId);

	List<DcsCategory> getRootChildCategories();
}
