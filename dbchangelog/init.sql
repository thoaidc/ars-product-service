CREATE DATABASE IF NOT EXISTS `ars_product` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ars_product`;

SET FOREIGN_KEY_CHECKS = 0;

-- ============================
-- TABLE: product
-- ============================
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
    normalized_name    VARCHAR(255),
    keyword            VARCHAR(500),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_group
-- ============================
CREATE TABLE product_group
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    shop_id            INT          NOT NULL,
    name               VARCHAR(200) NOT NULL,
    code               VARCHAR(50)  NOT NULL UNIQUE,
    description        VARCHAR(1000),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_product_group
-- ============================
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
CREATE TABLE category
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    name               VARCHAR(200) NOT NULL,
    code               VARCHAR(50)  NOT NULL UNIQUE,
    description        VARCHAR(255),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;


-- ============================
-- TABLE: product_category
-- ============================
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
-- TABLE: attribute
-- ============================
CREATE TABLE attribute
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    shop_id        INT NOT NULL,
    name           VARCHAR(100) NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- ============================
-- TABLE: variant
-- ============================
CREATE TABLE variant
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    name               VARCHAR(200) NOT NULL,
    price              DECIMAL(21, 6),
    product_id         INT          NOT NULL,
    attribute_id       INT          NOT NULL,
    thumbnail_url      VARCHAR(255),
    original_image     VARCHAR(255),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_variant_product
        FOREIGN KEY (product_id) REFERENCES product (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_variant_attribute_id
        FOREIGN KEY (attribute_id) REFERENCES attribute (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_variant_product ON variant (product_id);


-- ============================
-- TABLE: product_option
-- ============================
CREATE TABLE product_option
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    product_id         INT          NOT NULL,
    name               VARCHAR(100) NOT NULL,
    type               VARCHAR(50)  NOT NULL,
    top_percentage     FLOAT,
    left_percentage    FLOAT,
    width_percentage   FLOAT,
    height_percentage  FLOAT,
    description        VARCHAR(255),
    data               JSON,
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
-- TABLE: product_option_attribute
-- ============================
CREATE TABLE product_option_attribute
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    product_option_id  INT NOT NULL,
    image              VARCHAR(255),
    text               VARCHAR(255),
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_poa_option
        FOREIGN KEY (product_option_id) REFERENCES product_option (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_poa_po ON product_option_attribute (product_option_id);


-- ============================
-- TABLE: variant_option
-- ============================
CREATE TABLE variant_option
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    variant_id         INT NOT NULL,
    product_option_id  INT NOT NULL,
    created_by         VARCHAR(50),
    last_modified_by   VARCHAR(50),
    created_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_vo_variant
        FOREIGN KEY (variant_id) REFERENCES variant (id)
            ON DELETE CASCADE ON UPDATE CASCADE,

    CONSTRAINT fk_vo_po
        FOREIGN KEY (product_option_id) REFERENCES product_option (id)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE INDEX idx_vo_variant ON variant_option (variant_id);
CREATE INDEX idx_vo_po ON variant_option (product_option_id);

SET FOREIGN_KEY_CHECKS = 1;
