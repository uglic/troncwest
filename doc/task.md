# Структура программы

## База данных

### Справочники

Справочники в минимальной конфигурации для задачи

- Товары `Products`: 

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для связи
|`name`|`String`|`unique`| Видимое имя
   
- Склады `Stocks`:

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для связи
|`name`|`String`|`unique`| Видимое имя

- Клиенты `Customers`: 

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для связи
|`name`|`String`|`unique`| Видимое имя

### Основные таблицы

- Остатки товаров на складе `StockProductRemainders` 

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для 
|`product_id`|`long`| -> `Products.id`| Код товара
|`stock_id`|`long`| -> `Stocks.id`| Код склада
|`quantity`|`long`|-| Количество товара в минимальных единицах

unique index: `stock_product_idx`=(`stock_id`, `product_id`) 

- Резервы товаров для клиентов `StockReservedProductRemainders`

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для связи
|`productid`|`long`| -> `Products.id`| Код товара
|`stock_id`|`long`| -> `Stocks.id`| Код склада
|`customer_id`|`long`| -> `Customers.id`| Код клиента
|`quantity`|`long`|-| Зарезервированное количество товара в минимальных единицах

unique index: `stock_product_customer_idx`=(`stock_id`, `product_id`, `customer_id`)

## Структура классов

1. `model`: ОРМ-классы (@Entity) для таблиц в базе данных (БД)

2. `repository`:  Spring Data JPA интерфейсы (@Repository) для доступа к БД

3. `service`: классы для работы с сервисами (@Service)

4. `dto`: классы для обмена информацией, не совпадающей по структуре с моделью

   `dto\in`: классы для входящей информации

5. `exception`: пользовательские исключения

6. `web`: REST-контроллеры   

## Примеры

* Свободный баланс (остаток на складе - резервы) для связки товар-склад 
```
curl -v -X GET http://localhost:8080/remainders/free?productId=4922\&stockId=4926
# ответ
87
```

* Остаток на складе для связки товар-склад
```
curl -v -X GET http://localhost:8080/remainders/full?productId=4922\&stockId=4926
# ответ
91
```

* Резервирование товара для связки товар-склад-пользователь
```
curl -v -X PUT http://localhost:8080/remainders/reserve/add -H "Content-Type: application/json" -d '{"productId": 4922, "stockId": 4928, "customerId": 4919, "quantity": 12}'
# ответ: код возврата
HTTP/1.1 204 
```

* Снятие резервирования товара для связки товар-склад-пользователь
```
curl -v -X PUT http://localhost:8080/remainders/reserve/free -H "Content-Type: application/json" -d '{"productId": 4922, "stockId": 4928, "customerId": 4919, "quantity": 12}'
# ответ: код возврата
HTTP/1.1 204 
```

* Список резервов конкретного пользователя в виде товар-склад-количество
```
curl -v -X GET http://localhost:8080/remainders/by-customer/4918

# ответ
[
  {
    "product": {"id":4922,"name":"Product 4"},
    "stock":{"id":4926,"name":"Stock 1"},
    "quantity":1
  },
  {
    "product":{"id":4923,"name":"Product 1"},
    "stock":{"id":4927,"name":"Stock 3"},
    "quantity":2
  },
  {
    "product":{"id":4924,"name":"Product 2"},
    "stock":{"id":4928,"name":"Stock 2"},
    "quantity":3
  }
]
```
