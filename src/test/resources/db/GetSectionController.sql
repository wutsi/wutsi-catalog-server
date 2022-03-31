INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, product_count, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Electronic', 1, 10, false, null),
        (900, 1, 1, 'Deleted', 2, 11, true, now())
;
