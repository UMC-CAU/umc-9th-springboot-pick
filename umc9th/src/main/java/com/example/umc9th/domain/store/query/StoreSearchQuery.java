package com.example.umc9th.domain.store.query;

import com.example.umc9th.domain.store.dto.StoreListItemDto;
import com.example. umc9th.domain.store.entity.QStore;
import com.example. umc9th.domain.store.entity.QLocation;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreSearchQuery {

    private final JPAQueryFactory jpa;

    // 기본: page + size
    public Page<StoreListItemDto> searchStores(
            String regions,
            String keyword,
            String sort,
            Pageable pageable
    ){

        QStore s = QStore.store;
        QLocation l = QLocation.location;

        var where = buildWhere(s, l, regions, keyword, null);

        Long total = jpa
                .select(s.count())
                .from(s)
                .join(s.location, l)
                .where(where)
                .fetchOne();

        List<StoreListItemDto> content = jpa
                .select(Projections.constructor(StoreListItemDto.class,
                        s.id,
                        s.name,
                        s.address,
                        l.name
                ))
                .from(s)
                .join(s.location, l)
                .where(where)
                .orderBy(orderBy(sort, s))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);

    }

    public List<StoreListItemDto> searchStoresByCursor(
            String regions,
            String keyword,
            String sort,    // latest | name
            Long cursorId,  // 이전 페이지의 마지막 storeId
            int size
    ) {
        QStore s = QStore.store;
        QLocation l = QLocation.location;

        var where = buildWhere(s, l, regions, keyword, cursorId);

        return jpa
                .select(Projections.constructor(StoreListItemDto.class,
                        s.id,
                        s.name,
                        s.address,
                        l.name
                ))
                .from(s)
                .join(s.location, l)
                .where(where)
                .orderBy(orderBy(sort, s))
                .limit(size <= 0 ? 15 : size)
                .fetch();
    }

    private BooleanBuilder buildWhere(QStore s, QLocation l,
                                      String regions, String keyword, Long cursorId) {
        BooleanBuilder where = new BooleanBuilder();

        if (regions != null && !regions.isEmpty()) {
            where.and(l.name.in(regions));
        }

        // 이름 검색: 공백 포함 - 토큰 OR(합집합), 없으면 전체 포함
        if (keyword != null && !keyword.isBlank()) {
            String k = keyword.trim();
            if (k.contains(" ")) {
                BooleanBuilder orTokens = new BooleanBuilder();
                for (String token : k.split("\\s+")) {
                    if (!token.isBlank()) {
                        orTokens.or(s.name.containsIgnoreCase(token));
                    }
                }
                where.and(orTokens);
            } else {
                where.and(s.name.containsIgnoreCase(k));
            }
        }

        // 커서: 더 작은 id (최신순 페이지 전환 가정)
        if (cursorId != null) {
            where.and(s.id.lt(cursorId));
        }

        return where;
    }

    private OrderSpecifier<?>[] orderBy(String sort, QStore s) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        if ("name".equalsIgnoreCase(sort)) {
            // 가(한글) - 기타 - 동명이면 최신(id DESC)
            var rank = Expressions.numberTemplate(Integer.class,
                    "case " +
                            " when {0} REGEXP '^[ㄱ-ㅎ가-힣]' then 1" +
                            " else 2 end", s.name);
            orders.add(new OrderSpecifier<>(Order.ASC, rank));
            orders.add(s.name.asc());
            orders.add(s.id.desc());
        } else {
            // latest (기본)
            orders.add(s.id.desc());
        }
        return orders.toArray(OrderSpecifier[]::new);
    }
}
