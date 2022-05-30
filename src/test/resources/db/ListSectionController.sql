INSERT INTO T_SECTION(id, tenant_id, account_id, title, sort_order, is_deleted, deleted)
    VALUES
        (100, 1, 1, 'Electronic', 2, false, null),
        (900, 1, 1, 'Deleted', 2, true, now()),
        (110, 1, 1, 'Deals', 1, false, null)
;
