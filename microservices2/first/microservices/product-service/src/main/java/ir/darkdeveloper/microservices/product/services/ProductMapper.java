package ir.darkdeveloper.microservices.product.services;

import ir.darkdeveloper.microservice.api.core.product.Product;
import ir.darkdeveloper.microservices.product.persistence.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "serviceAddress", ignore = true),
            @Mapping(target = "productId", expression = "java(entity.getProductId())"),
            @Mapping(target = "name",  expression = "java(entity.getName())"),
            @Mapping(target = "weight",  expression = "java(entity.getWeight())")
    })
    Product entityToApi(ProductEntity entity);

//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(target = "version", ignore = true)
//    })
    ProductEntity apiToEntity(Product api);

}
