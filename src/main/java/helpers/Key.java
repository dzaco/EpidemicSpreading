package helpers;

public class Key {
    int x;
    int y;
    private Key(int x, int y){
        this.x = x;
        this.y = y;
    }
    public static Key Pair(int x,int y) {
        return new Key(x,y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Key key = (Key) o;

        if (x != key.x) return false;
        return y == key.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
