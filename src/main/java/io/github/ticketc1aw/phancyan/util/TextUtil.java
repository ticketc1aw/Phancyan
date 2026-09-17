package io.github.ticketc1aw.phancyan.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class TextUtil {

    /**
     * Sin波によって2色の間を往復するグラデーションテキストを生成する。(GPTed,KJSより移植)
     *
     * @param text       テキスト
     * @param age        経過tick
     * @param color1     開始色（0xRRGGBB）
     * @param color2     終了色（0xRRGGBB）
     * @param speed      色変化の速度
     * @param charOffset 文字ごとの位相差
     * @return 色付きのMutableComponent
     */
    public static MutableComponent sineGradient(
            String text,
            long age,
            int color1,
            int color2,
            double speed,
            double charOffset
    ) {
        MutableComponent result = Component.empty();

        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        for (int i = 0; i < text.length(); i++) {
            String ch = String.valueOf(text.charAt(i));

            double t = (Math.sin(age * speed + i * charOffset) + 1.0) / 2.0;

            int r = (int) Math.floor(r1 + (r2 - r1) * t);
            int g = (int) Math.floor(g1 + (g2 - g1) * t);
            int b = (int) Math.floor(b1 + (b2 - b1) * t);

            int color = (r << 16) | (g << 8) | b;

            result.append(
                    Component.literal(ch)
                            .setStyle(Style.EMPTY.withColor(color))
            );
        }

        return result;
    }
}