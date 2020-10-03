package ru.uglic.troncwest.validator;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.uglic.troncwest.model.Customer;

@Repository
public interface TestValidatedEntityRepository extends CrudRepository<TestValidatedEntity, Long> {
}
