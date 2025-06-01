-- Bảng loại tin: Categories
CREATE TABLE categories
(
    id   VARCHAR(50) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL
);

-- Bảng người dùng: Users
CREATE TABLE users
(
    id        VARCHAR(50) PRIMARY KEY,
    password  VARCHAR(255) NOT NULL,
    fullName  NVARCHAR(255) NOT NULL,
    birthdate DATE,
    gender    BIT DEFAULT 0, -- 0: nữ, 1: nam
    mobile    VARCHAR(15),
    email     VARCHAR(255) UNIQUE NOT NULL,
    role      BIT DEFAULT 0  -- 0: phóng viên, 1: quản trị viên
);

-- Bảng tin tức: News
CREATE TABLE news
(
    id         VARCHAR(50) PRIMARY KEY,
    title      NVARCHAR(255) NOT NULL,
    content    NVARCHAR(MAX) NOT NULL,
    image      VARCHAR(255),
    postDate   DATETIME DEFAULT GETDATE(),
    author     VARCHAR(50) NOT NULL,
    viewCount  INT DEFAULT 0,
    categoryId VARCHAR(50) NOT NULL,
    home       BIT DEFAULT 0, -- 0: không hiển thị trang chủ, 1: hiển thị trang chủ
    FOREIGN KEY (author) REFERENCES users (id),
    FOREIGN KEY (categoryId) REFERENCES categories (id)
);

-- Bảng Newsletter
CREATE TABLE newsletter
(
    email  VARCHAR(100) PRIMARY KEY,
    enable BIT DEFAULT 1 -- 1: nhận tin mới, 0: không nhận
);
