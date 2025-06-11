CREATE TRIGGER trg_auto_generate_category_id
ON categories
INSTEAD OF INSERT
AS
BEGIN
    DECLARE @nextNumber INT;
    DECLARE @newID NVARCHAR(10);


    SELECT @nextNumber =
        ISNULL(MAX(CAST(SUBSTRING(id, 4, LEN(id)) AS INT)), 0) + 1
    FROM categories;

    INSERT INTO categories (id, name)
    SELECT
        'cat' + CAST(@nextNumber + ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS NVARCHAR),
        name
    FROM inserted;
END;
