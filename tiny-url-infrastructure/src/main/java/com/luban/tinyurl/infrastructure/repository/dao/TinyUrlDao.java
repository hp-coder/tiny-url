package com.luban.tinyurl.infrastructure.repository.dao;

import com.luban.tinyurl.domain.TinyUrl;
import com.luban.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author hp
 */
@Repository
public interface TinyUrlDao extends BaseRepository<TinyUrl, Long> {

    @Query("update TinyUrl set accessCount =  accessCount + ?2 where id = ?1")
    @Modifying
    void incrAccessCount(Long id, Integer incrCount);
}
