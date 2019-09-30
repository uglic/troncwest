# Структура программы

## База данных

### Справочники

Справочники в минимальной конфигурации для задачи

- Товары `Goods`: 

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

- Остатки товаров на складе `StockGoodRemains` 

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для 
|`goods_id`|`long`| -> `Goods.id`| Код товара
|`stock_id`|`long`| -> `Stocks.id`| Код склада
|`quantity`|`long`|-| Количество товара в минимальных единицах

unique index: `good_stock_idx`=(`goods_id`, `stock_id`) 

- Резервы товаров для клиентов `StockCustomerReservedGoods`

|name|type|index|description
|---|---|---|---|
|`id`|`long`|`unique`| Код для связи
|`goods_id`|`long`| -> `Goods.id`| Код товара
|`stock_id`|`long`| -> `Stocks.id`| Код склада
|`customer_id`|`long`| -> `Customers.id`| Код клиента
|`quantity`|`long`|-| Зарезервированное количество товара в минимальных единицах

unique index: `good_stock_customer_idx`=(`goods_id`, `stock_id`, `customer_id`)

## Структура классов

1. `Model`: ОРМ-классы (@Entity) для таблиц в базе данных (БД)

2. `Repository`:  Spring Data JPA интерфейсы (@Repository) для доступа к БД

3. `Service`: классы для работы с сервисами (@Service)

4. `dto`: классы для обмена информацией, не совпадающей по структуре с моделью  