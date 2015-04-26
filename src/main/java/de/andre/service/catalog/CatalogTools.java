package de.andre.service.catalog;

import de.andre.entity.catalog.DcsCategory;
import de.andre.entity.catalog.DcsProduct;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by andreika on 4/20/2015.
 */
public interface CatalogTools {
	void populateCategoryMap(String catId, ModelAndView mav);

	List<DcsCategory> getRootChildCategories();

	DcsProduct getProductById(Integer prdId);
}
