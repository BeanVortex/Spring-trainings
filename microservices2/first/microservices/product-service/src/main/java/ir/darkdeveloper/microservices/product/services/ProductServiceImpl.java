package ir.darkdeveloper.microservices.product.services;

import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.product.ProductService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.api.exceptions.NotFoundException;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import ir.darkdeveloper.microservices.product.persistence.ProductEntity;
import ir.darkdeveloper.microservices.product.persistence.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil serviceUtil;
    private final ProductRepo repo;
    private final ProductMapper mapper;

    @Autowired
    public ProductServiceImpl(ServiceUtil serviceUtil, ProductRepo repo, ProductMapper mapper) {
        this.serviceUtil = serviceUtil;
        this.repo = repo;
        this.mapper = mapper;
    }


    @Override
    public Product getProduct(Integer productId) {

        if (productId < 1) {
            throw new InvalidInputException("Invalid productId: " + productId);
        }

        var entity = repo.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException("No product found for productId: " + productId));

        var response = mapper.entityToApi(entity);
        var product = new Product(productId, response.name(), response.weight(), serviceUtil.getServiceAddress());
        LOG.debug("getProduct: found productId: {}", product.productId());

        return product;
    }

    @Override
    public Product createProduct(Product body) {
        try {
            var entity = mapper.apiToEntity(body);
            var newEntity = repo.save(entity);

            LOG.debug("createProduct: entity created for productId: {}", body.productId());
            return mapper.entityToApi(newEntity);

        } catch (DuplicateKeyException dke) {
            throw new InvalidInputException("Duplicate key, Product Id: " + body.productId());
        }
    }


    @Override
    public void deleteProduct(Integer productId) {
        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
        repo.findByProductId(productId).ifPresent(repo::delete);
    }


}
