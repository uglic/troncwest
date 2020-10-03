package ru.uglic.troncwest.validator;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TestValidatedEntityService {
    private final TestValidatedEntityRepository repository;

    public TestValidatedEntityService(TestValidatedEntityRepository repository) {
        this.repository = repository;
    }

    public TestValidatedEntity save(TestValidatedEntity entity) {
        return repository.save(entity);
    }

    public void delete(TestValidatedEntity entity) {
        repository.delete(entity);
    }

    public List<TestValidatedEntity> getAll() {
        List<TestValidatedEntity> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
}
