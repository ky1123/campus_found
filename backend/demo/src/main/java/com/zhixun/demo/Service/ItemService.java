// ItemService.java
package com.zhixun.demo.Service;

import com.zhixun.demo.Entity.Item;
import com.zhixun.demo.dto.request.CreateItemRequest;

import java.util.List;

public interface ItemService {

    Item createItem(CreateItemRequest request, Long finderUserId);

    Item getItemById(Long id);

    List<Item> getRecentItems();

    List<Item> searchItems(String keyword);

    List<Item> getItemsByUser(Long userId);

    Item updateItemStatus(Long itemId, String status);

    void deleteItem(Long itemId, Long userId);
}