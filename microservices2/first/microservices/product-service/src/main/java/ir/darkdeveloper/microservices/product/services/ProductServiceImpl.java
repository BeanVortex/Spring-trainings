package ir.darkdeveloper.microservices.product.services;

import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservice.api.core.product.ProductService;
import ir.darkdeveloper.microservice.api.exceptions.InvalidInputException;
import ir.darkdeveloper.microservice.util.http.ServiceUtil;
import ir.darkdeveloper.microservices.product.persistence.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

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
    public Mono<Product> getProduct(Integer productId) {

        if (productId < 1)
            throw new InvalidInputException("Invalid productId: " + productId);


        LOG.info("Will get product info for id={}", productId);


        return repo.findByProductId(productId)
                .log(LOG.getName(), Level.FINE)
                .map(mapper::entityToApi)
                .map(p -> new Product(p.productId(), p.name(), p.weight(), serviceUtil.getServiceAddress()));

    }

    @Override
    public Mono<Product> createProduct(Product body) {
        var entity = mapper.apiToEntity(body);
        return repo.save(entity)
                .log(LOG.getName(), Level.FINE)
                .onErrorMap(DuplicateKeyException.class,
                        e -> new InvalidInputException("Duplicate key, Product Id: " + body.productId()))
                .map(mapper::entityToApi);
    }


    @Override
    public Mono<Void> deleteProduct(Integer productId) {
        if (productId < 1)
            throw new InvalidInputException("Invalid productId: " + productId);

        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);

        return repo.findByProductId(productId)
                .log(LOG.getName(), Level.FINE)
                .map(repo::delete)
                .flatMap(e -> e);
    }


}
