package org.example.service;

import org.springframework.transaction.annotation.Transactional;

public interface BookstoreService {
    @Transactional
    public void remove(Integer id);
}
