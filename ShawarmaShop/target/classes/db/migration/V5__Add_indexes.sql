-- Индексы для оптимизации запросов к базе данных

-- Индекс для быстрого поиска пользователей по email
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);

-- Индекс для быстрого поиска заказов по пользователю
CREATE INDEX IF NOT EXISTS idx_orders_user_id ON orders(user_id);

-- Индекс для быстрого поиска шаурмы по категории
CREATE INDEX IF NOT EXISTS idx_shawarmas_category ON shawarmas(category);

