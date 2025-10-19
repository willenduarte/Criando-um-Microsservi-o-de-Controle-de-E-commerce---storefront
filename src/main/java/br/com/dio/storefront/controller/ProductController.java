package br.com.dio.storefront.controller;

import br.com.dio.storefront.controller.request.ProductSaveRequest;
import br.com.dio.storefront.controller.response.ProductAvailableResponse;
import br.com.dio.storefront.controller.response.ProductDetailResponse;
import br.com.dio.storefront.controller.response.ProductSavedResponse;
import br.com.dio.storefront.mapper.IProductMapper;
import br.com.dio.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private final IProductService service;
    private final IProductMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    ProductSavedResponse create(@RequestBody final ProductSaveRequest request){
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toResponse(entity);
    }

    @PostMapping("{id}/purchase")
    @ResponseStatus(NO_CONTENT)
    void purchase(@PathVariable final UUID id){
        service.purchase(id);
    }

    @GetMapping
    List<ProductAvailableResponse> listAvailable(){
        var entities = service.findAllActive();
        return mapper.toResponse(entities);
    }

    @GetMapping("{id}")
    ProductDetailResponse findById(@PathVariable final UUID id){
        var dto = service.findInfo(id);
        return mapper.toResponse(dto);
    }

}
