package ru.uglic.troncwest.testdata;

import org.springframework.data.repository.CrudRepository;
import ru.uglic.troncwest.AbstractSpringTest;
import ru.uglic.troncwest.model.AbstractBaseEntity;
import ru.uglic.troncwest.model.AbstractNamedEntity;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractData<T extends AbstractBaseEntity> extends AbstractSpringTest {
    private final List<T> testDataItems = new ArrayList<>();
    protected final CrudRepository<T, Long> repository;

    public AbstractData(CrudRepository<T, Long> repository) {
        this.repository = repository;
    }

    public T get(int index) {
        return testDataItems.get(index);
    }

    public void deleteAll() {
        repository.deleteAll();
        testDataItems.clear();
    }

    void add(T testDataItem) {
        testDataItems.add(repository.save(testDataItem));
    }

    abstract void addData();

    static <T extends AbstractNamedEntity> T createNamed(T entity, Long id, String name) {
        entity.setId(id);
        entity.setName(name);
        return entity;
    }
}
