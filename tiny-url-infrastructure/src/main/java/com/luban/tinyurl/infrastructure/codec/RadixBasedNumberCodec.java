package com.luban.tinyurl.infrastructure.codec;

/**
 * @author hp
 */
public class RadixBasedNumberCodec implements NumberCodec {
    private final int radix;

    public RadixBasedNumberCodec(int radix) {
        this.radix = radix;
    }

    @Override
    public String encode(Long number) {
        return Long.toString(number, radix);
    }

    @Override
    public Long decode(String code) {
        return Long.parseLong(code, radix);
    }
}
