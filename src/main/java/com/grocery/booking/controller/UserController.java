package com.grocery.booking.controller;

import com.grocery.booking.dto.cartOrder.projection.GroceryOrderView;
import com.grocery.booking.dto.cartOrder.request.GroceryOrder;
import com.grocery.booking.dto.cartOrder.response.GroceryOrderResponse;
import com.grocery.booking.dto.grocery.projection.UserGroceryItems;
import com.grocery.booking.service.UserGroceryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController
{

    @Autowired
    private UserGroceryService userGroceryService;

    @PostMapping("/addGroceryItemToCart")
    public ResponseEntity<GroceryOrderView> addGroceryItemToCart(@RequestBody List<GroceryOrder> groceryOrders)
    {
        log.info("Inside addGroceryItemToCart");
        if (!CollectionUtils.isEmpty(groceryOrders)) {
            GroceryOrderView groceryOrder = userGroceryService.saveGroceryOrder(groceryOrders);
            if (!ObjectUtils.isEmpty(groceryOrder)) {
                log.info("Successfully saved grocery order");
                return new ResponseEntity<>(groceryOrder, HttpStatus.CREATED);
            }
            log.info("Failed to save grocery order");
            return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            log.info("Cannot save order with empty cart items");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllGroceryItems")
    public ResponseEntity<List<UserGroceryItems>> getAllGroceryItems()
    {
        log.info("Inside getAllGroceryItems");
        List<UserGroceryItems> userGroceryItems = userGroceryService.fetchGroceryItems();
        if (!CollectionUtils.isEmpty(userGroceryItems))
        {
            log.info("Successfully fetched grocery items");
            return new ResponseEntity<>(userGroceryItems, HttpStatus.OK);
        }
        log.info("Failed to fetch grocery items");
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/getOrderDetail/{id}")
    public ResponseEntity<GroceryOrderResponse> getOrderDetail(@PathVariable(value = "id") String id)
    {
        log.info("Inside getOrderDetail for fetch order by ID : {}",id);
        GroceryOrderResponse groceryOrderResponse = userGroceryService.fetchOrderDetails(id);
        if (!ObjectUtils.isEmpty(groceryOrderResponse))
        {
            log.info("Successfully fetched grocery order by orderId : {}",id);
            return new ResponseEntity<>(groceryOrderResponse, HttpStatus.OK);
        }
        log.info("Failed to fetch grocery order by Id : {}",id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
