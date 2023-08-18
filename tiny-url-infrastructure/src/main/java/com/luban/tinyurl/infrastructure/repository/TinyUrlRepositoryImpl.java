package com.luban.tinyurl.infrastructure.repository;

import com.luban.tinyurl.domain.TinyUrl;
import com.luban.tinyurl.infrastructure.cache.TinyUrlCache;
import com.luban.tinyurl.infrastructure.repository.dao.TinyUrlDao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author hp
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TinyUrlRepositoryImpl implements TinyUrlRepository {

    private final TinyUrlCache tinyUrlCache;
    private final TinyUrlDao tinyUrlDao;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TinyUrl save(TinyUrl tinyUrl) {
        return tinyUrlDao.save(tinyUrl);
    }

    @Override
    public Optional<TinyUrl> findById(Long id) {
        final Optional<TinyUrl> tinyUrl = tinyUrlCache.findById(id);
        if (tinyUrl.isPresent()) {
            return tinyUrl;
        } else {
            final Optional<TinyUrl> tinyUrlFromDb = tinyUrlDao.findById(id);
            if (tinyUrlFromDb.isPresent()) {
                tinyUrlCache.put(tinyUrlFromDb.get());
                return tinyUrlFromDb;
            }
        }
        return Optional.empty();
    }

    @Override
    public void incrAccessCount(Long id, Integer incrCount) {
        tinyUrlDao.incrAccessCount(id, incrCount);
    }

    @Override
    public Optional<TinyUrl> findByUrl(String url) {
       return  tinyUrlDao.findByUrl(url);

    }
}
