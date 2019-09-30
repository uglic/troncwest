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

1. `Model`: ОРМ-классы (@Entity) для таблиц в базе данных (БД)

2. `Repository`:  Spring Data JPA интерфейсы (@Repository) для доступа к БД

3. `Service`: классы для работы с сервисами (@Service)

4. `dto`: классы для обмена информацией, не совпадающей по структуре с моделью  