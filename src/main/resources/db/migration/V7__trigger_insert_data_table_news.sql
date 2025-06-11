CREATE TRIGGER trg_auto_generate_news_id
ON news
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @nextNumber INT;

    -- Tìm số lớn nhất từ cột id dạng 'newsX'
    SELECT @nextNumber =
        ISNULL(MAX(CAST(SUBSTRING(id, 5, LEN(id)) AS INT)), 0) + 1
    FROM news;

    -- Insert các dòng mới với id sinh tự động
    INSERT INTO news (id, title, content, image, postDate, author, viewCount, categoryId, home)
    SELECT
        'news' + CAST(@nextNumber + ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS VARCHAR),
        title,
        content,
        image,
        ISNULL(postDate, GETDATE()),
        author,
        ISNULL(viewCount, 0),
        categoryId,
        ISNULL(home, 0)
    FROM inserted;
END;