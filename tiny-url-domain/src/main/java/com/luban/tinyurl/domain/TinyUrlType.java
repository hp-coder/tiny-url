package com.luban.tinyurl.domain;


import com.google.common.base.Preconditions;
import com.luban.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

/**
 * @author hp
 */
@Getter
@AllArgsConstructor
public enum TinyUrlType implements BaseEnum<TinyUrlType, String> {
    /***/
    COMMON("common", "普通类型"),
    WITH_EXPIRATION("with_expiration", "普通类型") {
        @Override
        public boolean accessible(TinyUrl tinyUrl) {
            return tinyUrl.checkStatus() && tinyUrl.checkExpiration();
        }

        @Override
        public void validate(TinyUrl tinyUrl) {
            Preconditions.checkArgument(Objects.nonNull(tinyUrl.getStartsAt()), "WITH_EXPIRATION: startsAt can not be null");
            Preconditions.checkArgument(Objects.nonNull(tinyUrl.getEndsAt()), "WITH_EXPIRATION: expiresAt can not be null");
        }
    },
    WITH_ACCESS_COUNT_LIMITATION("with_access_count_limitation", "普通类型") {
        @Override
        public boolean accessible(TinyUrl tinyUrl) {
            return tinyUrl.checkStatus() && tinyUrl.checkAccessCount();
        }

        @Override
        public boolean needToUpdateAccessCount() {
            return true;
        }

        @Override
        public void validate(TinyUrl tinyUrl) {
            Preconditions.checkArgument(Objects.nonNull(tinyUrl.getMaxCount()),"WITH_ACCESS_COUNT_LIMITATION: maxCount can not be null");
            Preconditions.checkArgument(Objects.nonNull(tinyUrl.getAccessCount()),"WITH_ACCESS_COUNT_LIMITATION: accessCount can not be null");
        }
    },
    ;
    private final String code;
    private final String name;

    public static Optional<TinyUrlType> of(String code) {
        return Optional.ofNullable(BaseEnum.parseByCode(TinyUrlType.class, code));
    }

    public void validate(TinyUrl tinyUrl) {
    }

    public boolean accessible(TinyUrl tinyUrl) {
        return tinyUrl.checkStatus();
    }

    public boolean needToUpdateAccessCount(){
        return false;
    }

}
