package com.grocery.booking.dto.grocery.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItem
{
    private String name;
    private String category;
    private double price;
    private int quantity;
}
