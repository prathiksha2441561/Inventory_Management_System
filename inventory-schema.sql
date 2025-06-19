-- Create database
CREATE DATABASE IF NOT EXISTS inventory;

-- Use the database
USE inventory;

-- Create items table
CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    category VARCHAR(50) NOT NULL
);

-- Optional: Sample data
INSERT INTO items (name, quantity, category) VALUES
('Mouse', 10, 'Electronics'),
('Shampoo', 25, 'Grocery'),
('Jeans', 15, 'Clothing');
