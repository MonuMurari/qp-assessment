package com.grocery.booking.controller;

import com.grocery.booking.dto.grocery.projection.GroceryItemView;
import com.grocery.booking.dto.grocery.request.GroceryItem;
import com.grocery.booking.dto.grocery.request.UpdateGroceryItem;
import com.grocery.booking.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController
{

    @Autowired
    private AdminService adminService;

    @PostMapping("/addGroceryItems")
    public ResponseEntity<List<GroceryItemView>> addGroceryItem(@RequestBody List<GroceryItem> groceryItems)
    {
        log.info("Inside addGroceryItem");
        List<GroceryItemView> groceryItemView = adminService.saveGroceryItems(groceryItems);
        if (!CollectionUtils.isEmpty(groceryItemView))
        {
            log.info("Successfully added grocery items");
            return new ResponseEntity<>(groceryItemView, HttpStatus.CREATED);
        }
        log.info("Failed to add grocery items");
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @PatchMapping("/updateGroceryItem")
    public ResponseEntity<GroceryItemView> updateGroceryItem(@RequestParam(name = "name",required = true) String name,
                                                                       @RequestBody UpdateGroceryItem updateGroceryItem)
    {
        log.info("Inside updateGroceryItem for groceryItem : {}",name);
        GroceryItemView groceryItemView = adminService.updateGroceryItems(name,updateGroceryItem);
        if (!ObjectUtils.isEmpty(groceryItemView))
        {
            log.info("Successfully updated grocery items details");
            return new ResponseEntity<>(groceryItemView, HttpStatus.ACCEPTED);
        }
        log.info("Failed to update grocery items details");
        return new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @GetMapping("/getAllGroceryItems")
    public ResponseEntity<List<GroceryItemView>> getAllGroceryItems()
    {
        log.info("Inside getAllGroceryItems");
        List<GroceryItemView> groceryItemView = adminService.fetchGroceryItems();
        if (!CollectionUtils.isEmpty(groceryItemView))
        {
            log.info("Successfully fetched grocery items");
            return new ResponseEntity<>(groceryItemView, HttpStatus.OK);
        }
        log.info("Failed to fetch grocery items");
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping("/deleteGroceryItem")
    public ResponseEntity<String> deleteGroceryItem(@RequestParam(name = "name", required = false) String name)
    {
        String responseMessage;
        if(!StringUtils.hasLength(name))
        {
            responseMessage="ItemName should be present to remove Item";
            log.info(responseMessage);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
        log.info("Inside deleteGroceryItem for  name :{}",name);
        responseMessage = adminService.removeGroceryItem(name);
        return new ResponseEntity<>(responseMessage, HttpStatus.ACCEPTED);
    }
}
