package com.midway.starwarsapi.util;

import java.time.LocalDate;

public interface ApiExpiration {
    static void checkApiExpiration() {
        LocalDate expirationDate = LocalDate.of(2023, 1, 1);
        if (LocalDate.now().isAfter(expirationDate)) System.exit(5);
    }
}
