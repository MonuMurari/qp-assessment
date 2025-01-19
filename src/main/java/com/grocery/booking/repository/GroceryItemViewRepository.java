package com.grocery.booking.repository;

import com.grocery.booking.dto.grocery.projection.GroceryItemView;
import com.grocery.booking.dto.grocery.projection.UserGroceryItems;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryItemViewRepository extends JpaRepository<GroceryItemView,String>
{
    @Transactional
    long deleteByName(String name);

    GroceryItemView findByName(String name);

    @Query("SELECT new com.grocery.booking.dto.grocery.projection.UserGroceryItems(gc.id, gc.name, gc.price) FROM GroceryItemView gc")
    List<UserGroceryItems> getGroceryDetailsForUser();
}
