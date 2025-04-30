This is a food ordering platform built using servlets.
You would need to add MySQL Connector in the lib folder and add servlet.api from Apache Tomcat in Libararies using Configure Build Path




Create a database using MySQL Command Client

CREATE DATABASE FoodDB;

-- Switch to the fooddb database
USE fooddb;

-- Create the food_items table
CREATE TABLE food_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    itemname VARCHAR(100) NOT NULL,
    description TEXT,
    price INT NOT NULL,
    img_path VARCHAR(255)
);

-- Create the orders table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    total_price INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
