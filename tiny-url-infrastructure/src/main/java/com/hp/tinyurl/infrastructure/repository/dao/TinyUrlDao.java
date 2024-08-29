package com.hp.tinyurl.infrastructure.repository.dao;

import com.hp.tinyurl.domain.TinyUrl;
import com.hp.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hp
 */
@Repository
public interface TinyUrlDao extends BaseRepository<TinyUrl, Long> {

    @Query("update TinyUrl set accessCount =  accessCount + ?2 where id = ?1")
    @Modifying
    void incrAccessCount(Long id, Integer incrCount);

    Optional<TinyUrl> findByUrl(String url);
}
