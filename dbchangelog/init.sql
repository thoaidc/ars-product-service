CREATE DATABASE IF NOT EXISTS `ars_product` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ars_product`;

SET FOREIGN_KEY_CHECKS = 0;

-- ============================
-- TABLE: shop
-- ============================
DROP TABLE IF EXISTS shop;
CREATE TABLE shop (
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    email VARCHAR(255),
    phone VARCHAR(50),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    total_sales INT DEFAULT 0,
    created_by VARCHAR(50),
    last_modified_by VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================
-- TABLE: voucher
-- ============================
DROP TABLE IF EXISTS voucher;
CREATE TABLE voucher (
    id INT AUTO_INCREMENT PRIMARY KEY,
    shop_id INT NOT NULL,
    type INT NOT NULL, -- 1: by value, 2: by percentage
    status INT NOT NULL, -- 1: active, 2: inactive
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    date_started INT,
    date_expired INT,
    value DECIMAL(21,6) DEFAULT 0.00 NOT NULL,
    created_by VARCHAR(50),
    last_modified_by VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- ============================
-- TABLE: product
-- ============================
DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    shop_id            INT            NOT NULL,
    name               VARCHAR(255)   NOT NULL,
    code               VARCHAR(50)    NOT NULL UNIQUE,
    price              DECIMAL(21, 6) NOT NULL,
    description        VARCHAR(1000),
    is_customizable    BOOLEAN        NOT NULL DEFAULT FALSE,
    status             VARCHAR(20)    NOT NULL,
    thumbnail_url      VARCHAR(255),
    original_image     VARCHAR(255),
    total_sales        INT NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_image
-- ============================
DROP TABLE IF EXISTS product_image;
CREATE TABLE product_image
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    product_id  INT NOT NULL,
    image              VARCHAR(255),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_product_image
        FOREIGN KEY (product_id) REFERENCES product (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_group
-- ============================
DROP TABLE IF EXISTS product_group;
CREATE TABLE product_group
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    shop_id            INT          NOT NULL,
    name               VARCHAR(200) NOT NULL,
    description        VARCHAR(1000),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_product_group
-- ============================
DROP TABLE IF EXISTS product_product_group;
CREATE TABLE product_product_group
(
    id               INT PRIMARY KEY AUTO_INCREMENT,
    product_id       INT NOT NULL,
    product_group_id INT NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_ppg_product
        FOREIGN KEY (product_id) REFERENCES product (id)
            ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_ppg_group
        FOREIGN KEY (product_group_id) REFERENCES product_group (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_ppg_product ON product_product_group (product_id);
CREATE INDEX idx_ppg_group ON product_product_group (product_group_id);


-- ============================
-- TABLE: category
-- ============================
DROP TABLE IF EXISTS category;
CREATE TABLE category
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    name               VARCHAR(200) NOT NULL,
    description        VARCHAR(255),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_category
-- ============================
DROP TABLE IF EXISTS product_category;
CREATE TABLE product_category
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    product_id  INT NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_pc_category
        FOREIGN KEY (category_id) REFERENCES category (id)
            ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_pc_product
        FOREIGN KEY (product_id) REFERENCES product (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_pc_category ON product_category (category_id);
CREATE INDEX idx_pc_product ON product_category (product_id);

-- ============================
-- TABLE: product_option
-- ============================
DROP TABLE IF EXISTS product_option;
CREATE TABLE product_option
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    product_id         INT          NOT NULL,
    name               VARCHAR(100) NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_product_option_product
        FOREIGN KEY (product_id) REFERENCES product (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_po_product ON product_option (product_id);


-- ============================
-- TABLE: product_option_value
-- ============================
DROP TABLE IF EXISTS product_option_value;
CREATE TABLE product_option_value
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    product_option_id  INT NOT NULL,
    image              VARCHAR(255),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_poa_option
        FOREIGN KEY (product_option_id) REFERENCES product_option (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_poa_po ON product_option_value (product_option_id);


-- ============================
-- TABLE: review
-- ============================
DROP TABLE IF EXISTS review;
CREATE TABLE review
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    order_product_id INT NOT NULL,
    content VARCHAR(500) NOT NULL,
    image VARCHAR(255),
    customer_name VARCHAR(255),
    customer_id INT NOT NULL,
    shop_id INT NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: outbox
-- ============================
DROP TABLE IF EXISTS outbox;
CREATE TABLE outbox (
    id INT AUTO_INCREMENT PRIMARY KEY,
    saga_id VARCHAR(100) NOT NULL,
    type VARCHAR(100) NOT NULL,
    value VARCHAR(1000) NOT NULL,
    error VARCHAR(1000),
    status VARCHAR(20) DEFAULT 'PENDING',
    created_by VARCHAR(50),
    last_modified_by VARCHAR(50),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_outbox_type_status_id ON outbox (type, status, id DESC);
CREATE INDEX idx_outbox_status_id ON outbox (status, id DESC);


SET FOREIGN_KEY_CHECKS = 1;
