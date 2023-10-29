package com.dilip.orderservice.service;

import com.dilip.orderservice.dto.InventoryResponse;
import com.dilip.orderservice.dto.OrderLineItemsDto;
import com.dilip.orderservice.dto.OrderRequest;
import com.dilip.orderservice.event.OrderPlacedEvent;
import com.dilip.orderservice.model.Order;
import com.dilip.orderservice.model.OrderLineItems;
import com.dilip.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAutoConfiguration
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

/***
 * commented as I used RequiredArgsConstructor
 *
 * public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }*/

    public String placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        // Call inventory service and place the order if product is in stock
        InventoryResponse[] inventoryResponsesArray = webClient.get()
                // rather than making http call for every product, go with one call. For that make changes in InventoryController to take List of arguments
                .uri("http://localhost:8082/api/inventory"
                ,uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)// to read it we have to make the body to mono
                .block();// to make synchronous call as webclient makes async calls

        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "Order Placed Successfully";
        }else {
            throw new IllegalAccessException("Product is not in stock, please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;

    }
}
