package com.dota.hero.manager.repository;

import com.dota.hero.manager.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByName(String name);

}
