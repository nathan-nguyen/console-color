package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;

public abstract class ConsoleSprite extends RenderableSprite {
    private static final long CONVERT_BIT = 1L << 16 - 1;
    protected static final char[][] EMPTY_TEXTURE = new char[0][0];

    public int offsetX, offsetY;
    private long[][] texture;

    public ConsoleSprite(String id, int offsetX, int offsetY) {
        super(id);
        this.texture = convertCharTexture(EMPTY_TEXTURE);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public ConsoleSprite(char[][] texture, String id, int offsetX, int offsetY) {
        super(id);
        this.texture = convertCharTexture(texture);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    protected void setTexture(char[][] texture) {
        this.texture = convertCharTexture(texture);
    }

    public long[][] getTexture() {
        return texture;
    }

    public static <T> long[][] convertTexture(T[][] texture) {
        int m = texture.length, n = texture[0].length;
        long[][] result = new long[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                result[i][j] = encode(String.valueOf(texture[i][j]));
            }
        }
        return result;
    }

    public static long[][] convertCharTexture(char[][] texture) {
        int m = texture.length;
        if (m == 0) return new long[0][0];

        int n = texture[0].length;
        long[][] result = new long[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                result[i][j] = encode(String.valueOf(texture[i][j]));
            }
        }
        return result;
    }

    public static long[][][] convertCharTexture(char[][][] texture) {
        int m = texture.length;
        if (m == 0) return new long[0][0][0];

        int n = texture[0].length, o = texture[0][0].length;
        long[][][] result = new long[m][n][o];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                for (int k = 0; k < o; ++k) {
                    result[i][j][k] = encode(String.valueOf(texture[i][j][k]));
                }
            }
        }
        return result;
    }

    private static long encode(String s) {
        if (s.length() == 1) return s.charAt(0);
        return ((long) s.charAt(0) << 16) + s.charAt(1);
    }

    public static String decode(long s) {
        if (s >> 16 == 0) {
            return String.valueOf((char) s);
        }
        return String.valueOf((char) (s >> 16)) + (char) (s | CONVERT_BIT);
    }
}
