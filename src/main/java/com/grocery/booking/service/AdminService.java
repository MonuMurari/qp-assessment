package com.grocery.booking.service;

import com.grocery.booking.dto.grocery.projection.GroceryItemView;
import com.grocery.booking.dto.grocery.request.GroceryItem;
import com.grocery.booking.dto.grocery.request.UpdateGroceryItem;
import com.grocery.booking.repository.GroceryItemViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AdminService
{
    @Autowired
    private GroceryItemViewRepository groceryItemViewRepository;

    public List<GroceryItemView> saveGroceryItems(List<GroceryItem> groceryItems)
    {
        log.info("Inside saveGroceryItems");
        List<GroceryItemView> groceryItemViews=new ArrayList<>();
        try {
            groceryItems.stream().forEach(groceryItem -> {
                GroceryItemView groceryItemView = GroceryItemView.builder()
                        .id(UUID.randomUUID().toString())
                        .name(groceryItem.getName())
                        .category(groceryItem.getCategory())
                        .price(groceryItem.getPrice())
                        .quantity(groceryItem.getQuantity())
                        .build();
                log.info("Saving groceryItem with Id: {}",groceryItemView.getId());
                GroceryItemView saveGroceryItemView = groceryItemViewRepository.save(groceryItemView);
                if (!ObjectUtils.isEmpty(saveGroceryItemView))
                {
                    log.info("Saved groceryItem with Id: {}",groceryItemView.getId());
                }
                groceryItemViews.add(saveGroceryItemView);
            });


            return groceryItemViews;
        }
        catch (Exception e)
        {
            log.error("ERROR:: Failed to save Grocery Item with errorMessage: {}",e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<GroceryItemView> fetchGroceryItems()
    {
        log.info("Inside fetchGroceryItems");
        try {
            return groceryItemViewRepository.findAll();
        }
        catch (Exception e)
        {
            log.error("ERROR:: Failed to fetch GroceryItems : {}",e.getMessage());
        }
        return Collections.emptyList();

    }

    public String removeGroceryItem(String name)
    {
        log.info("Inside removeGroceryItem for name : {}",name);
        String responseMessage;
        try {
            long noOfGroceryItemsDeleted = groceryItemViewRepository.deleteByName(name);
            responseMessage= MessageFormat.format("{0} GroceryItem deleted by name : {1}",noOfGroceryItemsDeleted,name);
            log.info(responseMessage);
        }
        catch (Exception e)
        {
            responseMessage=MessageFormat.format("ERROR:: Failed to delete grocery item for name : {0} with errorMessage : {1}",name,e.getMessage());
            log.error(responseMessage);
        }
        return responseMessage;
    }

    public GroceryItemView updateGroceryItems(String name, UpdateGroceryItem updateGroceryItem)
    {
        log.info("Inside updateGroceryItems for name : {}",name);
        try {
            GroceryItemView groceryItemView=groceryItemViewRepository.findByName(name);
            if (!ObjectUtils.isEmpty(groceryItemView))
            {
                boolean isUpdateRequired=false;
                if (updateGroceryItem.getQuantity()>0)
                {
                    groceryItemView.setQuantity(updateGroceryItem.getQuantity());
                    isUpdateRequired=true;
                }
                if (updateGroceryItem.getPrice()>0)
                {
                    groceryItemView.setPrice(updateGroceryItem.getPrice());
                    isUpdateRequired=true;
                }
                if (StringUtils.hasLength(updateGroceryItem.getCategory()))
                {
                    groceryItemView.setCategory(updateGroceryItem.getCategory());
                    isUpdateRequired=true;
                }
                if (isUpdateRequired)
                {
                    return groceryItemViewRepository.save(groceryItemView);
                }
            }
            else {
                log.info("GroceryItem not found for update by name: {}",name);
                return null;
            }
        }
        catch (Exception e)
        {
            log.error("ERROR:: Failed to update groceryItems by name : {} with errorMessage : {}",name,e.getMessage());
        }
        return null;
    }
}
