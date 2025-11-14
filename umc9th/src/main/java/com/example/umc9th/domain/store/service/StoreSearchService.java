package com.example.umc9th.domain.store.service;

import com.example.umc9th.domain.store.dto.StoreListItemDto;
import com.example.umc9th.domain.store.query.StoreSearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreSearchService {

    private final StoreSearchQuery query;

    public Page<StoreListItemDto> searchStores(
            String regions, String keyword, String sort, int page, int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return query.searchStores(regions, keyword, sort, pageable);
    }

    public List<StoreListItemDto> searchStoresByCursor(
            String regions, String keyword, String sort, Long cursorId, int size
    ) {
        return query.searchStoresByCursor(regions, keyword, sort, cursorId, size);
    }
}
