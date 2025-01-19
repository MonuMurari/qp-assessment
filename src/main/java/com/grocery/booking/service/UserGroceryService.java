package com.grocery.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grocery.booking.dto.cartOrder.projection.GroceryOrderView;
import com.grocery.booking.dto.cartOrder.request.GroceryOrder;
import com.grocery.booking.dto.cartOrder.response.GroceryOrderResponse;
import com.grocery.booking.dto.grocery.projection.UserGroceryItems;
import com.grocery.booking.repository.GroceryItemViewRepository;
import com.grocery.booking.repository.GroceryOrderViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserGroceryService
{
    @Autowired
    private GroceryOrderViewRepository groceryOrderViewRepository;

    @Autowired
    private GroceryItemViewRepository groceryItemViewRepository;

    public GroceryOrderView saveGroceryOrder(List<GroceryOrder> groceryOrders)
    {
        log.info("Inside saveGroceryItems");

        try {
            ObjectMapper objectMapper=new ObjectMapper();

            String groceryItems=objectMapper.writeValueAsString(groceryOrders);
            GroceryOrderView groceryOrderView=GroceryOrderView.builder()
                    .id(UUID.randomUUID().toString())
                    .groceryOrders(groceryItems)
                    .build();
            return groceryOrderViewRepository.save(groceryOrderView);
        }
        catch (Exception e)
        {
            log.error("ERROR:: Failed to save Grocery order with errorMessage: {}",e.getMessage());
        }
        return null;
    }

    public List<UserGroceryItems> fetchGroceryItems()
    {
        log.info("Inside fetchGroceryItems");
        try {

            return groceryItemViewRepository.getGroceryDetailsForUser();
        }
        catch (Exception e)
        {
            log.error("ERROR:: Failed to fetch GroceryItems with errorMessage: {}",e.getMessage());
        }
        return Collections.emptyList();

    }

    public GroceryOrderResponse fetchOrderDetails(String id)
    {
        log.info("Inside fetchOrderDetails for orderId : {}",id);
        try
        {
            Optional<GroceryOrderView> optionalGroceryOrderView = groceryOrderViewRepository.findById(id);
            if (optionalGroceryOrderView.isPresent())
            {
                GroceryOrderView groceryOrderView = optionalGroceryOrderView.get();
                ObjectMapper objectMapper=new ObjectMapper();
                List<GroceryOrder> groceryOrders = objectMapper.readValue(groceryOrderView.getGroceryOrders(), new TypeReference<List<GroceryOrder>>() {});
                if (!CollectionUtils.isEmpty(groceryOrders))
                {
                    return GroceryOrderResponse.builder()
                            .id(groceryOrderView.getId())
                            .groceryOrders(groceryOrders)
                            .build();
                }
                else {
                    log.info("Failed to fetchOrder by OrderId : {} groceryItems is NULL",id);
                }
            }
            else {
                log.info("Failed to fetchOrder by OrderId : {}",id);
            }
        }
        catch (Exception e)
        {
            log.error("ERROR:: Failed to fetch order details for id : {} with errorMessage: {}",id,e.getMessage());
        }
        return null;
    }
}
