package de.andre.repository.catalog;

import de.andre.repository.RepositoryAdapter;

public class ProductCatalog extends RepositoryAdapter {
    private final CatalogRepository catalogRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductCatalog(final CatalogRepository catalogRepository, final CategoryRepository categoryRepository,
            final ProductRepository productRepository) {
        super(catalogRepository, categoryRepository, productRepository);
        this.catalogRepository = catalogRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public CatalogRepository getCatalogRepository() {
        return catalogRepository;
    }

    public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }
}
