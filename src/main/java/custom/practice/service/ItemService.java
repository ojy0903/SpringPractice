package custom.practice.service;

import custom.practice.domain.Item;
import custom.practice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        Optional<Item> findItem = itemRepository.findById(itemId);
        if (findItem.isPresent()) {
            return findItem.get();
        } else {
            throw new IllegalStateException("해당 Id의 회원은 존재하지 않습니다");
        }
    }


    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item;
        Optional<Item> byId = itemRepository.findById(itemId);
        if (byId.isPresent()) {
            item = byId.get();
        } else {
            throw new IllegalStateException("해당 id의 item 존재하지 않습니다");
        }

        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }
}
