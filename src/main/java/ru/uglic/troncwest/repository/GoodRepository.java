package ru.uglic.troncwest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.Good;

@Repository
public interface GoodRepository extends CrudRepository<Good, Long> {
}
