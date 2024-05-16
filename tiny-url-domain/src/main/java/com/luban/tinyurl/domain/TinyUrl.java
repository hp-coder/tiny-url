package com.luban.tinyurl.domain;


import cn.hutool.core.util.StrUtil;
import com.google.common.base.Preconditions;
import com.luban.common.base.annotation.FieldDesc;
import com.luban.common.base.enums.ValidStatus;
import com.luban.jpa.BaseJpaAggregate;
import com.luban.jpa.convertor.LocalDateTimeConverter;
import com.luban.jpa.convertor.ValidStatusConverter;
import com.luban.tinyurl.domain.command.AbstractCreateTinyUrlCommand;
import com.luban.tinyurl.domain.command.AccessTinyUrlCommand;
import com.luban.tinyurl.domain.command.DisableTinyUrlCommand;
import com.luban.tinyurl.domain.command.EnableTinyUrlCommand;
import com.luban.tinyurl.domain.context.CreateTinyUrlContext;
import com.luban.tinyurl.domain.context.CreateTinyUrlWithAccessCountLimitationContext;
import com.luban.tinyurl.domain.context.CreateTinyUrlWithExpirationContext;
import com.luban.tinyurl.domain.event.TinyUrlDomainEvents;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author hp 2023/4/10
 */
@Entity
@Table(name = "t_tiny_url")
@Data
@NoArgsConstructor
public class TinyUrl extends BaseJpaAggregate {

    @Convert(converter = TinyUrlTypeConverter.class)
    @FieldDesc("短链类型")
    private TinyUrlType type;

    @Convert(converter = ValidStatusConverter.class)
    @FieldDesc("状态")
    private ValidStatus status;

    @Column(columnDefinition = "varchar(2048)", updatable = false, nullable = false)
    @FieldDesc("实际URL")
    private String url;

    @Column(updatable = false)
    @FieldDesc("最大访问数量")
    private Integer maxCount;

    @Column(nullable = false)
    @FieldDesc("访问数量")
    private Integer accessCount;

    @Convert(converter = LocalDateTimeConverter.class)
    @FieldDesc("访问时间: 开始")
    private LocalDateTime startsAt;

    @Convert(converter = LocalDateTimeConverter.class)
    @FieldDesc("访问时间: 结束")
    private LocalDateTime endsAt;

    @FieldDesc("TODO 还需要研究下")
    @Column(name = "switch_code", updatable = false, nullable = false)
    private Integer switches = 0;

    public static TinyUrl createTinyUrl(CreateTinyUrlContext context) {
        final TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.COMMON);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.init(context.getCommand());
        return tinyUrl;
    }

    public static TinyUrl createTinyUrlWithExpiration(CreateTinyUrlWithExpirationContext context) {
        final TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.WITH_EXPIRATION);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.setStartsAt(context.getCommand().getStartsAt());
        tinyUrl.setEndsAt(context.getCommand().getEndsAt());
        tinyUrl.init(context.getCommand());
        return tinyUrl;
    }

    public static TinyUrl createTinyUrlWithAccessCountLimitation(CreateTinyUrlWithAccessCountLimitationContext context) {
        final TinyUrl tinyUrl = new TinyUrl();
        tinyUrl.setId(context.nextId());
        tinyUrl.setType(TinyUrlType.WITH_ACCESS_COUNT_LIMITATION);

        tinyUrl.setUrl(context.getCommand().getUrl());
        tinyUrl.setMaxCount(context.getCommand().getMaxCount());
        tinyUrl.init(context.getCommand());
        return tinyUrl;
    }

    private void init(AbstractCreateTinyUrlCommand command) {
        setStatus(ValidStatus.VALID);
        setAccessCount(0);
        if (Objects.nonNull(command.getEnableCacheSync()) && command.getEnableCacheSync()) {
            setEnableCacheSync();
            setEnableCache();
        }
        if (Objects.nonNull(command.getEnableCache()) && command.getEnableCache()) {
            setEnableCache();
        }
        registerEvent(new TinyUrlDomainEvents.TinyUrlCreatedEvent(this));
    }

    public void validate() {
        Preconditions.checkArgument(getType() != null);
        Preconditions.checkArgument(getStatus() != null);
        Preconditions.checkArgument(StrUtil.isNotEmpty(getUrl()));
        this.type.validate(this);
    }

    public boolean accessible() {
        return this.type.accessible(this);
    }

    public boolean needToUpdateAccessCount() {
        return this.type.needToUpdateAccessCount();
    }

    public void setEnableCacheSync() {
        setSwitches(ValidStatus.VALID.getCode());
    }

    public void setEnableCache() {
        setSwitches(ValidStatus.VALID.getCode());
    }

    public boolean isEnableCache() {
        return Objects.equals(ValidStatus.VALID.getCode(), getSwitches());
    }

    public boolean isEnableCacheSync() {
        return Objects.equals(ValidStatus.VALID.getCode(), getSwitches());
    }

    boolean checkExpiration() {
        return LocalDateTime.now().isAfter(getStartsAt()) && LocalDateTime.now().isBefore(getEndsAt());
    }

    boolean checkAccessCount() {
        return getMaxCount() > getAccessCount();
    }

    boolean checkStatus() {
        return ValidStatus.VALID == getStatus();
    }

    public void access(AccessTinyUrlCommand command) {
        final Integer count = command.getAccessCount();
        if (getType().needToUpdateAccessCount()) {
            final int newCount = getAccessCount() + count;
            setAccessCount(newCount);
        }
    }

    public void enable(EnableTinyUrlCommand command) {
        if (getStatus() == ValidStatus.INVALID) {
            setStatus(ValidStatus.VALID);
            registerEvent(new TinyUrlDomainEvents.TinyUrlEnabledEvent(this));
        }
    }

    public void disable(DisableTinyUrlCommand command) {
        if (getStatus() == ValidStatus.VALID) {
            setStatus(ValidStatus.INVALID);
            registerEvent(new TinyUrlDomainEvents.TinyUrlDisabledEvent(this));
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TinyUrl tinyUrl = (TinyUrl) o;
        return getId() != null && Objects.equals(getId(), tinyUrl.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
